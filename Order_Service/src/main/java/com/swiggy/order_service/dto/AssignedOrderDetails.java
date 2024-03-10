package com.swiggy.order_service.dto;

import fulfillment.AssignedOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignedOrderDetails {
    private Long id;
    private Long orderId;
    private Long deliveryPersonnelId;
    private String status;

    public AssignedOrderDetails(AssignedOrder assignedOrder) {
        this.id = assignedOrder.getId();
        this.orderId = assignedOrder.getOrderId();
        this.deliveryPersonnelId = assignedOrder.getDeliveryPersonnelId();
        this.status = assignedOrder.getStatus().toString();
    }
}
