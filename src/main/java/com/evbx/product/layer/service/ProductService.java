package com.evbx.product.layer.service;

import com.evbx.product.model.domain.Product;
import com.evbx.product.model.data.ItemData;
import com.evbx.product.model.dto.ProductDto;

public interface ProductService {

    ItemData<ProductDto> getAllProducts();
    ProductDto getProduct(long id);
    Product save(Product product);
    Product update(long id, Product product);
    void deleteById(long id);
}
