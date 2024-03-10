package com.swiggy.order_service.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Location {
    @NotNull
    @DecimalMin(value = "18.4", inclusive = true, message = "latitude should be between 18.4 to 18.6")
    @DecimalMax(value = "18.6", inclusive = true, message = "latitude should be between 18.4 to 18.6")
    private Double latitude;

    @NotNull
    @DecimalMin(value = "73.7", inclusive = true, message = "longitude should be between 73.7 to 74.0")
    @DecimalMax(value = "74.0", inclusive = true, message = "longitude should be between 73.7 to 74.0")
    private Double longitude;


    public fulfillment.Location covertToProto() {
        return fulfillment.Location.newBuilder()
                .setLatitude(this.latitude)
                .setLongitude(this.longitude)
                .build();
    }
}
