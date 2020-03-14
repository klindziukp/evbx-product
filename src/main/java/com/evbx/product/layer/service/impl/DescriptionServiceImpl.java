package com.evbx.product.layer.service.impl;

import com.evbx.product.constant.Item;
import com.evbx.product.exception.ItemNotFoundException;
import com.evbx.product.layer.repository.DescriptionRepository;
import com.evbx.product.layer.service.DescriptionService;
import com.evbx.product.model.domain.Description;
import com.evbx.product.model.data.ItemData;
import com.evbx.product.util.AuthUtil;
import com.evbx.product.util.UpdateUtil;
import com.evbx.product.util.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DescriptionServiceImpl implements DescriptionService {

    private static final Logger LOGGER = LogManager.getLogger(DescriptionServiceImpl.class);
    private DescriptionRepository descriptionRepository;

    @Autowired
    public DescriptionServiceImpl(DescriptionRepository descriptionRepository) {
        this.descriptionRepository = descriptionRepository;
    }

    @Override
    public ItemData<Description> getAllDescriptions() {
        return new ItemData<>(descriptionRepository.findAll());
    }

    @Override
    public Description getDescription(long id) {
        return descriptionRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(Item.DESCRIPTION, id));
    }

    @Override
    public Description save(Description description) {
        verifyDescriptionItem(description);
        description.setCreatedBy(AuthUtil.getUserName());
        Description saveDescription = descriptionRepository.save(description);
        LOGGER.info("Product with id = {} saved successfully", saveDescription.getId());
        return saveDescription;
    }

    @Override
    public Description update(long id, Description description) {
        Description persistedDesc = descriptionRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException(Item.DESCRIPTION, id));
        verifyDescriptionItem(description);
        UpdateUtil.updateItem(description, persistedDesc);
        Description savedDescription = descriptionRepository.save(persistedDesc);
        LOGGER.info("Product with id = {} updated successfully", savedDescription.getId());
        return savedDescription;
    }

    @Override
    public void deleteById(long id) {
        if (!descriptionRepository.existsById(id)) {
            throw new ItemNotFoundException(Item.DESCRIPTION, id);
        }
        descriptionRepository.deleteById(id);
        LOGGER.info("Product with id = {} deleted successfully", id);
    }

    private void verifyDescriptionItem(Description description) {
        verifyProductModel(description);
        verifyDescriptionPresence(description);
    }

    private void verifyProductModel(Description description) {
        if (Objects.nonNull(description.getModelId())) {
            ValidationUtil.validateItemId(Item.PRODUCT_MODEL, descriptionRepository.getProductModelIds(),
                    description.getModelId());
        }
    }

    private void verifyDescriptionPresence(Description description) {
        String descLine = description.getDescriptionLine();
        if (Objects.nonNull(descLine)) {
            ValidationUtil.validateResourceName(descLine, descriptionRepository.getUniqueColumnNames());
        }
    }
}
