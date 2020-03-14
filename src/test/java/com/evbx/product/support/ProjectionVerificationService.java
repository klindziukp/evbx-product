package com.evbx.product.support;

import com.evbx.product.layer.repository.projection.UniqueProjection;
import org.assertj.core.api.SoftAssertions;

import java.util.List;
import java.util.Optional;

public final class ProjectionVerificationService {

    public void verifyNameProjections(List<UniqueProjection> actual, List<UniqueProjection> expected) {
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(actual.size()).as("Sizes are not equal").isEqualTo(expected.size());
        for (int i = 0; i < expected.size(); i++) {
            String actualName = Optional.ofNullable(actual.get(i).getUniqueColumn()).orElse("Unable to get actual name.");
            String expectedName = Optional.ofNullable(expected.get(i).getUniqueColumn()).orElse("Unable to get expected name.");
            softAssertions.assertThat(actualName).as("Unique columns are not equal.").isEqualTo(expectedName);
        }
        softAssertions.assertAll();
    }
}
