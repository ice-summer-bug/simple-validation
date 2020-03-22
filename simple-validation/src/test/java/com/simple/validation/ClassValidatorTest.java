package com.simple.validation;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClassValidatorTest {

    private ClassValidator classValidator;

    @Before
    public void init() {
        classValidator = new ClassValidator() {



            public FieldValidator nameValidator = new AbstractFieldValidator(getTargetClass(), "name",
                    Stream.of("name").collect(Collectors.toList())) {

                public void validate(ValidationContext validationContext, String name) {
                    validationContext.addAttribute("name", name);
                    if (StringUtils.isEmpty(name)) {
                        validationContext.addErrorMessage(getName(), "name can not be null");
                    }
                }
            };

            @Override
            protected Class<?> getTargetClass() {
                return SimpleBean.class;
            }

            @Override
            protected void init() {
                register();
            }
        };
    }

    @Test
    public void testGetFieldValidators() {

        List<FieldValidator> fieldValidators = classValidator.getFieldValidators();
        System.out.println(fieldValidators.size());

    }

    @Test
    public void testRegisterFieldValidators() {
        classValidator.init();
        Map<String, FieldValidator> fieldValidators = SimpleValidator.newInstance().getValidatorRegister().getFieldValidators(SimpleBean.class);
        System.out.println(fieldValidators.size());

    }
}