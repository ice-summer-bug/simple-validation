package com.simple.validation;

import java.util.Map;

/**
 * The register  field validator
 *
 * @author liam
 */
public interface FieldValidatorRegister {

    /**
     * Get field validator with field name
     *
     * @param fieldName
     * @return
     */
    FieldValidator getFieldValidator(String fieldName);

    /**
     * Get field validator with field name and class of target field
     *
     * @param clazz
     * @param fieldName
     * @return
     */
    FieldValidator getFieldValidator(Class<?> clazz, String fieldName);

    /**
     * Get all field validator of target class
     *
     * @param clazz
     * @return
     */
    Map<String, FieldValidator> getFieldValidators(Class<?> clazz);

    /**
     * Register field validator to container
     */
    void register(FieldValidator fieldValidator);


}
