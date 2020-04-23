package com.evbx.product.layer.controller;

import com.evbx.product.constant.Item;
import com.evbx.product.layer.service.ProductService;
import com.evbx.product.model.domain.Product;
import com.evbx.product.model.data.ItemData;
import com.evbx.product.model.dto.ProductDto;
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
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemData<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> getProduct(@PathVariable long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PostMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @PatchMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.update(id, product));
    }

    @DeleteMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteProduct(@PathVariable long id) {
        productService.deleteById(id);
        return ResponseEntity.ok(
                String.format("%s item with id = %d successfully deleted.", Item.PRODUCT, id));
    }
}
