package com.evbx.product.support;

import com.evbx.product.model.dto.ProductDto;
import com.evbx.product.model.dto.ProductModelDto;
import com.evbx.product.util.TestDataUtil;
import com.evbx.product.model.domain.Description;
import com.evbx.product.model.domain.Product;
import com.evbx.product.model.domain.ProductModel;
import org.assertj.core.api.SoftAssertions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public final class JpaVerificationService {

    public void verifyProducts(List<Product> actual, List<Product> expected) {
        TestDataUtil.nullifyNonPresentItemsForProduct(actual);
        SoftAssertions softAssertions = new SoftAssertions();
        for (int i = 0; i < actual.size(); i++) {
            softAssertions.assertThat(actual.get(i)).usingRecursiveComparison().ignoringOverriddenEqualsForTypes(
                    ProductModel.class, Description.class).isEqualTo(expected.get(i));
        }
        softAssertions.assertAll();
    }

    public void verifyProductsDto(List<ProductDto> actual, List<ProductDto> expected) {
        SoftAssertions softAssertions = new SoftAssertions();
        for (int i = 0; i < actual.size(); i++) {
            softAssertions.assertThat(actual.get(i)).usingRecursiveComparison().ignoringOverriddenEqualsForTypes(
                    ProductModelDto.class, Description.class).isEqualTo(expected.get(i));
        }
        softAssertions.assertAll();
    }

    public void verifyProduct(Product actual, Product expected) {
        TestDataUtil.nullifyNonPresentItemsForProduct(actual);
        TestDataUtil.nullifyNonPresentItemsForProduct(expected);
        assertThat(actual).usingRecursiveComparison().ignoringOverriddenEqualsForTypes(ProductModel.class,
                Description.class).isEqualTo(expected);
    }

    public void verifyProductDto(ProductDto actual, ProductDto expected) {
        assertThat(actual).usingRecursiveComparison().ignoringOverriddenEqualsForTypes(ProductModelDto.class,
                Description.class).isEqualTo(expected);
    }
}
