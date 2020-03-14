package com.evbx.product.test.service;

import com.evbx.product.data.ProductModelTestDataStorage;
import com.evbx.product.layer.repository.ProductModelRepository;
import com.evbx.product.layer.service.impl.ProductModelServiceImpl;
import com.evbx.product.model.dto.ProductModelDto;
import com.evbx.product.util.ItemUtil;
import com.evbx.product.exception.ItemNotFoundException;
import com.evbx.product.layer.service.ProductModelService;
import com.evbx.product.model.domain.ProductModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.evbx.product.support.Step.__GIVEN;
import static com.evbx.product.support.Step.__THEN;
import static com.evbx.product.support.Step.__WHEN;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ProductModelServiceTest extends BaseServiceTest {

    private ProductModelService productModelService;
    private ProductModelRepository productModelRepository;

    @Autowired
    public ProductModelServiceTest(ProductModelRepository productModelRepository) {
        this.productModelService = new ProductModelServiceImpl(productModelRepository, getMockApiClientService());
    }

    @Test
    void findAllProductModelsTest() {
        __GIVEN();
        List<ProductModelDto> expectedProductModels = ProductModelTestDataStorage.getProductModelsDto();
        __WHEN();
        List<ProductModelDto> actualProductModels = productModelService.getAllProductModels().getItems();
        __THEN();
        assertThat(actualProductModels).isEqualTo(expectedProductModels);
    }

    @Test
    void findProductModelByIdTest() {
        __GIVEN();
        ProductModelDto expectedProductModel = ProductModelTestDataStorage.getProductModelsDto().get(0);
        __WHEN();
        Long id = expectedProductModel.getId();
        ProductModelDto actualProductModel = productModelService.getProductModel(id);
        __THEN();
        assertThat(actualProductModel).isEqualTo(expectedProductModel);
    }

    @Test
    void saveProductModelTest() {
        __GIVEN();
        mockSecurityContext();
        ProductModel mutationProductModel = ProductModelTestDataStorage.getMutationProductModel();
        __WHEN();
        ProductModel savedProductModel = productModelService.save(mutationProductModel);
        __THEN();
        assertThat(savedProductModel).isEqualTo(mutationProductModel);
    }

    @Test
    void updateProductModelTest() {
        __GIVEN();
        mockSecurityContext();
        ProductModel mutationProductModel = ProductModelTestDataStorage.getMutationProductModel();
        ProductModel expectedProductModel = ItemUtil.getRandomItem(ProductModelTestDataStorage.getProductModels());
        expectedProductModel.setModelName(mutationProductModel.getModelName());
        int itemsSizeBefore = productModelService.getAllProductModels().getItems().size();
        __WHEN();
        ProductModel savedProductModel = productModelService.save(expectedProductModel);
        int itemsSizeAfter = productModelService.getAllProductModels().getItems().size();
        __THEN();
        assertThat(mutationProductModel.getModelName()).isEqualTo(savedProductModel.getModelName());
        assertThat(itemsSizeAfter).isEqualTo(itemsSizeBefore);
    }

    @Test
    void deleteProductModelByIdTest() {
        __GIVEN();
        List<ProductModelDto> descriptionsBeforeDelete = productModelService.getAllProductModels().getItems();
        __WHEN();
        ProductModelDto descriptionToDelete = ItemUtil.getRandomItem(descriptionsBeforeDelete);
        productModelService.deleteById(descriptionToDelete.getId());
        __THEN();
        List<ProductModelDto> descriptionsAfterDelete = productModelService.getAllProductModels().getItems();
        assertThat(descriptionsAfterDelete.size()).isNotEqualTo(descriptionsBeforeDelete.size());
    }

    @Test
    void itemNotFoundExceptionTest() {
        __GIVEN();
        Long maxId = productModelService.getAllProductModels().getItems().stream().mapToLong(ProductModelDto::getId)
                .max().orElse(999L);
        __WHEN();
        long nonPresentId = maxId + 100L;
        __THEN();
        Assertions.assertThrows(ItemNotFoundException.class, () -> productModelService.getProductModel(nonPresentId));
    }
}
