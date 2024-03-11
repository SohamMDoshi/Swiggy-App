package main

import (
	"bytes"
	"encoding/json"
	"net/http"
	"net/http/httptest"
	"testing"

	"github.com/gin-gonic/gin"
	"github.com/stretchr/testify/assert"
)

func SetUpRouter() *gin.Engine {
	router := gin.Default()
	return router
}

func TestUpdateStatus(t *testing.T) {
	r := SetUpRouter()

	r.PUT("/fulfillment_service/delivery-personnels/:delivery_personnel_id/assigned-orders/:assigned_order_id", updateAssignedOrder)
	status := "DELIVERED"
	jsonValue, _ := json.Marshal(status)
	reqFound, _ := http.NewRequest("PUT", "/fulfillment_service/delivery-personnels/1/assigned-orders/1", bytes.NewBuffer(jsonValue))
	w := httptest.NewRecorder()
	r.ServeHTTP(w, reqFound)
	assert.Equal(t, http.StatusOK, w.Code)
}
