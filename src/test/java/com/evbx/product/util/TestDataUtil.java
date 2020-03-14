package com.evbx.product.util;

import com.evbx.product.model.UpdatableEntity;
import com.evbx.product.model.domain.Description;
import com.evbx.product.model.domain.Product;
import com.evbx.product.model.domain.ProductModel;

import java.util.List;

public final class TestDataUtil {

    private TestDataUtil() {

    }

    public static void nullifyNonPresentItemsForProduct(List<Product> products) {
        products.forEach(TestDataUtil::nullifyNonPresentItemsForProduct);
    }

    public static void nullifyNonPresentItemsForProduct(Product product) {
        nullifyUpdatableItemItems(product);
        nullifyNonPresentItemsForProductModel(product.getModels());
    }

    public static void nullifyNonPresentItemsForProductModel(List<ProductModel> productModels) {
        productModels.forEach(TestDataUtil::nullifyNonPresentItemsForProductModel);
    }

    public static void nullifyNonPresentItemsForProductModel(ProductModel productModel) {
        productModel.setProduct(null);
        nullifyUpdatableItemItems(productModel);
        nullifyNonPresentItemsForDescriptions(productModel.getDescriptions());
    }

    public static void nullifyNonPresentItemsForDescriptions(List<Description> descriptions) {
        descriptions.forEach(TestDataUtil::nullifyNonPresentItemsForDescription);
    }

    public static void nullifyNonPresentItemsForDescription(Description description) {
        description.setProductModel(null);
        nullifyUpdatableItemItems(description);
    }

    private static <T extends UpdatableEntity> void nullifyUpdatableItemItems(T updatableEntity) {
        updatableEntity.setCreatedAt(null);
        updatableEntity.setUpdatedAt(null);
    }
}
