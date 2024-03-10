package model

type AssignedOrder struct {
	ID                  int64  `gorm:"primaryKey;autoIncrement"`
	OrderID             int64  `gorm:"not null"`
	DeliveryPersonnelID int64  `gorm:"not null"`
	Status              string `gorm:"not null"`
}