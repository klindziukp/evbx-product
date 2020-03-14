package com.evbx.product.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class UpdatableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @JsonProperty("id")
    private Long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonProperty("createdAt")
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @JsonIgnore
    private Date createdAt;

    @Column(name = "updated_at")
    @JsonProperty("updatedAt")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @JsonIgnore
    private Date updatedAt;

    @Column(name = "deleted_at")
    @JsonProperty("deletedAt")
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @JsonIgnore
    private Date deletedAt;

    @Column(name = "created_by")
    @JsonProperty("createdBy")
    @JsonIgnore
    private String createdBy;

    @Column(name = "updated_by")
    @JsonProperty("updatedBy")
    @JsonIgnore
    private String updatedBy;

    @Column(name = "deleted_by")
    @JsonProperty("deletedBy")
    @JsonIgnore
    private String deletedBy;
}
