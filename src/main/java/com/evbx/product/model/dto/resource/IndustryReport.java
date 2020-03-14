package com.evbx.product.model.dto.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@JsonPropertyOrder({ "id", "industryName", "description", "text" })
public class IndustryReport extends ResourceDto {

    @JsonProperty("industryName")
    private String industryName;
}
