package com.evbx.product.model.dto.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class IndustryReport extends ResourceDto {

    @JsonProperty("industryName")
    private String industryName;
}
