package com.evbx.product.support;

import org.apache.commons.lang3.ArrayUtils;

public final class Ignore {

    private Ignore() {
    }

    public static String[] getUpdatableEntityFields() {
        return new String[] { "id", "createdAt", "updatedAt", "deletedAt", "updatedBy", "deletedBy" };
    }

    public static String[] getProductModelIgnoreFields() {
        return ArrayUtils.addAll(getUpdatableEntityFields(), "productModel");
    }
}
