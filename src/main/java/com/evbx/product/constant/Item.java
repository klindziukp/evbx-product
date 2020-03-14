package com.evbx.product.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents Fields for domain models, used in exceptions
 */
@RequiredArgsConstructor
@Getter
public enum Item {

    PRODUCT("Product"),
    PRODUCT_MODEL("Product Model"),
    DESCRIPTION("Description Line"),
    E_BOOK("E-Book"),
    INDUSTRY_REPORT("Industry report"),
    SPECIFICATION("Specification");

    private final String itemName;
}
