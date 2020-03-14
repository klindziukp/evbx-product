package com.evbx.product.test.controller;

import com.evbx.product.data.ProductTestDataStorage;
import com.evbx.product.layer.controller.ProductController;
import com.evbx.product.layer.service.ProductService;
import com.evbx.product.model.domain.Product;
import com.evbx.product.util.ItemUtil;
import com.evbx.product.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends BaseControllerTest {

    private MockMvc mockMvc;

    ProductControllerTest() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(getMockedProductService())).build();
    }

    @Test
    void getProductTest() throws Exception {
        mockMvc.perform(get("/products/" + 100L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllProductsTest() throws Exception {
        mockMvc.perform(get("/products")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(3)))
                .andExpect(jsonPath("$.total", is(3)));
    }

    @Test
    void addProductTest() throws Exception {
        Product mutationProduct = ProductTestDataStorage.getMutationProduct();
        mockMvc.perform(post("/products")
                .content(JsonUtil.toJson(mutationProduct))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", is("Product-1")));
    }

    @Test
    void updateProductModelTest() throws Exception {
        Product mutationProduct = ItemUtil.getRandomItem(ProductTestDataStorage.getTestProducts());
        String productModelName = "Product-2";
        mutationProduct.setProductName(productModelName);
        mockMvc.perform(patch("/products/" + 100L)
                .content(JsonUtil.toJson(mutationProduct))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName", is(productModelName)));
    }

    @Test
    void deleteProductModelTest() throws Exception {
        mockMvc.perform(delete("/products/" + 102L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("PRODUCT item with id = 102 successfully deleted.")));
    }

    ProductService getMockedProductService() {
        ProductService productModelService = Mockito.mock(ProductService.class);
        Mockito.when(productModelService.getAllProducts()).thenReturn(ProductTestDataStorage.getProductItemDataDto());
        Mockito.when(productModelService.getProduct(ArgumentMatchers.anyLong())).thenReturn(
                ProductTestDataStorage.getTestProductsDto().get(0));
        Mockito.when(productModelService.save(ArgumentMatchers.any(Product.class))).thenReturn(
                ProductTestDataStorage.getTestProducts().get(1));
        Mockito.when(productModelService.update(ArgumentMatchers.anyLong(), ArgumentMatchers.any(Product.class)))
                .thenReturn(ProductTestDataStorage.getTestProducts().get(2));
        return productModelService;
    }
}