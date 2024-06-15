package com.alta.ecommerce_api.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductSearchRequest {
    private String name;
    private String description;

    @NotNull
    private Integer page;

    @NotNull
    private Integer limit;
}
