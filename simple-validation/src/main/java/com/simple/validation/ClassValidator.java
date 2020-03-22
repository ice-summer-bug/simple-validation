package com.simple.validation;

import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * the container of field validator
 *
 * the {@link ClassValidator} must support singleton
 *
 * @author liam
 */
public abstract class ClassValidator {

    ClassValidator() {
    }

    /**
     * get the field validator which defined in {@link ClassValidator}
     *
     * @return
     */
    public List<FieldValidator> getFieldValidators() {
        List<FieldValidator> fieldValidators = new ArrayList<>();
        ClassValidator classValidator = this;
        ReflectionUtils.doWithFields(getClass(), field -> {
            field.setAccessible(true);
            fieldValidators.add((FieldValidator) field.get(classValidator));
        }, field -> field.getType().isAssignableFrom(FieldValidator.class));
        return fieldValidators;
    }

    /**
     * register the field validator to context
     */
    protected void register() {
        SimpleValidator.newInstance().register(this);
    }

    /**
     * get the target class for {@link ClassValidator}
     *
     * @return
     */
    protected abstract Class<?> getTargetClass();

    /**
     * Init the class validator
     *
     * such as invoke {@link ClassValidator#register()}
     */
    protected abstract void init();

}
