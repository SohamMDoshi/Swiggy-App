package main

import (
	"errors"
	"fmt"
	"log"
	"net/http"
	"reflect"

	"fulfillment_service/model"
	pb "fulfillment_service/proto"

	"github.com/gin-gonic/gin"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

var db *gorm.DB

func main() {
	var err error
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
	db, err = gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		log.Fatal("Error connecting to the database...", err)
	}
	fmt.Println("Database connection successful...")

	router := gin.Default()

	// Auth middleware to authenticate delivery personnel using basic auth
	router.Use(authMiddleware)

	// Route to handle the assigned order update
	router.PUT("/fulfillment_service/delivery-personnels/:delivery_personnel_id/assigned-orders/:assigned_order_id", updateAssignedOrder)

	router.Run(":8082")
}

func authMiddleware(c *gin.Context) {
	// Retrieve username and password from request headers
	username, password, _ := c.Request.BasicAuth()
	// Authenticate the delivery personnel using basic auth
	deliveryPersonnel := &model.DeliveryPersonnel{}
	result := db.Where("name = ?", username).First(deliveryPersonnel)
	if result.Error != nil {
		c.AbortWithStatusJSON(http.StatusInternalServerError, gin.H{"error": "Failed to authenticate: database error"})
		return
	}

	// Check if delivery personnel exists and password is correct
	if !reflect.DeepEqual(password, deliveryPersonnel.Password) {
		c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"error": "Invalid credentials"})
		return
	}

	// Set delivery personnel in context for subsequent handlers
	c.Set("delivery_personnel", deliveryPersonnel)
	c.Next()
}

func updateAssignedOrder(c *gin.Context) {
	// Parse assigned order ID from URL params
	assignedOrderID := c.Param("assigned_order_id")

	// Parse status from request body
	var requestBody struct {
		Status string `json:"status" binding:"required"`
	}
	if err := c.ShouldBindJSON(&requestBody); err != nil {
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	// Fetch delivery personnel from context
	deliveryPersonnel, _ := c.Get("delivery_personnel")

	// Fetch assigned order from database
	assignedOrder := &model.AssignedOrder{}
	result := db.Where("id = ? AND delivery_personnel_id = ?", assignedOrderID, deliveryPersonnel.(*model.DeliveryPersonnel).Id).First(assignedOrder)
	if result.Error != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": "Assigned order not found"})
		return
	}

	// Update assigned order status
	assignedOrder.Status = requestBody.Status
	db.Save(&assignedOrder)

	// Update delivery personnel location based on status
	if err := updateDeliveryPersonnelLocation(c, assignedOrder.OrderID, requestBody.Status); err != nil {
		c.AbortWithStatusJSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Assigned order updated successfully", "data": assignedOrder})
}

func updateDeliveryPersonnelLocation(c *gin.Context, orderID int64, status string) error {
	// Fetch delivery details from the database
	var deliveryDetails model.DeliveryDetails
	if err := db.Where("order_id = ?", orderID).First(&deliveryDetails).Error; err != nil {
		return fmt.Errorf("failed to fetch delivery details: %v", err)
	}

	// Get the current delivery personnel
	deliveryPersonnel, ok := c.Get("delivery_personnel")
	if !ok {
		return errors.New("delivery personnel not found in context")
	}
	currentDeliveryPersonnel := deliveryPersonnel.(*model.DeliveryPersonnel)

	// Determine the location to update based on the status
	var location *pb.Location
	switch status {
	case "IN_TRANSIT":
		location = deliveryDetails.RestaurantLocation
	case "DELIVERED":
		location = deliveryDetails.CustomerLocation
		currentDeliveryPersonnel.Availability = true
	default:
		return errors.New("invalid status")
	}

	// Update the delivery personnel's location
	currentDeliveryPersonnel.Location = &model.Location{
		Latitude:  location.Latitude,
		Longitude: location.Longitude,
	}

	if err := db.Save(&currentDeliveryPersonnel).Error; err != nil {
		return fmt.Errorf("failed to update delivery personnel location: %v", err)
	}

	return nil
}
