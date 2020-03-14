package com.evbx.product.layer.repository;

import com.evbx.product.layer.repository.projection.UniqueProjection;

import java.util.List;

public interface BaseRepository {

    void deleteById(long id);
    List<UniqueProjection> getUniqueColumnNames();
    boolean existsById(long id);
}
