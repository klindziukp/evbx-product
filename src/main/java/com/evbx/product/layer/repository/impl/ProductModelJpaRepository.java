package com.evbx.product.layer.repository.impl;

import com.evbx.product.layer.repository.ProductModelRepository;
import com.evbx.product.model.domain.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductModelJpaRepository extends JpaRepository<ProductModel, Long>, ProductModelRepository {

}
