package com.alta.ecommerce_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    @Size(max = 255)
    @JsonProperty("user_id")
    private String userId;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Min(1)
    private double price;

    @Min(1)
    private int stock;

}
