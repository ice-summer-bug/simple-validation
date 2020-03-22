package com.simple.validation;

import org.springframework.util.StringUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleBeanValidator extends ClassValidator {

    private SimpleBeanValidator() {
        super();
    }

    @Override
    protected Class<?> getTargetClass() {
        return SimpleBean.class;
    }

    @Override
    protected void init() {
        register();
    }


    public FieldValidator aidValidator = new AbstractFieldValidator(getTargetClass(), "id",
            Stream.of("id").collect(Collectors.toList())) {

        public void validate(ValidationContext validationContext, long id) {
            validationContext.addAttribute("id", id);
            String name = (String) validationContext.getAttribute("name");
            System.out.println("id: " + id + " get name: " + name + " from validate context");
            if (id > 0 && !StringUtils.isEmpty(name)) {
                validationContext.addErrorMessage(getName(),
                        "name must be empty with id bigger than 0, id: %s, name: %s", id, name);
            }
        }

    };

    public FieldValidator nameValidator = new AbstractFieldValidator(getTargetClass(), "name",
            Stream.of("name").collect(Collectors.toList())) {

        public void validate(ValidationContext validationContext, String name) {
            validationContext.addAttribute("name", name);
            if (StringUtils.isEmpty(name)) {
                validationContext.addErrorMessage(getName(), "name can not be null");
            }
        }
    };



    public static SimpleBeanValidator newInstance() {
        return SingletonHolder.SINGLETON;
    }

    private static class SingletonHolder {
        private static final SimpleBeanValidator SINGLETON = new SimpleBeanValidator();
    }

}
