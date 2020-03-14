package com.evbx.product.test.controller;

import com.evbx.product.data.DescriptionTestDataStorage;
import com.evbx.product.layer.controller.DescriptionController;
import com.evbx.product.model.domain.Description;
import com.evbx.product.util.ItemUtil;
import com.evbx.product.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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

class DescriptionControllerTest extends BaseControllerTest {

    private MockMvc mockMvc;
    private DescriptionController descriptionController;

    @Autowired
    public DescriptionControllerTest(DescriptionController descriptionController) {
        this.descriptionController = descriptionController;
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.descriptionController).build();
    }

    @Test
    void getDescriptionTest() throws Exception {
        mockMvc.perform(get("/descriptions/" + 100L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllDescriptionTest() throws Exception {
        mockMvc.perform(get("/descriptions")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(3)))
                .andExpect(jsonPath("$.total", is(3)));
    }

    @Test
    void addDescriptionTest() throws Exception {
        Description mutationDesc = DescriptionTestDataStorage.getMutationDesc();
        mockMvc.perform(post("/descriptions")
                .content(JsonUtil.toJson(mutationDesc))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descriptionLine", is(mutationDesc.getDescriptionLine())));
    }

    @Test
    void updateDescriptionTest() throws Exception {
        Description mutationDesc = ItemUtil.getRandomItem(DescriptionTestDataStorage.getTestDescriptions());
        String descriptionLine = "updatedDescriptionLine";
        mutationDesc.setDescriptionLine(descriptionLine);
        mockMvc.perform(patch("/descriptions/" + 100L)
                .content(JsonUtil.toJson(mutationDesc))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descriptionLine", is(descriptionLine)));
    }

    @Test
    void deleteDescriptionTest() throws Exception {
        mockMvc.perform(delete("/descriptions/" + 102L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("DESCRIPTION item with id = 102 successfully deleted.")));
    }

    @Test
    void notFoundDescriptionModelTest() throws Exception {
        mockMvc.perform(get("/products/" + 1000L)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}