package com.evbx.product.layer.repository;

import com.evbx.product.layer.repository.projection.UniqueProjection;
import com.evbx.product.model.domain.Description;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DescriptionRepository extends BaseRepository {

    List<Description> findAll();
    Optional<Description> findById(long id);
    Description save(Description description);

    @Override
    @Query(value = "SELECT description_line AS uniqueColumn FROM description", nativeQuery = true)
    List<UniqueProjection> getUniqueColumnNames();

    @Query(value = "SELECT id FROM product_model", nativeQuery = true)
    List<Long> getProductModelIds();
}
