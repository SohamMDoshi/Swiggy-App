package com.swiggy.order_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swiggy.order_service.dto.AssignedOrderDetails;
import com.swiggy.order_service.responseModel.MenuItemResponse;
import fulfillment.AssignOrderRequest;
import fulfillment.AssignedOrder;
import fulfillment.FulfillmentServiceGrpc;
import fulfillment.Location;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static jakarta.persistence.ConstraintMode.CONSTRAINT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "users_id", foreignKey = @ForeignKey(value = CONSTRAINT,
            foreignKeyDefinition = "FOREIGN KEY (users_id) REFERENCES users(id) ON DELETE CASCADE"))
    private User user;
    private Double totalPrice;

    public Order(User user,Double totalPrice) {
        this.user = user;
        this.totalPrice = totalPrice;
    }

    private final static Logger log = LoggerFactory.getLogger(Order.class);

    public AssignedOrderDetails assignDeliveryPersonnel(MenuItemResponse response) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        FulfillmentServiceGrpc.FulfillmentServiceBlockingStub stub = FulfillmentServiceGrpc.newBlockingStub(channel);
        AssignOrderRequest request = AssignOrderRequest.newBuilder()
                .setOrderId(this.id)
                .setRestaurantLocation(response.getRestaurant().getLocation().covertToProto())
                .setCustomerLocation(this.user.getLocation().covertToProto())
                .build();

        AssignedOrder assignedOrder = stub.assignOrder(request);

        log.info("Server message: {}",response);
        channel.shutdown();
        return new AssignedOrderDetails(assignedOrder);
    }
}
