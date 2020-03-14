package com.evbx.product.layer.service;

import com.evbx.product.model.domain.Description;
import com.evbx.product.model.data.ItemData;

public interface DescriptionService {

    ItemData<Description> getAllDescriptions();
    Description getDescription(long id);
    Description save(Description description);
    Description update(long id, Description description);
    void deleteById(long id);
}
