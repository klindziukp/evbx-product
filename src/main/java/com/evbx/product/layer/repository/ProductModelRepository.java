package com.evbx.product.layer.repository;

import com.evbx.product.layer.repository.projection.UniqueProjection;
import com.evbx.product.model.domain.ProductModel;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductModelRepository extends BaseRepository {

    List<ProductModel> findAll();
    Optional<ProductModel> findById(long id);
    ProductModel save(ProductModel productModel);

    @Override
    @Query(value = "SELECT model_name AS uniqueColumn FROM product_model", nativeQuery = true)
    List<UniqueProjection> getUniqueColumnNames();

    @Query(value = "SELECT id FROM product", nativeQuery = true)
    List<Long> getAllProductIds();
}
