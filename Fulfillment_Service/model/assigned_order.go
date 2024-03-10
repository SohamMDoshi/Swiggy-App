package model

type AssignedOrder struct {
	ID                  int64  `gorm:"primaryKey;autoIncrement:true" json:"id"`
	OrderID             int64  `gorm:"not null"`
	DeliveryPersonnelID int64  `gorm:"not null"`
	Status              string `gorm:"not null"`
}
