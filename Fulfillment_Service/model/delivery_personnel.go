package model

type Location struct {
	Latitude  float64 `gorm:"type:double precision"`
	Longitude float64 `gorm:"type:double precision"`
}

type DeliveryPersonnel struct {
	Id           int64     `gorm:"primaryKey;autoIncrement:true" json:"id"`
	Name         string    `json:"name" gorm:"not null"`
	Password     string    `json:"-" gorm:"not null"`
	Location     *Location `json:"location" gorm:"embedded"`
	Availability bool      `json:"availability" gorm:"not null"`
}
