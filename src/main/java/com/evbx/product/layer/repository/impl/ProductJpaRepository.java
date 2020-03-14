package com.evbx.product.layer.repository.impl;

import com.evbx.product.layer.repository.ProductRepository;
import com.evbx.product.model.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductRepository {

}
