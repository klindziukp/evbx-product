package com.evbx.product.model.data;

import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
public class ItemData<T> {

    private List<T> items;
    private long total;

    public ItemData(List<T> items){
        this.items = Optional.ofNullable(items).orElse(Collections.emptyList());
        this.total = this.items.size();
    }
}
