package com.evbx.product.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ProductDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("productName")
    private String productName;

    @JsonProperty("productModels")
    private List<ProductModelDto> productModels;
}
