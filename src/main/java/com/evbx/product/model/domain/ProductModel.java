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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "product_model")
@Data
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({ "id", "createdAt", "updatedAt", "deletedAt", "createdBy", "updatedBy", "deletedBy" })
@JsonPropertyOrder({ "modelName", "bookId", "industryReportId", "specificationReportId", "descriptions" })
@ToString(exclude = "product")
@Accessors(chain = true)
public class ProductModel extends UpdatableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, insertable = false, updatable = false)
    @JsonBackReference
    private Product product;

    @Column(name = "product_id")
    @JsonProperty("productId")
    @Range(min = 1, message = "{item.invalid-size}")
    @NotNull(message = "'productId' {item.mandatory-field}")
    private Long productId;

    @Column(name = "book_id")
    @JsonProperty("bookId")
    @Range(min = 1, message = "{item.invalid-size}")
    private Long bookId;

    @Column(name = "industry_report_id")
    @JsonProperty("industryReportId")
    @Range(min = 1, message = "{item.invalid-size}")
    private Long industryReportId;

    @Column(name = "specification_report_id")
    @JsonProperty("specificationReportId")
    @Range(min = 1, message = "{item.invalid-size}")
    private Long specificationReportId;

    @Column(name = "model_name")
    @JsonProperty("modelName")
    @Size(max = 255, min = 5, message = "{item.invalid-size}")
    @NotEmpty(message = "'modelName' {item.mandatory-field}")
    private String modelName;

    @OneToMany(mappedBy = "productModel", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JsonProperty("descriptions")
    private List<Description> descriptions = Collections.emptyList();
}
