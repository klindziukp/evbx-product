package com.evbx.product.exception;

public class ItemAlreadyPresentException extends RuntimeException {

    public ItemAlreadyPresentException(String uniqueParam) {
        super("Item already presents with unique param = " + uniqueParam);
    }
}
