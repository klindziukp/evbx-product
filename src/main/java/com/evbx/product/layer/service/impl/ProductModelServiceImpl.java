package com.evbx.product.layer.service.impl;

import com.evbx.product.constant.Item;
import com.evbx.product.exception.ItemNotFoundException;
import com.evbx.product.layer.repository.ProductModelRepository;
import com.evbx.product.layer.service.ApiClientService;
import com.evbx.product.layer.service.ProductModelService;
import com.evbx.product.model.domain.ProductModel;
import com.evbx.product.model.data.ItemData;
import com.evbx.product.model.dto.ProductModelDto;
import com.evbx.product.util.AuthUtil;
import com.evbx.product.util.UpdateUtil;
import com.evbx.product.util.ValidationUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ProductModelServiceImpl implements ProductModelService {

    private static final Logger LOGGER = LogManager.getLogger(ProductModelServiceImpl.class);
    private ProductModelRepository productModelRepository;
    private ApiClientService apiClientService;

    @Autowired
    public ProductModelServiceImpl(ProductModelRepository productModelRepository, ApiClientService apiClientService) {
        this.productModelRepository = productModelRepository;
        this.apiClientService = apiClientService;
    }

    @Override
    public ItemData<ProductModelDto> getAllProductModels() {
        List<ProductModel> productModels = productModelRepository.findAll();
        return new ItemData<>(composeProductModelsDto(productModels));
    }

    @Override
    public ProductModelDto getProductModel(long id) {
        ProductModel productModel = productModelRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(Item.PRODUCT_MODEL, id));
        return composeProductModelDto(productModel);
    }

    @Override
    public ProductModel save(ProductModel productModel) {
        verifyProductModel(productModel);
        productModel.setCreatedBy(AuthUtil.getUserName());
        ProductModel savedProduct = productModelRepository.save(productModel);
        LOGGER.info("Product model with id = {} saved successfully", savedProduct.getId());
        return savedProduct;
    }

    @Override
    public ProductModel update(long id, ProductModel productModel) {
        ProductModel persistedProductModel = productModelRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(Item.PRODUCT_MODEL, id));
        verifyProductModel(productModel);
        UpdateUtil.updateItem(productModel, persistedProductModel);
        ProductModel updatedProduct = productModelRepository.save(persistedProductModel);
        LOGGER.info("Product model with id = {} updated successfully", updatedProduct.getId());
        return updatedProduct;
    }

    @Override
    public void deleteById(long id) {
        if (!productModelRepository.existsById(id)) {
            throw new ItemNotFoundException(Item.PRODUCT_MODEL, id);
        }
        productModelRepository.deleteById(id);
        LOGGER.info("Product model with id = {} deleted successfully", id);
    }

    public List<ProductModelDto> composeProductModelsDto(List<ProductModel> productModels) {
        List<CompletableFuture<ProductModelDto>> futures = new ArrayList<>(productModels.size());
        productModels.forEach(productModel -> futures.add(composeFutureTask(productModel)));
        CompletableFuture<Void> combinedFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        waitForAllTasksFinished(combinedFuture);

        List<ProductModelDto> productModelsDto = new ArrayList<>();
        futures.forEach(future -> productModelsDto.add(getResultFromFuture(future)));
        return productModelsDto;
    }

    private void waitForAllTasksFinished(CompletableFuture<Void> combinedFuture) {
        try {
            combinedFuture.get();
        } catch (InterruptedException | ExecutionException inEx) {
            Thread.currentThread().interrupt();
            LOGGER.warn("Unable to complete 'Combined Future' task");
        }
    }

    private ProductModelDto getResultFromFuture(CompletableFuture<ProductModelDto> future) {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException inEx) {
            Thread.currentThread().interrupt();
            LOGGER.warn("Unable to get result from 'Future' task");
        }
        // return null for 'Resource'
        return null;
    }

    private CompletableFuture<ProductModelDto> composeFutureTask(ProductModel productModel) {
        return CompletableFuture.supplyAsync(() -> composeProductModelDto(productModel));
    }

    private ProductModelDto composeProductModelDto(ProductModel productModel) {
        ProductModelDto productModelDto = new ProductModelDto();
        productModelDto.setId(productModel.getId());
        productModelDto.setModelName(productModel.getModelName());
        productModelDto.setDescriptions(productModel.getDescriptions());
        return apiClientService.composeProductModelDto(productModel, productModelDto);
    }

    private void verifyProductModel(ProductModel productModel) {
        verifyProductId(productModel);
        verifyModelPresence(productModel);
        verifyResourceIds(productModel);
    }

    private void verifyProductId(ProductModel productModel) {
        Long productId = productModel.getProductId();
        if (Objects.nonNull(productId)) {
            ValidationUtil.validateItemId(Item.PRODUCT, productModelRepository.getAllProductIds(), productId);
        }
    }

    private void verifyResourceIds(ProductModel productModel) {
        apiClientService.verifyResourceIds(productModel);
    }

    private void verifyModelPresence(ProductModel productModel) {
        String productModelName = productModel.getModelName();
        if (Objects.nonNull(productModelName)) {
            ValidationUtil.validateResourceName(productModelName, productModelRepository.getUniqueColumnNames());
        }
    }
}
