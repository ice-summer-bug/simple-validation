package com.simple.validation;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Before;
import org.junit.Test;
import sun.jvm.hotspot.utilities.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class SimpleValidatorTest {

    private SimpleValidator simpleValidator;

    @Before
    public void init() {
        simpleValidator = SimpleValidator.newInstance();
        SimpleBeanValidator.newInstance().init();
    }

    @Test
    public void testValidate() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setId(1);
        simpleBean.setName("simple test bean");
        simpleBean.setDescription("a simple bean object to be test");
        simpleBean.setRelatedId(1);

        Map<String, String> validateResult = simpleValidator.validate(simpleBean);
        Assert.that(validateResult.isEmpty(), "there is no validate result");
    }

    @Test
    public void testGetFields() {
        Field[] declaredFields = SimpleBeanValidator.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField.getName());
        }
    }

    @Test
    public void testValidateBlankName() {
        SimpleBean simpleBean = new SimpleBean();
        simpleBean.setId(1);
        simpleBean.setName("dddddd");
        simpleBean.setDescription("a simple bean object to be test");
        simpleBean.setRelatedId(1);

        Map<String, String> validateResult = simpleValidator.validate(simpleBean);
        System.out.println(validateResult);
        Assert.that(validateResult.size() == 1, "there is one validate result");
    }

    @Test
    public void testPropertyUtilsGetProperties() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String, Object> map = new HashMap<>();
        map.put("test", "test value");
        SimpleClassWithMap simpleClassWithMap = new SimpleClassWithMap();
        simpleClassWithMap.setExtendInfo(map);

        Object value = PropertyUtils.getProperty(simpleClassWithMap, "extendInfo(test)");
        System.out.println(value);

    }

}
