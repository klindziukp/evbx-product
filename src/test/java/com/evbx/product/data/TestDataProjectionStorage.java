package com.evbx.product.data;

import com.evbx.product.layer.repository.projection.UniqueProjection;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.ArrayList;
import java.util.List;

public final class TestDataProjectionStorage {

    public TestDataProjectionStorage() {
    }

    private static ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

    public static List<UniqueProjection> getDescriptionProjections() {
        List<UniqueProjection> projections = new ArrayList<>();
        projections.add(getNameProjection("Description-line-0"));
        projections.add(getNameProjection("Description-line-1"));
        projections.add(getNameProjection("Description-line-2"));
        return projections;
    }

    public static List<UniqueProjection> getProductModelProjections() {
        List<UniqueProjection> projections = new ArrayList<>();
        projections.add(getNameProjection("Product-model-0"));
        projections.add(getNameProjection("Product-model-1"));
        projections.add(getNameProjection("Product-model-2"));
        return projections;
    }

    public static List<UniqueProjection> getProductProjections() {
        List<UniqueProjection> projections = new ArrayList<>();
        projections.add(getNameProjection("Product-0"));
        projections.add(getNameProjection("Product-1"));
        projections.add(getNameProjection("Product-2"));
        return projections;
    }

    private static UniqueProjection getNameProjection(String name) {
        UniqueProjection uniqueColumnProjection = factory.createProjection(UniqueProjection.class);
        uniqueColumnProjection.setUniqueColumn(name);
        return uniqueColumnProjection;
    }
}
