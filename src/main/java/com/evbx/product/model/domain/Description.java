package com.evbx.product.model.domain;

import com.evbx.product.model.UpdatableEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "description")
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({ "createdAt", "updatedAt", "deletedAt", "createdBy", "updatedBy", "deletedBy" })
@JsonPropertyOrder({ "id", "modelId", "descriptionLine" })
@ToString(exclude = "productModel")
@Accessors(chain = true)
public class Description extends UpdatableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "model_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private ProductModel productModel;

    @Column(name = "model_id")
    @JsonProperty("modelId")
    @Range(min = 1, message = "{item.invalid-size}")
    @NotNull(message = "'modelId' {item.mandatory-field}")
    private Long modelId;

    @Column(name = "description_line")
    @JsonProperty("descriptionLine")
    @Size(max = 255, min = 5, message = "{item.invalid-size}")
    @NotEmpty(message = "'descriptionLine' {item.mandatory-field}")
    private String descriptionLine;
}
