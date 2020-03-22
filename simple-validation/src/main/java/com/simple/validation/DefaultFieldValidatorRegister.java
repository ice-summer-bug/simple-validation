package com.simple.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class DefaultFieldValidatorRegister implements FieldValidatorRegister {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultFieldValidatorRegister.class);

    private Map<Class<?>, Map<String, FieldValidator>> fieldValidatorMap = new HashMap<>();

    @Override
    public FieldValidator getFieldValidator(String name) {
        FieldValidator fieldValidator = fieldValidatorMap.get(null).get(name);
        if (fieldValidator != null) {
            return fieldValidator;
        }
        for (Class<?> clazz : fieldValidatorMap.keySet()) {
            if (clazz == null) {
                continue;
            }
            Map<String, FieldValidator> classFieldValidators = fieldValidatorMap.get(clazz);
            if (classFieldValidators == null || classFieldValidators.isEmpty()) {
                continue;
            }
            FieldValidator tmpValidator = classFieldValidators.get(name);
            if (tmpValidator != null) {
                return tmpValidator;
            }
        }
        return null;
    }

    @Override
    public FieldValidator getFieldValidator(Class<?> clazz, String name) {
        Map<String, FieldValidator> clazzFieldValidator = fieldValidatorMap.get(clazz);
        if (clazzFieldValidator == null) {
            return null;
        }
        return clazzFieldValidator.get(name);
    }

    @Override
    public Map<String, FieldValidator> getFieldValidators(Class<?> clazz) {
        return fieldValidatorMap.computeIfAbsent(clazz, k -> new HashMap<>());
    }

    @Override
    public void register(FieldValidator fieldValidator) {
        LOGGER.debug("register FieldValidator, name: {}, target class: {}", fieldValidator.getName(), fieldValidator.getTargetClass());
        getFieldValidators(fieldValidator.getTargetClass()).put(fieldValidator.getName(), fieldValidator);
    }
}
