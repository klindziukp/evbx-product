package com.evbx.product.layer.service;

import com.evbx.product.model.domain.ProductModel;
import com.evbx.product.model.dto.ProductModelDto;

public interface ApiClientService {

    void verifyResourceIds(ProductModel productModel);
    ProductModelDto composeProductModelDto(ProductModel productModel, ProductModelDto productModelDto);
}


