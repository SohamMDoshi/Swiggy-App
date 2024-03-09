package main

import (
	"context"
	"errors"
	"fmt"
	"log"
	"math"
	"net"

	pb "fulfillment_service/proto"

	"google.golang.org/grpc"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

type Server struct {
	DB *gorm.DB
	pb.UnimplementedFulfillmentServiceServer
}

func init() {
	DatabaseConnection()
}

type Location struct {
	Latitude  float64 `gorm:"type:double precision"`
	Longitude float64 `gorm:"type:double precision"`
}

type DeliveryPersonnel struct {
	ID           uint     `gorm:"primaryKey;autoIncrement"`
	Name         string   `gorm:"not null"`
	Location     Location `gorm:"embedded;embeddedPrefix:location_"`
	Availability bool     `gorm:"not null"`
}

type AssignedOrder struct {
	ID                  uint   `gorm:"primaryKey;autoIncrement"`
	OrderID             string `gorm:"not null"`
	DeliveryPersonnelID string `gorm:"not null"`
	Status              string `gorm:"not null"`
}

func DatabaseConnection() *gorm.DB {
	host := "localhost"
	port := "5432"
	dbName := "fulfillment_service"
	dbUser := "swiggy"
	password := ""
	dsn := fmt.Sprintf("host=%s port=%s user=%s dbname=%s password=%s sslmode=disable",
		host,
		port,
		dbUser,
		dbName,
		password,
	)
	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatal("Error connecting to the database...", err)
	}
	fmt.Println("Database connection successful...")

	db.AutoMigrate(&DeliveryPersonnel{}, &AssignedOrder{})

	return db
}

func (s *Server) AssignOrder(ctx context.Context, req *pb.AssignOrderRequest) (*pb.AssignedOrder, error) {
	if req == nil {
		return nil, errors.New("request is nil")
	}
	if req.OrderId == 0 {
		return nil, errors.New("order ID is empty")
	}
	if req.RestaurantLocation == nil {
		return nil, errors.New("restaurant location is nil")
	}

	// Fetch all available delivery personnel from the database
	availablePersonnel, err := s.fetchAvailableDeliveryPersonnel(ctx)
	if err != nil {
		return nil, err
	}
	if len(availablePersonnel) == 0 {
		return nil, errors.New("no available delivery personnel")
	}

	// Find the closest available delivery personnel
	closestDeliveryPersonnel, err := s.findClosestDeliveryPersonnel(req.RestaurantLocation, availablePersonnel)
	if err != nil {
		return nil, err
	}

	// Assign the order to the closest available delivery personnel
	assignedOrder := &pb.AssignedOrder{
		OrderId:             req.OrderId,
		DeliveryPersonnelId: closestDeliveryPersonnel.Id,
		Status:              pb.AssignedOrder_Status(0), // Set status to ASSIGNED
	}

	return assignedOrder, nil
}

func (s *Server) fetchAvailableDeliveryPersonnel(ctx context.Context) ([]*pb.DeliveryPersonnel, error) {
	var availablePersonnel []*pb.DeliveryPersonnel
	if err := s.DB.Where("availability = ?", true).Find(&availablePersonnel).Error; err != nil {
		return nil, err
	}
	return availablePersonnel, nil
}

func (s *Server) findClosestDeliveryPersonnel(restaurantLocation *pb.Location, availablePersonnel []*pb.DeliveryPersonnel) (*pb.DeliveryPersonnel, error) {
	var closestDeliveryPersonnel *pb.DeliveryPersonnel
	minDistance := math.Inf(1)

	for _, dp := range availablePersonnel {
		distance := calculateDistance(restaurantLocation, dp.Location)
		if distance < minDistance {
			minDistance = distance
			closestDeliveryPersonnel = dp
		}
	}

	if closestDeliveryPersonnel == nil {
		return nil, errors.New("no closest delivery personnel found")
	}

	return closestDeliveryPersonnel, nil
}

func calculateDistance(loc1, loc2 *pb.Location) float64 {
	// Using Haversine formula to calculate distance between two locations
	const earthRadius = 6371 // Earth radius in kilometers
	lat1 := toRadians(loc1.Latitude)
	lon1 := toRadians(loc1.Longitude)
	lat2 := toRadians(loc2.Latitude)
	lon2 := toRadians(loc2.Longitude)

	dlat := lat2 - lat1
	dlon := lon2 - lon1

	a := math.Pow(math.Sin(dlat/2), 2) + math.Cos(lat1)*math.Cos(lat2)*math.Pow(math.Sin(dlon/2), 2)
	c := 2 * math.Atan2(math.Sqrt(a), math.Sqrt(1-a))

	return earthRadius * c
}

func toRadians(deg float64) float64 {
	return deg * (math.Pi / 180)
}

func main() {
	lis, err := net.Listen("tcp", ":9090")
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	s := grpc.NewServer()
	db := DatabaseConnection()
	pb.RegisterFulfillmentServiceServer(s, &Server{DB: db})
	log.Println("Server started at :9090")
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
