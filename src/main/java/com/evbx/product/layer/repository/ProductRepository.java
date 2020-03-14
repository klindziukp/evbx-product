package com.evbx.product.layer.repository;

import com.evbx.product.layer.repository.projection.UniqueProjection;
import com.evbx.product.model.domain.Product;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends BaseRepository {

    List<Product> findAll();
    Optional<Product> findById(long id);
    Product save(Product product);

    @Override
    @Query(value = "SELECT product_name AS uniqueColumn FROM product", nativeQuery = true)
    List<UniqueProjection> getUniqueColumnNames();
}
