package com.evbx.product.layer.service;

import com.evbx.product.model.domain.ProductModel;
import com.evbx.product.model.data.ItemData;
import com.evbx.product.model.dto.ProductModelDto;

import java.util.List;

public interface ProductModelService {

    ItemData<ProductModelDto> getAllProductModels();
    ProductModelDto getProductModel(long id);
    ProductModel save(ProductModel productModel);
    ProductModel update(long id, ProductModel productModel);
    List<ProductModelDto> composeProductModelsDto(List<ProductModel> productModels);
    void deleteById(long id);
}


