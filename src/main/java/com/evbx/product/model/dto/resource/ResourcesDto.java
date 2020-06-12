package com.evbx.product.model.dto.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResourcesDto implements Serializable {

    @JsonProperty("e-book")
    private Book book;

    @JsonProperty("industryReport")
    private IndustryReport industryReport;

    @JsonProperty("specification")
    private Specification specification;
}
