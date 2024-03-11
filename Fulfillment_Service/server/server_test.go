package main

import (
	"context"
	pb "fulfillment_service/proto"
	"testing"

	"github.com/DATA-DOG/go-sqlmock"
	"github.com/stretchr/testify/assert"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

func TestAssignOrder(t *testing.T) {
	db, mock, err := sqlmock.New()
	assert.Nil(t, err, "Error creating mock db: %v", err)
	defer db.Close()

	dialect := postgres.New(postgres.Config{
		Conn:       db,
		DriverName: "postgres",
	})

	gormDB, err := gorm.Open(dialect, &gorm.Config{})
	assert.Nil(t, err, "Error creating mock gorm db: %v", err)

	mockServer := &Server{DB: gormDB}

	type args struct {
		ctx context.Context
		req *pb.AssignOrderRequest
	}

	tests := []struct {
		name    string
		args    args
		rows    func()
		want    *pb.AssignedOrder
		wantErr bool
	}{
		{
			name: "Assign order - Success",
			args: args{
				ctx: context.Background(),
				req: &pb.AssignOrderRequest{
					OrderId:            123,
					RestaurantLocation: &pb.Location{Latitude: 0.0, Longitude: 0.0},
					CustomerLocation:   &pb.Location{Latitude: 1.0, Longitude: 1.0},
				},
			},
			rows: func() {
				mock.ExpectQuery("SELECT").WillReturnRows(sqlmock.NewRows([]string{"id"}).AddRow(1))
				mock.ExpectBegin()
				mock.ExpectQuery("INSERT").WillReturnRows(sqlmock.NewRows([]string{"id"}).AddRow(1))
				mock.ExpectCommit()
				mock.ExpectExec("UPDATE").WillReturnResult(sqlmock.NewResult(0, 1))
			},
			want: &pb.AssignedOrder{
				OrderId:             123,
				DeliveryPersonnelId: 1,
				Status:              pb.AssignedOrder_ASSIGNED,
			},
			wantErr: false,
		},
	}

	for _, tc := range tests {
		t.Run(tc.name, func(t *testing.T) {
			tc.rows()

			got, err := mockServer.AssignOrder(tc.args.ctx, tc.args.req)

			if (err != nil) != tc.wantErr {
				t.Fatalf("AssignOrder() error = %v, wantErr %v", err, tc.wantErr)
			}
			assert.Equalf(t, tc.want, got, "AssignOrder(%v, %v)", tc.args.ctx, tc.args.req)
		})
	}
}
