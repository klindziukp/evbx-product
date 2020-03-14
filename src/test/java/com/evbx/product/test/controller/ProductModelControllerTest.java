package com.evbx.product.test.controller;

import com.evbx.product.data.ProductModelTestDataStorage;
import com.evbx.product.layer.controller.ProductModelController;
import com.evbx.product.layer.service.ProductModelService;
import com.evbx.product.model.domain.ProductModel;
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

class ProductModelControllerTest extends BaseControllerTest {

    private MockMvc mockMvc;

    ProductModelControllerTest() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ProductModelController(getMockedProductModelService()))
                .build();
    }

    @Test
    void getProductModelTest() throws Exception {
        mockMvc.perform(get("/product-models/" + 100L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllProductModelTest() throws Exception {
        mockMvc.perform(get("/product-models")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(3)))
                .andExpect(jsonPath("$.total", is(3)));
    }

    @Test
    void addProductModelTest() throws Exception {
        ProductModel mutationProductModel = ProductModelTestDataStorage.getMutationProductModel();
        mockMvc.perform(post("/product-models")
                .content(JsonUtil.toJson(mutationProductModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelName", is("Product-model-0")));
    }

    @Test
    void updateProductModelTest() throws Exception {
        ProductModel mutationProductModel = ItemUtil.getRandomItem(ProductModelTestDataStorage.getProductModels());
        String productModelName = "Product-model-1";
        mutationProductModel.setModelName(productModelName);
        mockMvc.perform(patch("/product-models/" + 100L)
                .content(JsonUtil.toJson(mutationProductModel))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.modelName", is(productModelName)));
    }

    @Test
    void deleteProductModelTest() throws Exception {
        mockMvc.perform(delete("/product-models/" + 102L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("PRODUCT_MODEL item with id = 102 successfully deleted.")));
    }

    ProductModelService getMockedProductModelService() {
        ProductModelService productModelService = Mockito.mock(ProductModelService.class);
        Mockito.when(productModelService.getAllProductModels()).thenReturn(
                ProductModelTestDataStorage.getProductModelItemData());
        Mockito.when(productModelService.getProductModel(ArgumentMatchers.anyLong())).thenReturn(
                ProductModelTestDataStorage.getProductModelsDto().get(0));
        Mockito.when(productModelService.save(ArgumentMatchers.any(ProductModel.class))).thenReturn(
                ProductModelTestDataStorage.getProductModels().get(0));
        Mockito.when(productModelService.update(ArgumentMatchers.anyLong(), ArgumentMatchers.any(ProductModel.class)))
                .thenReturn(ProductModelTestDataStorage.getProductModels().get(1));
        return productModelService;
    }
}