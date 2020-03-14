package com.evbx.product.test.repository;

import com.evbx.product.constant.Item;
import com.evbx.product.data.DescriptionTestDataStorage;
import com.evbx.product.util.ItemUtil;
import com.evbx.product.util.TestDataUtil;
import com.evbx.product.data.TestDataProjectionStorage;
import com.evbx.product.exception.ItemNotFoundException;
import com.evbx.product.layer.repository.DescriptionRepository;
import com.evbx.product.layer.repository.projection.UniqueProjection;
import com.evbx.product.model.domain.Description;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.evbx.product.support.Step.__GIVEN;
import static com.evbx.product.support.Step.__THEN;
import static com.evbx.product.support.Step.__WHEN;
import static org.assertj.core.api.Assertions.assertThat;

public class DescriptionRepositoryTest extends BaseRepositoryTest {

    private DescriptionRepository descriptionRepository;

    @Autowired
    public DescriptionRepositoryTest(DescriptionRepository descriptionRepository) {
        this.descriptionRepository = descriptionRepository;
    }

    @Test
    void findAllDescriptionsTest() {
        __GIVEN();
        List<Description> expectedDescriptions = DescriptionTestDataStorage.getTestDescriptions();
        __WHEN();
        List<Description> actualDescriptions = descriptionRepository.findAll();
        TestDataUtil.nullifyNonPresentItemsForDescriptions(actualDescriptions);
        __THEN();
        assertThat(actualDescriptions).isEqualTo(expectedDescriptions);
    }

    @Test
    void findDescriptionByIdTest() {
        __GIVEN();
        Description expectedDescription = ItemUtil.getRandomItem(DescriptionTestDataStorage.getTestDescriptions());
        __WHEN();
        Long id = expectedDescription.getId();
        Description actualDescription = descriptionRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException(Item.DESCRIPTION, id));
        TestDataUtil.nullifyNonPresentItemsForDescription(actualDescription);
        __THEN();
        assertThat(actualDescription).isEqualTo(expectedDescription);
    }

    @Test
    void saveDescriptionTest() {
        __GIVEN();
        Description mutationDesc = DescriptionTestDataStorage.getMutationDesc();
        __WHEN();
        Description savedDesc = descriptionRepository.save(mutationDesc);
        Long id = savedDesc.getId();
        Description extractedDesc = descriptionRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException(Item.DESCRIPTION, id));
        TestDataUtil.nullifyNonPresentItemsForDescription(savedDesc);
        TestDataUtil.nullifyNonPresentItemsForDescription(extractedDesc);
        __THEN();
        assertThat(mutationDesc).isEqualTo(savedDesc);
        assertThat(mutationDesc).isEqualTo(extractedDesc);
    }

    @Test
    void updateDescriptionTest() {
        __GIVEN();
        Description mutationDescription = DescriptionTestDataStorage.getMutationDesc();
        Description expectedDescription = ItemUtil.getRandomItem(DescriptionTestDataStorage.getTestDescriptions());
        expectedDescription.setDescriptionLine(mutationDescription.getDescriptionLine());
        int itemsSizeBefore = descriptionRepository.findAll().size();
        __WHEN();
        Description savedDescription = descriptionRepository.save(expectedDescription);
        Long id = savedDescription.getId();
        int itemsSizeAfter = descriptionRepository.findAll().size();
        Description extractedDescription = descriptionRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException(Item.DESCRIPTION, id));
        TestDataUtil.nullifyNonPresentItemsForDescription(extractedDescription);
        __THEN();
        assertThat(expectedDescription).isEqualTo(extractedDescription);
        assertThat(itemsSizeAfter).isEqualTo(itemsSizeBefore);
    }

    @Test
    void getUniqueColumnNamesTest() {
        __GIVEN();
        List<UniqueProjection> expectedDescProjections = TestDataProjectionStorage.getDescriptionProjections();
        __WHEN();
        List<UniqueProjection> actualDescNameProjections = descriptionRepository.getUniqueColumnNames();
        __THEN();
        projectionVerificationService.verifyNameProjections(actualDescNameProjections, expectedDescProjections);
    }

    @Test
    void deleteDescriptionByIdTest() {
        __GIVEN();
        List<Description> descriptionsBeforeDelete = descriptionRepository.findAll();
        __WHEN();
        Description descriptionToDelete = ItemUtil.getRandomItem(descriptionsBeforeDelete);
        descriptionRepository.deleteById(descriptionToDelete.getId());
        __THEN();
        List<Description> descriptionsAfterDelete = descriptionRepository.findAll();
        assertThat(descriptionsAfterDelete).doesNotContain(descriptionToDelete);
    }

    @Test
    void existsDescriptionById() {
        __GIVEN();
        Long id = ItemUtil.getRandomItem(DescriptionTestDataStorage.getTestDescriptions()).getId();
        __WHEN();
        boolean isExists = descriptionRepository.existsById(id);
        __THEN();
        assertThat(isExists).isTrue();
    }
}
