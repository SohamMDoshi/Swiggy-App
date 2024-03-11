package main

import (
	"context"
	"errors"
	"fmt"
	"log"
	"math"
	"net"

	"fulfillment_service/model"
	pb "fulfillment_service/proto"

	"google.golang.org/grpc"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

type Server struct {
	DB *gorm.DB
	pb.UnimplementedFulfillmentServiceServer
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

	db.AutoMigrate(&model.DeliveryPersonnel{}, &model.AssignedOrder{}, &model.DeliveryDetails{})

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

	availablePersonnel, err := s.fetchAvailableDeliveryPersonnel()
	if err != nil {
		return nil, err
	}
	if len(availablePersonnel) == 0 {
		return nil, errors.New("no available delivery personnel")
	}

	closestDeliveryPersonnel, err := s.findClosestDeliveryPersonnel(req.RestaurantLocation, availablePersonnel)
	if err != nil {
		return nil, err
	}

	assignedOrder := &pb.AssignedOrder{
		OrderId:             req.OrderId,
		DeliveryPersonnelId: closestDeliveryPersonnel.Id,
		Status:              pb.AssignedOrder_ASSIGNED,
	}

	deliveryDetails := &model.DeliveryDetails{
		OrderId:            req.OrderId,
		RestaurantLocation: req.RestaurantLocation,
		CustomerLocation:   req.CustomerLocation,
	}

	err1 := s.saveDeliveryDetails(deliveryDetails)
	if err1 != nil {
		return nil, err1
	}

	result, err := s.saveAssignedOrder(assignedOrder)
	if err != nil {
		return nil, err
	}
	assignedOrder.Id = result.ID

	if err := s.updateDeliveryPersonnelAvailability(closestDeliveryPersonnel.Id, false); err != nil {
		return nil, err
	}

	return assignedOrder, nil
}

func (s *Server) saveDeliveryDetails(details *model.DeliveryDetails) error {
	result := s.DB.Create(details)
	if result.Error != nil {
		return fmt.Errorf("failed to save delivery details: %v", result.Error)
	}
	return nil
}

func (s *Server) saveAssignedOrder(order *pb.AssignedOrder) (*model.AssignedOrder, error) {
	// Convert AssignedOrder to model.AssignedOrder
	dbOrder := &model.AssignedOrder{
		OrderID:             order.OrderId,
		DeliveryPersonnelID: order.DeliveryPersonnelId,
		Status:              order.Status.String(),
	}

	// Save the order to the database using GORM
	result := s.DB.Create(dbOrder)
	if result.Error != nil {
		return nil, fmt.Errorf("failed to save assigned order: %v", result.Error)
	}

	// Return the saved order
	return dbOrder, nil
}

func (s *Server) updateDeliveryPersonnelAvailability(id int64, availability bool) error {
	// Update the availability of the delivery personnel in the database
	if err := s.DB.Model(&model.DeliveryPersonnel{}).Where("id = ?", id).Update("availability", availability).Error; err != nil {
		return fmt.Errorf("failed to update delivery personnel availability: %v", err)
	}
	return nil
}

func (s *Server) fetchAvailableDeliveryPersonnel() ([]*model.DeliveryPersonnel, error) {
	var availablePersonnel []*model.DeliveryPersonnel
	if err := s.DB.Where("availability = ?", true).Find(&availablePersonnel).Error; err != nil {
		return nil, err
	}
	return availablePersonnel, nil
}

func (s *Server) findClosestDeliveryPersonnel(restaurantLocation *pb.Location, availablePersonnel []*model.DeliveryPersonnel) (*model.DeliveryPersonnel, error) {
	var closestDeliveryPersonnel *model.DeliveryPersonnel
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

func calculateDistance(loc1 *pb.Location, loc2 *model.Location) float64 {
	const earthRadius = 6371
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
