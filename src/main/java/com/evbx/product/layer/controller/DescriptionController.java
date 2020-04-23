package com.evbx.product.layer.controller;

import com.evbx.product.constant.Item;
import com.evbx.product.layer.service.DescriptionService;
import com.evbx.product.model.domain.Description;
import com.evbx.product.model.data.ItemData;
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
public class DescriptionController {

    private DescriptionService descriptionService;

    @Autowired
    public DescriptionController(DescriptionService descriptionService) {
        this.descriptionService = descriptionService;
    }

    @GetMapping(value = "/descriptions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemData<Description>> getAllDescriptions() {
        return ResponseEntity.ok(descriptionService.getAllDescriptions());
    }

    @GetMapping(value = "/descriptions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Description> getDescription(@PathVariable long id) {
        return ResponseEntity.ok(descriptionService.getDescription(id));
    }

    @PostMapping(value = "/descriptions", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Description> addDescription(@Valid @RequestBody Description description) {
        return ResponseEntity.ok(descriptionService.save(description));
    }

    @PatchMapping(value = "/descriptions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Description> updateDescription(@PathVariable long id, @RequestBody Description description) {
        return ResponseEntity.ok(descriptionService.update(id, description));
    }

    @DeleteMapping(value = "/descriptions/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteReport(@PathVariable long id) {
        descriptionService.deleteById(id);
        return ResponseEntity.ok(
                String.format("%s item with id = %d successfully deleted. ", Item.DESCRIPTION, id));
    }
}
