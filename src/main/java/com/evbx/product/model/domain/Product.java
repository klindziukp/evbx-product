package com.evbx.product.model.domain;

import com.evbx.product.model.UpdatableEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({ "createdAt", "updatedAt", "deletedAt", "createdBy", "updatedBy", "deletedBy" })
@JsonPropertyOrder({ "id", "productName", "models" })
@Accessors(chain = true)
public class Product extends UpdatableEntity {

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonProperty("models")
    private List<ProductModel> models;

    @Column(name = "product_name")
    @JsonProperty("productName")
    @Size(max = 255, min = 5, message = "{item.invalid-size}")
    @NotEmpty(message = "'productName' {item.mandatory-field}")
    private String productName;
}
