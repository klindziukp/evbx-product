package com.evbx.product.model.dto.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@JsonPropertyOrder({ "id", "specificationName", "description", "text" })
public class Specification extends ResourceDto {

    @JsonProperty("specificationName")
    private String specificationName;
}
