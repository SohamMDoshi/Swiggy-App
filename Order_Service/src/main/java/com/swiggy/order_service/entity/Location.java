package com.swiggy.order_service.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Location {
    private Double latitude;
    private Double longitude;


    public fulfillment.Location covertToProto() {
        return fulfillment.Location.newBuilder()
                .setLatitude(this.getLatitude())
                .setLongitude(this.getLongitude())
                .build();
    }
}
