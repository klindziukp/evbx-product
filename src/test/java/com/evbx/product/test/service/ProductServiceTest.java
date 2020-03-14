package com.evbx.product.test.service;

import com.evbx.product.data.ProductModelTestDataStorage;
import com.evbx.product.data.ProductTestDataStorage;
import com.evbx.product.exception.ItemNotFoundException;
import com.evbx.product.layer.repository.ProductModelRepository;
import com.evbx.product.layer.repository.ProductRepository;
import com.evbx.product.layer.service.ApiClientService;
import com.evbx.product.layer.service.ProductModelService;
import com.evbx.product.layer.service.ProductService;
import com.evbx.product.layer.service.impl.ProductModelServiceImpl;
import com.evbx.product.layer.service.impl.ProductServiceImpl;
import com.evbx.product.model.domain.Product;
import com.evbx.product.model.domain.ProductModel;
import com.evbx.product.model.dto.ProductDto;
import com.evbx.product.model.dto.ProductModelDto;
import com.evbx.product.util.ItemUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.evbx.product.support.Step.__GIVEN;
import static com.evbx.product.support.Step.__THEN;
import static com.evbx.product.support.Step.__WHEN;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductServiceTest extends BaseServiceTest {

    private ProductService productService;

    private ProductModelRepository productModelRepository;
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceTest(ProductModelRepository productModelRepository, ProductRepository productRepository) {
        ProductModelService productModelService = new ProductModelServiceImpl(productModelRepository, getMockApiClientService());
        this.productService = new ProductServiceImpl(productRepository, productModelService);
    }

    @Test
    void findAllProductsTest() {
        __GIVEN();
        List<ProductDto> expectedProducts = ProductTestDataStorage.getTestProductsDto();
        __WHEN();
        List<ProductDto> actualProducts = productService.getAllProducts().getItems();
        __THEN();
       jpaVerificationService.verifyProductsDto(actualProducts, expectedProducts);
    }

    @Test
    void findProductByIdTest() {
        __GIVEN();
        ProductDto expectedProduct = ItemUtil.getRandomItem(ProductTestDataStorage.getTestProductsDto());
        __WHEN();
        Long id = expectedProduct.getId();
        ProductDto actualProduct = productService.getProduct(id);
        __THEN();
        jpaVerificationService.verifyProductDto(actualProduct, expectedProduct);
    }

    @Test
    void saveProductTest() {
        __GIVEN();
        mockSecurityContext();
        Product mutationProduct = ProductTestDataStorage.getMutationProduct();
        __WHEN();
        Product savedProduct = productService.save(mutationProduct);
        __THEN();
        jpaVerificationService.verifyProduct(savedProduct, mutationProduct);
    }

    @Test
    void updateProductTest() {
        __GIVEN();
        mockSecurityContext();
        Product mutationProduct = ProductTestDataStorage.getMutationProduct();
        Product expectedProduct = ItemUtil.getRandomItem(ProductTestDataStorage.getTestProducts());
        expectedProduct.setProductName(mutationProduct.getProductName());
        int itemsSizeBefore = productService.getAllProducts().getItems().size();
        __WHEN();
        Product savedProduct = productService.save(expectedProduct);
        int itemsSizeAfter = productService.getAllProducts().getItems().size();
        __THEN();
        jpaVerificationService.verifyProduct(expectedProduct, savedProduct);
        assertThat(itemsSizeAfter).isEqualTo(itemsSizeBefore);
    }

    @Test
    void deleteProductByIdTest() {
        __GIVEN();
        List<ProductDto> productModelsBeforeDelete = productService.getAllProducts().getItems();
        __WHEN();
        ProductDto productModelToDelete = ItemUtil.getRandomItem(productModelsBeforeDelete);
        productService.deleteById(productModelToDelete.getId());
        __THEN();
        List<ProductDto> productModelsAfterDelete = productService.getAllProducts().getItems();
        assertThat(productModelsAfterDelete.size()).isNotEqualTo(productModelsBeforeDelete.size());
    }

    @Test
    void itemNotFoundExceptionTest() {
        __GIVEN();
        Long maxId = productService.getAllProducts().getItems().stream().mapToLong(ProductDto::getId)
                .max().orElse(999L);
        __WHEN();
        long nonPresentId = maxId + 100L;
        __THEN();
        Assertions.assertThrows(ItemNotFoundException.class, () -> productService.getProduct(nonPresentId));
    }
}
