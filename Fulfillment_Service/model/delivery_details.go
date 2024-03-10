package model

import pb "fulfillment_service/proto"

type DeliveryDetails struct {
	Id                 int64        `gorm:"primaryKey;autoIncrement:true" json:"id"`
	OrderId            int64        `gorm:"not null"`
	RestaurantLocation *pb.Location `json:"restaurant_location" gorm:"embedded;embeddedPrefix:restaurant_location_"`
	CustomerLocation   *pb.Location `json:"customer_location" gorm:"embedded;embeddedPrefix:customer_location_"`
}
