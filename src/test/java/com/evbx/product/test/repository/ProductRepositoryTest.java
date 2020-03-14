package com.evbx.product.test.repository;

import com.evbx.product.constant.Item;
import com.evbx.product.data.ProductTestDataStorage;
import com.evbx.product.util.TestDataUtil;
import com.evbx.product.data.TestDataProjectionStorage;
import com.evbx.product.util.ItemUtil;
import com.evbx.product.exception.ItemNotFoundException;
import com.evbx.product.layer.repository.ProductRepository;
import com.evbx.product.layer.repository.projection.UniqueProjection;
import com.evbx.product.model.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.evbx.product.support.Step.__GIVEN;
import static com.evbx.product.support.Step.__THEN;
import static com.evbx.product.support.Step.__WHEN;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryTest extends BaseRepositoryTest {

    private ProductRepository productRepository;

    @Autowired
    public ProductRepositoryTest(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Test
    void findAllProductsTest() {
        __GIVEN();
        List<Product> expectedProducts = ProductTestDataStorage.getTestProducts();
        __WHEN();
        List<Product> actualProducts = productRepository.findAll();
        TestDataUtil.nullifyNonPresentItemsForProduct(actualProducts);
        __THEN();
       jpaVerificationService.verifyProducts(actualProducts, expectedProducts);
    }

    @Test
    void findProductByIdTest() {
        __GIVEN();
        Product expectedProduct = ItemUtil.getRandomItem(ProductTestDataStorage.getTestProducts());
        __WHEN();
        Long id = expectedProduct.getId();
        Product actualProduct = productRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException(Item.PRODUCT, id));
        TestDataUtil.nullifyNonPresentItemsForProduct(actualProduct);
        __THEN();
        jpaVerificationService.verifyProduct(actualProduct, expectedProduct);
    }

    @Test
    void saveProductTest() {
        __GIVEN();
        Product mutationProduct = ProductTestDataStorage.getMutationProduct();
        __WHEN();
        Product savedProduct = productRepository.save(mutationProduct);
        Long id = savedProduct.getId();
        Product extractedProduct = productRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException(Item.PRODUCT, id));
        TestDataUtil.nullifyNonPresentItemsForProduct(extractedProduct);
        __THEN();
        jpaVerificationService.verifyProduct(extractedProduct, savedProduct);
    }

    @Test
    void updateProductTest() {
        __GIVEN();
        Product mutationProduct = ProductTestDataStorage.getMutationProduct();
        Product expectedProduct = ItemUtil.getRandomItem(productRepository.findAll());
        expectedProduct.setProductName(mutationProduct.getProductName());
        int itemsSizeBefore = productRepository.findAll().size();
        __WHEN();
        Product savedProduct = productRepository.save(expectedProduct);
        Long id = savedProduct.getId();
        int itemsSizeAfter = productRepository.findAll().size();
        Product extractedProduct = productRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException(Item.PRODUCT, id));
        TestDataUtil.nullifyNonPresentItemsForProduct(extractedProduct);
        __THEN();
        jpaVerificationService.verifyProduct(extractedProduct, savedProduct);
        assertThat(itemsSizeAfter).isEqualTo(itemsSizeBefore);
    }

    @Test
    void getUniqueColumnNamesTest() {
        __GIVEN();
        List<UniqueProjection> expectedDescProjections = TestDataProjectionStorage.getProductProjections();
        __WHEN();
        List<UniqueProjection> actualDescNameProjections = productRepository.getUniqueColumnNames();
        __THEN();
        projectionVerificationService.verifyNameProjections(actualDescNameProjections, expectedDescProjections);
    }

    @Test
    void deleteProductByIdTest() {
        __GIVEN();
        List<Product> productModelsBeforeDelete = productRepository.findAll();
        __WHEN();
        Product productModelToDelete = ItemUtil.getRandomItem(productModelsBeforeDelete);
        productRepository.deleteById(productModelToDelete.getId());
        __THEN();
        List<Product> productModelsAfterDelete = productRepository.findAll();
        assertThat(productModelsAfterDelete).doesNotContain(productModelToDelete);
    }

    @Test
    void existsProductById() {
        __GIVEN();
        Long id = ItemUtil.getRandomItem(ProductTestDataStorage.getTestProducts()).getId();
        __WHEN();
        boolean isExists = productRepository.existsById(id);
        __THEN();
        assertThat(isExists).isTrue();
    }
}
