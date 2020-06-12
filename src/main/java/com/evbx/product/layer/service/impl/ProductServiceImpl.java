package com.evbx.product.layer.service.impl;

import com.evbx.product.constant.Item;
import com.evbx.product.exception.ItemNotFoundException;
import com.evbx.product.layer.repository.ProductRepository;
import com.evbx.product.layer.service.ProductModelService;
import com.evbx.product.layer.service.ProductService;
import com.evbx.product.model.domain.Product;
import com.evbx.product.model.data.ItemData;
import com.evbx.product.model.dto.ProductDto;
import com.evbx.product.model.dto.ProductModelDto;
import com.evbx.product.util.AuthUtil;
import com.evbx.product.util.UpdateUtil;
import com.evbx.product.util.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LogManager.getLogger(ProductServiceImpl.class);
    private ProductModelService productModelService;
    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductModelService productModelService) {
        this.productModelService = productModelService;
        this.productRepository = productRepository;
    }

    @Override
    @Cacheable(value= "allProductsCache", unless= "#result.getTotal() == 0")
    public ItemData<ProductDto> getAllProducts() {
        LOGGER.info("Get all products");
        List<ProductDto> products = new ArrayList<>();
        List<Product> persistedProducts = productRepository.findAll();
        composeProductsDto(products, persistedProducts);
        return new ItemData<>(products);
    }

    @Override
    @Cacheable(value= "productCache", key= "'product'+#id")
    public ProductDto getProduct(long id) {
        ProductDto productDto = new ProductDto();
        Product product = productRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(Item.PRODUCT, id));
        BeanUtils.copyProperties(product, productDto);
        productDto.setProductModels(composeProductModelsDto(product));
        LOGGER.info("Get product with id = '{}'", id);
        return productDto;
    }

    @Override
    @Caching(put = { @CachePut(value = "productCache", key = "'product'+#productDto.id") },
             evict = { @CacheEvict(value = "allProductsCache", allEntries = true) })
    public Product save(Product product) {
        verifyProductPresence(product);
        product.setCreatedBy(AuthUtil.getUserName());
        Product savedProduct = productRepository.save(product);
        LOGGER.info("Product with id = {} saved successfully", savedProduct.getId());
        return savedProduct;
    }

    @Override
    @Caching(put = { @CachePut(value = "productCache", key = "'product'+#productDto.id") },
             evict = { @CacheEvict(value = "allProductsCache", allEntries = true) })
    public Product update(long id, Product product) {
        Product persistedProductModel = productRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException(Item.PRODUCT, id));
        verifyProductPresence(product);
        UpdateUtil.updateItem(product, persistedProductModel);
        Product updatedProduct = productRepository.save(persistedProductModel);
        LOGGER.info("Product with id = {} updated successfully", updatedProduct.getId());
        return updatedProduct;
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "productCache", key = "'product'+#id"),
            @CacheEvict(value = "allProductsCache", allEntries = true) })
    public void deleteById(long id) {
        if (!productRepository.existsById(id)) {
            throw new ItemNotFoundException(Item.PRODUCT, id);
        }
        productRepository.deleteById(id);
        LOGGER.info("Product with id = {} deleted successfully", id);
    }

    private List<ProductModelDto> composeProductModelsDto(Product product) {
        return productModelService.composeProductModelsDto(product.getModels());
    }

    private void composeProductsDto(List<ProductDto> products, List<Product> persistedProducts) {
        for (Product product : persistedProducts) {
            ProductDto productDto = new ProductDto();
            BeanUtils.copyProperties(product, productDto);
            productDto.setProductModels(composeProductModelsDto(product));
            products.add(productDto);
        }
    }

    private void verifyProductPresence(Product product) {
        String productModelName = product.getProductName();
        if (Objects.nonNull(productModelName)) {
            ValidationUtil.validateResourceName(productModelName, productRepository.getUniqueColumnNames());
        }
    }
}
