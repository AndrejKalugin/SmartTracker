package com.tracker.smarttracker.util;

import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class BeanCopyUtils {

    public static void copyNonNullProperties(Object source, Object destination, String... ignoreProperties){
        Set<String> ignorePropertiesSet = getNullPropertyNames(source);
        ignorePropertiesSet.addAll(List.of(ignoreProperties));
        BeanUtils.copyProperties(source, destination, ignorePropertiesSet.toArray(new String[1]));
    }

    public static Set<String> getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        var emptyNames = new HashSet<String>();
        for(var propertyDescriptor : src.getPropertyDescriptors()) {
            var srcValue = src.getPropertyValue(propertyDescriptor.getName());
            if (Objects.isNull(srcValue)) emptyNames.add(propertyDescriptor.getName());
        }
        return emptyNames;
    }
}