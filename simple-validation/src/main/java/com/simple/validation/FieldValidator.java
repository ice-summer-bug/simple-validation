package com.simple.validation;

import java.util.List;

/**
 * Validator of filed in target class
 * @author liam
 */
public interface FieldValidator {

    /**
     * Get target class of field validator
     *
     * @return
     */
    Class<?> getTargetClass();

    /**
     * Get name of field validator
     *
     * @return
     */
    String getName();

    /**
     * Get argument name list of field validator
     *
     * @return
     */
    List<String> getArgumentNameList();

}
