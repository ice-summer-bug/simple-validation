package com.simple.validation;

import java.util.List;

/**
 * Abstract field validator
 *
 * @author liam
 */
public class AbstractFieldValidator implements FieldValidator {

    private Class<?> targetClass;

    private String name;

    private List argumentNameList;

    public AbstractFieldValidator(Class<?> targetClass, String name, List argumentNameList) {
        this.targetClass = targetClass;
        this.name = name;
        this.argumentNameList = argumentNameList;
    }

    @Override
    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<String> getArgumentNameList() {
        return argumentNameList;
    }

    public void setArgumentNameList(List argumentNameList) {
        this.argumentNameList = argumentNameList;
    }
}
