package com.evbx.product.util;

import com.evbx.product.constant.Item;
import com.evbx.product.exception.ItemAlreadyPresentException;
import com.evbx.product.exception.ItemNotFoundException;
import com.evbx.product.layer.repository.projection.UniqueProjection;

import java.util.List;
import java.util.Objects;

public final class ValidationUtil {

    private ValidationUtil() {

    }

    public static void validateResourceName(String name, List<UniqueProjection> projections) {
        for (UniqueProjection nameProjection : projections) {
            String projectionName = nameProjection.getUniqueColumn();
            if (Objects.nonNull(projectionName) && projectionName.equals(name)) {
                throw new ItemAlreadyPresentException(name);
            }
        }
    }

    public static void validateItemId(Item item, List<Long> persistedIds, long id) {
        if (!persistedIds.contains(id)) {
            throw new ItemNotFoundException(item, id);
        }
    }
}
