package com.evbx.product.util;

import com.evbx.product.model.UpdatableEntity;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public final class ItemUtil {

    private ItemUtil() {
    }

    public static <T> T getRandomItem(List<T> items) {
        return items.get(new Random().nextInt(items.size()));
    }

    public static <T extends UpdatableEntity> List<Long> getIdsFromList(List<T> items) {
        return items.stream().map(UpdatableEntity::getId).collect(Collectors.toList());
    }
}
