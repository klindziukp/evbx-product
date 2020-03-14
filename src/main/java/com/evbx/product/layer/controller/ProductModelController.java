package com.evbx.product.layer.controller;

import com.evbx.product.constant.Item;
import com.evbx.product.layer.service.ProductModelService;
import com.evbx.product.model.domain.ProductModel;
import com.evbx.product.model.data.ItemData;
import com.evbx.product.model.dto.ProductModelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ProductModelController {

    private ProductModelService productModelService;

    @Autowired
    public ProductModelController(ProductModelService productModelService) {
        this.productModelService = productModelService;
    }

    @GetMapping(value = "/product-models", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemData<ProductModelDto>> getAllProductModels() {
        return ResponseEntity.ok(productModelService.getAllProductModels());
    }

    @GetMapping(value = "/product-models/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductModelDto> getProduct(@PathVariable long id) {
        return ResponseEntity.ok(productModelService.getProductModel(id));
    }

    @PostMapping(value = "/product-models", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addProduct(@Valid @RequestBody ProductModel productModel) {
        return ResponseEntity.ok(productModelService.save(productModel));
    }

    @PatchMapping(value = "/product-models/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateDescription(@PathVariable long id, @RequestBody ProductModel productModel) {
        return ResponseEntity.ok(productModelService.update(id, productModel));
    }

    @DeleteMapping(value = "/product-models/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteReport(@PathVariable long id) {
        productModelService.deleteById(id);
        return ResponseEntity.ok(
                String.format("%s item with id = %d successfully deleted.", Item.PRODUCT_MODEL, id));
    }
}
