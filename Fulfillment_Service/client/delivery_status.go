package main

import (
	"errors"
	"fmt"
	"log"
	"net/http"
	"reflect"
	"strconv"

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

	router.Use(authMiddleware)

	router.PUT("/fulfillment_service/delivery-personnels/:delivery_personnel_id/assigned-orders/:assigned_order_id", updateAssignedOrder)

	router.Run(":8082")
}

func authMiddleware(c *gin.Context) {
	username, password, _ := c.Request.BasicAuth()
	deliveryPersonnel := &model.DeliveryPersonnel{}
	result := db.Where("name = ?", username).First(deliveryPersonnel)
	if result.Error != nil {
		c.AbortWithStatusJSON(http.StatusInternalServerError, gin.H{"error": "Failed to authenticate: database error"})
		return
	}

	if !reflect.DeepEqual(password, deliveryPersonnel.Password) {
		c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"error": "Invalid credentials"})
		return
	}

	c.Set("delivery_personnel", deliveryPersonnel)
	c.Next()
}

func updateAssignedOrder(c *gin.Context) {
	deliveryPersonnelIDStr := c.Param("delivery_personnel_id")
	assignedOrderID := c.Param("assigned_order_id")

	deliveryPersonnelID, err := strconv.ParseInt(deliveryPersonnelIDStr, 10, 64)
	if err != nil {
		c.JSON(http.StatusBadRequest, gin.H{"error": "Invalid delivery personnel ID"})
		return
	}

	var requestBody struct {
		Status string `json:"status" binding:"required"`
	}
	if err := c.ShouldBindJSON(&requestBody); err != nil {
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	deliveryPersonnel, _ := c.Get("delivery_personnel")

	if deliveryPersonnelID != deliveryPersonnel.(*model.DeliveryPersonnel).Id {
		c.AbortWithStatusJSON(http.StatusUnauthorized, gin.H{"error": "Invalid credentials"})
		return
	}

	assignedOrder := &model.AssignedOrder{}
	result := db.Where("id = ? AND delivery_personnel_id = ?", assignedOrderID, deliveryPersonnelID).First(assignedOrder)
	if result.Error != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": "Assigned order not found"})
		return
	}

	if assignedOrder.Status == "DELIVERED" {
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"error": "The order has already been delivered"})
		return
	}

	assignedOrder.Status = requestBody.Status
	db.Save(&assignedOrder)

	if err := updateDeliveryPersonnelLocation(c, assignedOrder.OrderID, requestBody.Status); err != nil {
		c.AbortWithStatusJSON(http.StatusInternalServerError, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"message": "Assigned order updated successfully", "data": assignedOrder})
}

func updateDeliveryPersonnelLocation(c *gin.Context, orderID int64, status string) error {
	var deliveryDetails model.DeliveryDetails
	if err := db.Where("order_id = ?", orderID).First(&deliveryDetails).Error; err != nil {
		return fmt.Errorf("failed to fetch delivery details: %v", err)
	}

	deliveryPersonnel, ok := c.Get("delivery_personnel")
	if !ok {
		return errors.New("delivery personnel not found in context")
	}
	currentDeliveryPersonnel := deliveryPersonnel.(*model.DeliveryPersonnel)

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

	currentDeliveryPersonnel.Location = &model.Location{
		Latitude:  location.Latitude,
		Longitude: location.Longitude,
	}

	if err := db.Save(&currentDeliveryPersonnel).Error; err != nil {
		return fmt.Errorf("failed to update delivery personnel location: %v", err)
	}

	return nil
}
