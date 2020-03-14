package com.evbx.product.model.dto.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResourcesDto {

    @JsonProperty("e-book")
    private Book book;

    @JsonProperty("industryReport")
    private IndustryReport industryReport;

    @JsonProperty("specification")
    private Specification specification;
}
