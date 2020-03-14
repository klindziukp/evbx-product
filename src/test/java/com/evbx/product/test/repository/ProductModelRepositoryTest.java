package com.evbx.product.test.repository;

import com.evbx.product.constant.Item;
import com.evbx.product.data.ProductModelTestDataStorage;
import com.evbx.product.util.TestDataUtil;
import com.evbx.product.data.TestDataProjectionStorage;
import com.evbx.product.util.ItemUtil;
import com.evbx.product.exception.ItemNotFoundException;
import com.evbx.product.layer.repository.ProductModelRepository;
import com.evbx.product.layer.repository.projection.UniqueProjection;
import com.evbx.product.model.domain.ProductModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.evbx.product.support.Step.__GIVEN;
import static com.evbx.product.support.Step.__THEN;
import static com.evbx.product.support.Step.__WHEN;
import static org.assertj.core.api.Assertions.assertThat;

public class ProductModelRepositoryTest extends BaseRepositoryTest {

    private ProductModelRepository productModelRepository;

    @Autowired
    public ProductModelRepositoryTest(ProductModelRepository productModelRepository) {
        this.productModelRepository = productModelRepository;
    }

    @Test
    void findAllProductModelsTest() {
        __GIVEN();
        List<ProductModel> expectedProductModels = ProductModelTestDataStorage.getProductModels();
        __WHEN();
        List<ProductModel> actualProductModels = productModelRepository.findAll();
        TestDataUtil.nullifyNonPresentItemsForProductModel(actualProductModels);
        __THEN();
        assertThat(actualProductModels).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(expectedProductModels);
    }

    @Test
    void findProductModelByIdTest() {
        __GIVEN();
        ProductModel expectedProductModel = ItemUtil.getRandomItem(ProductModelTestDataStorage.getProductModels());
        __WHEN();
        Long id = expectedProductModel.getId();
        ProductModel actualProductModel = productModelRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException(Item.PRODUCT_MODEL, id));
        TestDataUtil.nullifyNonPresentItemsForProductModel(actualProductModel);
        __THEN();
        assertThat(actualProductModel).usingRecursiveComparison()
                .isEqualTo(expectedProductModel);
    }

    @Test
    void saveProductModelTest() {
        __GIVEN();
        ProductModel mutationProductModel = ProductModelTestDataStorage.getMutationProductModel();
        __WHEN();
        ProductModel savedProductModel = productModelRepository.save(mutationProductModel);
        Long id = savedProductModel.getId();
        ProductModel extractedProductModel = productModelRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException(Item.PRODUCT_MODEL, id));
        TestDataUtil.nullifyNonPresentItemsForProductModel(savedProductModel);
        TestDataUtil.nullifyNonPresentItemsForProductModel(extractedProductModel);
        __THEN();
        assertThat(savedProductModel).usingRecursiveComparison().isEqualTo(extractedProductModel);
    }

    @Test
    void updateProductModelTest() {
        __GIVEN();
        ProductModel mutationProductModel = ProductModelTestDataStorage.getMutationProductModel();
        ProductModel expectedProductModel = ItemUtil.getRandomItem(productModelRepository.findAll());
        expectedProductModel.setModelName(mutationProductModel.getModelName());
        int itemsSizeBefore = productModelRepository.findAll().size();
        __WHEN();
        ProductModel savedProductModel = productModelRepository.save(expectedProductModel);
        Long id = savedProductModel.getId();
        int itemsSizeAfter = productModelRepository.findAll().size();
        ProductModel extractedProductModel = productModelRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException(Item.PRODUCT_MODEL, id));
        TestDataUtil.nullifyNonPresentItemsForProductModel(extractedProductModel);
        __THEN();
        assertThat(extractedProductModel).usingRecursiveComparison().isEqualTo(expectedProductModel);
        assertThat(itemsSizeAfter).isEqualTo(itemsSizeBefore);
    }

    @Test
    void getUniqueColumnNamesTest() {
        __GIVEN();
        List<UniqueProjection> expectedDescProjections = TestDataProjectionStorage.getProductModelProjections();
        __WHEN();
        List<UniqueProjection> actualDescNameProjections = productModelRepository.getUniqueColumnNames();
        __THEN();
        projectionVerificationService.verifyNameProjections(actualDescNameProjections, expectedDescProjections);
    }

    @Test
    void deleteProductModelByIdTest() {
        __GIVEN();
        List<ProductModel> productModelsBeforeDelete = productModelRepository.findAll();
        __WHEN();
        ProductModel productModelToDelete = ItemUtil.getRandomItem(productModelsBeforeDelete);
        productModelRepository.deleteById(productModelToDelete.getId());
        __THEN();
        List<ProductModel> productModelsAfterDelete = productModelRepository.findAll();
        assertThat(productModelsAfterDelete).doesNotContain(productModelToDelete);
    }

    @Test
    void getProductModelsIdsTest() {
        __GIVEN();
        List<Long> expectedIds = ItemUtil.getIdsFromList(ProductModelTestDataStorage.getProductModels());
        __WHEN();
        List<Long> actualIds = productModelRepository.getAllProductIds();
        __THEN();
        assertThat(actualIds).isEqualTo(expectedIds);
    }

    @Test
    void existsProductModelById() {
        __GIVEN();
        Long id = ItemUtil.getRandomItem(ProductModelTestDataStorage.getProductModels()).getId();
        __WHEN();
        boolean isExists = productModelRepository.existsById(id);
        __THEN();
        assertThat(isExists).isTrue();
    }
}
