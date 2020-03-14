package com.evbx.product.util;

import com.evbx.product.model.UpdatableEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.Calendar;
import java.util.Objects;
import java.util.stream.Stream;

public final class UpdateUtil {

    private UpdateUtil() {
    }

    public static <T extends UpdatableEntity> void updateItem(T source, T target) {
        String[] ignoredProperties = getNullPropertyNames(source);
        BeanUtils.copyProperties(source, target, ignoredProperties);
        target.setUpdatedBy(AuthUtil.getUserName());
        target.setUpdatedAt(Calendar.getInstance().getTime());
    }

    private static <T> String[] getNullPropertyNames(T item) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(item);
        return Stream.of(wrappedSource.getPropertyDescriptors()).map(FeatureDescriptor::getName).filter(
                propertyName -> Objects.isNull(wrappedSource.getPropertyValue(propertyName))).toArray(String[]::new);
    }
}
