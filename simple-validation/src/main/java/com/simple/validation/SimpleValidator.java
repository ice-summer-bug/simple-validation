package com.simple.validation;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Simple validator, base on spring's ReflectionUtils
 * and commons-beanutils's PropertyUtils
 *
 * @author liam
 * @see org.springframework.util.ReflectionUtils
 * @see PropertyUtils
 */
public class SimpleValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleValidator.class);

    private FieldValidatorRegister validatorRegister = new DefaultFieldValidatorRegister();

    private SimpleValidator() {
    }

    public FieldValidatorRegister getValidatorRegister() {
        return validatorRegister;
    }

    public void setValidatorRegister(FieldValidatorRegister validatorRegister) {
        this.validatorRegister = validatorRegister;
    }

    public void register(ClassValidator classValidator) {
        if (classValidator == null) {
            throw new IllegalArgumentException("Class validator can not be null");
        }
        classValidator.getFieldValidators().forEach(this::register);
    }

    public void register(FieldValidator fieldValidator) {
        if (fieldValidator == null) {
            throw new IllegalArgumentException("Field validator can not be null");
        }
        validatorRegister.register(fieldValidator);
    }

    public <T extends Serializable> Map<String, String> validate(T t) {
        if (t == null) {
            throw new IllegalArgumentException("Can not validate object which is null");
        }
        List<String> validateFields = findValidateFields(t);
        System.out.println(validateFields);
        if (StringUtils.isEmpty(validateFields)) {
            LOGGER.debug("There is no field mark with @Validate in {}", t.getClass());
            return Collections.emptyMap();
        }
        Map<String, FieldValidator> fieldValidators = validatorRegister.getFieldValidators(t.getClass());
        if (MapUtils.isEmpty(fieldValidators)) {
            LOGGER.debug("There is no field validator defined for bean: {}", t.getClass());
            return Collections.emptyMap();
        }

        ValidationContext validationContext = new ValidationContext();
        validateFields.forEach(fieldName -> {
            System.out.println("do validate for filed: " + fieldName);
            FieldValidator fieldValidator = fieldValidators.get(fieldName);
            if (fieldValidator == null) {
                LOGGER.debug("Can not find validator for field: {}", fieldName);
                return;
            }
            List<String> argumentNameList = fieldValidator.getArgumentNameList();
            Object[] args = argumentNameList.stream().map(argumentName -> {
                try {
                    return PropertyUtils.getProperty(t, fieldName);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Can not get property " + fieldName + " from " + t, e);
                }
            }).toArray();
            ValidatorInvoker.invoke(fieldValidator, validationContext, args);
        });

        return validationContext.getErrorMsgMap();
    }

    private <T extends Serializable> List<String> findValidateFields(T t) {
        Map<Field, List<String>> mapFieldMap = new HashMap<>(20);
        ReflectionUtils.doWithFields(t.getClass(), field -> {
            field.setAccessible(true);
            if (field.getType().isAssignableFrom(Map.class)) {
                Map tmpMap = (Map) field.get(t);
                if (tmpMap == null) {
                    return;
                }

                for (Object mapField : tmpMap.keySet()) {
                    if (mapField != null) {
                        List<String> tmpMapFields = mapFieldMap.get(field);
                        if (tmpMapFields == null) {
                            tmpMapFields = new ArrayList<>();
                            tmpMapFields.add((String) mapField);
                        }
                        mapFieldMap.put(field, tmpMapFields);
                    }
                }
            } else {
                List<String> fieldNameList = new ArrayList<>(1);
                fieldNameList.add(field.getName());
                mapFieldMap.put(field, fieldNameList);
            }
        }, field -> field.getAnnotation(Validate.class) != null);
        return mapFieldMap.keySet().stream().sorted(Comparator.comparingInt(o -> o.getAnnotation(Validate.class).order()))
                .flatMap(field -> mapFieldMap.get(field).stream()).collect(Collectors.toList());
    }

    public static SimpleValidator newInstance() {
        return SingletonHolder.SINGLETON;
    }

    private static class SingletonHolder {
        private static final SimpleValidator SINGLETON = new SimpleValidator();
    }
}
