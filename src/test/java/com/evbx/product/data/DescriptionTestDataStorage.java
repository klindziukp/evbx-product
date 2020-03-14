package com.evbx.product.data;

import com.evbx.product.model.domain.Description;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public final class DescriptionTestDataStorage {

    private DescriptionTestDataStorage() {
    }

    public static List<Description> getTestDescriptions() {
        List<Description> descriptions = new ArrayList<>();

        Description firstDescription = new Description();
        firstDescription.setId(100L);
        firstDescription.setModelId(100L).setDescriptionLine("Description-line-0").setCreatedBy("script-0");
        descriptions.add(firstDescription);

        Description secondDescription = new Description();
        secondDescription.setId(101L);
        secondDescription.setModelId(101L).setDescriptionLine("Description-line-1").setCreatedBy("script-1");
        descriptions.add(secondDescription);

        Description thirdDescription = new Description();
        thirdDescription.setId(102L);
        thirdDescription.setModelId(102L).setDescriptionLine("Description-line-2").setCreatedBy("script-2");
        descriptions.add(thirdDescription);

        return descriptions;
    }

    public static Description getMutationDesc() {
        Description description = new Description();
        description.setModelId(100L).setDescriptionLine("Description-line-777").setCreatedBy("script-777");
        description.setCreatedAt(Calendar.getInstance().getTime());
        return description;
    }
}
