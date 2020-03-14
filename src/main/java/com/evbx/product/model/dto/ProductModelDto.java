package com.evbx.product.model.dto;

import com.evbx.product.model.domain.Description;
import com.evbx.product.model.dto.resource.ResourcesDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ProductModelDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("modelName")
    private String modelName;

    @JsonProperty("descriptions")
    private List<Description> descriptions;

    @JsonProperty("resources")
    private ResourcesDto resourcesDto = new ResourcesDto();
}
