package main

import (
	"fmt"
	"log"
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

func main() {
	lis, err := net.Listen("tcp", ":50051")
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}
	s := grpc.NewServer()
	db := DatabaseConnection()
	pb.RegisterFulfillmentServiceServer(s, &Server{DB: db})
	log.Println("Server started at :50051")
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
