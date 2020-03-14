package com.evbx.product.test.service;

import com.evbx.product.data.DescriptionTestDataStorage;
import com.evbx.product.util.ItemUtil;
import com.evbx.product.util.TestDataUtil;
import com.evbx.product.exception.ItemNotFoundException;
import com.evbx.product.layer.service.DescriptionService;
import com.evbx.product.model.UpdatableEntity;
import com.evbx.product.model.domain.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.evbx.product.support.Step.__GIVEN;
import static com.evbx.product.support.Step.__THEN;
import static com.evbx.product.support.Step.__WHEN;
import static org.assertj.core.api.Assertions.assertThat;

public class DescriptionServiceTest extends BaseServiceTest {

    private DescriptionService descriptionService;

    @Autowired
    public DescriptionServiceTest(DescriptionService descriptionService) {
        this.descriptionService = descriptionService;
    }

    @Test
    void findAllDescriptionsTest() {
        __GIVEN();
        List<Description> expectedDescriptions = DescriptionTestDataStorage.getTestDescriptions();
        __WHEN();
        List<Description> actualDescriptions = descriptionService.getAllDescriptions().getItems();
        TestDataUtil.nullifyNonPresentItemsForDescriptions(actualDescriptions);
        __THEN();
        assertThat(actualDescriptions).isEqualTo(expectedDescriptions);
    }

    @Test
    void findDescriptionByIdTest() {
        __GIVEN();
        Description expectedDescription = ItemUtil.getRandomItem(
                DescriptionTestDataStorage.getTestDescriptions());
        __WHEN();
        Long id = expectedDescription.getId();
        Description actualDescription = descriptionService.getDescription(id);
        TestDataUtil.nullifyNonPresentItemsForDescription(actualDescription);
        __THEN();
        assertThat(actualDescription).isEqualTo(expectedDescription);
    }

    @Test
    void saveDescriptionTest() {
        __GIVEN();
        mockSecurityContext();
        Description mutationDesc = DescriptionTestDataStorage.getMutationDesc();
        __WHEN();
        Description savedDesc = descriptionService.save(mutationDesc);
        Long id = savedDesc.getId();
        Description extractedDesc = descriptionService.getDescription(id);
        TestDataUtil.nullifyNonPresentItemsForDescription(savedDesc);
        TestDataUtil.nullifyNonPresentItemsForDescription(extractedDesc);
        __THEN();
        assertThat(mutationDesc).isEqualTo(savedDesc);
        assertThat(mutationDesc).isEqualTo(extractedDesc);
    }

    @Test
    void updateDescriptionTest() {
        __GIVEN();
        mockSecurityContext();
        Description mutationDescription = DescriptionTestDataStorage.getMutationDesc();
        Description expectedDescription = ItemUtil.getRandomItem(
                DescriptionTestDataStorage.getTestDescriptions());
        expectedDescription.setDescriptionLine(mutationDescription.getDescriptionLine());
        int itemsSizeBefore = descriptionService.getAllDescriptions().getItems().size();
        __WHEN();
        Description savedDescription = descriptionService.save(expectedDescription);
        Long id = savedDescription.getId();
        int itemsSizeAfter = descriptionService.getAllDescriptions().getItems().size();
        Description extractedDescription = descriptionService.getDescription(id);
        TestDataUtil.nullifyNonPresentItemsForDescription(extractedDescription);
        __THEN();
        assertThat(expectedDescription).isEqualTo(extractedDescription);
        assertThat(itemsSizeAfter).isEqualTo(itemsSizeBefore);
    }

    @Test
    void deleteDescriptionByIdTest() {
        __GIVEN();
        List<Description> descriptionsBeforeDelete = descriptionService.getAllDescriptions().getItems();
        __WHEN();
        Description descriptionToDelete = ItemUtil.getRandomItem(descriptionsBeforeDelete);
        descriptionService.deleteById(descriptionToDelete.getId());
        __THEN();
        List<Description> descriptionsAfterDelete = descriptionService.getAllDescriptions().getItems();
        assertThat(descriptionsAfterDelete).doesNotContain(descriptionToDelete);
    }

    @Test
    void itemNotFoundExceptionTest() {
        __GIVEN();
        Long maxId = descriptionService.getAllDescriptions().getItems().stream().mapToLong(UpdatableEntity::getId).max()
                .orElse(999L);
        __WHEN();
        long nonPresentId = maxId + 100L;
        __THEN();
        Assertions.assertThrows(ItemNotFoundException.class, () -> descriptionService.getDescription(nonPresentId));
    }
}
