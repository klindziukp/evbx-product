package com.evbx.product.data;

import com.evbx.product.model.data.ItemData;
import com.evbx.product.model.domain.Product;
import com.evbx.product.model.dto.ProductDto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public final class ProductTestDataStorage {

    private ProductTestDataStorage() {
    }

    public static ItemData<ProductDto> getProductItemDataDto() {
        return new ItemData<>(getTestProductsDto());
    }

    public static List<ProductDto> getTestProductsDto() {
        List<ProductDto> productsDto = new ArrayList<>();

        Product firstProduct = getTestProducts().get(0);
        ProductDto firstProductDto = new ProductDto();
        firstProductDto.setId(firstProduct.getId()).setProductName(firstProduct.getProductName()).setProductModels(
                Collections.singletonList(ProductModelTestDataStorage.getProductModelsDto().get(0)));
        productsDto.add(firstProductDto);

        Product secondProduct = getTestProducts().get(1);
        ProductDto secondProductDto = new ProductDto();
        secondProductDto.setId(secondProduct.getId()).setProductName(secondProduct.getProductName()).setProductModels(
                Collections.singletonList(ProductModelTestDataStorage.getProductModelsDto().get(0)));
        productsDto.add(secondProductDto);

        Product thirdProduct = getTestProducts().get(2);
        ProductDto thirdProductDto = new ProductDto();
        thirdProductDto.setId(thirdProduct.getId()).setProductName(thirdProduct.getProductName()).setProductModels(
                Collections.singletonList(ProductModelTestDataStorage.getProductModelsDto().get(0)));
        productsDto.add(thirdProductDto);
        return productsDto;
    }

    public static List<Product> getTestProducts() {
        List<Product> products = new ArrayList<>();

        Product firstProduct = new Product();
        firstProduct.setId(100L);
        firstProduct.setProductName("Product-0").setModels(
                Collections.singletonList(ProductModelTestDataStorage.getProductModels().get(0))).setCreatedBy(
                "script-0");
        products.add(firstProduct);

        Product secondProduct = new Product();
        secondProduct.setId(101L);
        secondProduct.setProductName("Product-1").setModels(
                Collections.singletonList(ProductModelTestDataStorage.getProductModels().get(1))).setCreatedBy(
                "script-1");
        products.add(secondProduct);

        Product thirdProduct = new Product();
        thirdProduct.setId(102L);
        thirdProduct.setProductName("Product-2").setModels(
                Collections.singletonList(ProductModelTestDataStorage.getProductModels().get(2))).setCreatedBy(
                "script-2");
        products.add(thirdProduct);
        return products;
    }

    public static Product getMutationProduct() {
        Product firstProduct = new Product();
        firstProduct.setProductName("Product-777").setModels(
                Collections.singletonList(ProductModelTestDataStorage.getProductModels().get(0)));
        firstProduct.setCreatedAt(Calendar.getInstance().getTime());
        return firstProduct;
    }
}
