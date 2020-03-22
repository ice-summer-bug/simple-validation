package com.simple.validation;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ValidatorInvoker {

    private static final String VALIDATE_METHOD_NAME = "validate";

    public static ValidationContext invoke(FieldValidator fieldValidator, ValidationContext context, Object[] args) {
        Object[] nArgs = new Object[args.length + 1];
        nArgs[0] = context;
        System.arraycopy(args, 0, nArgs, 1, nArgs.length - 1);
        invokeValidateMethod(fieldValidator, nArgs);
        return context;
    }

    private static void invokeValidateMethod(FieldValidator fieldValidator, Object[] args) {
        Method uniqueValidateMethod = findUniqueValidateMethod(fieldValidator.getClass());
        ReflectionUtils.invokeMethod(uniqueValidateMethod, fieldValidator, args);
    }

    private static Method findUniqueValidateMethod(Class<? extends FieldValidator> clazz) {
        List<Method> methods = new ArrayList<>();
        ReflectionUtils.doWithMethods(clazz, method -> {
            method.setAccessible(true);
            methods.add(method);
        }, method -> method != null && method.getName().equals(VALIDATE_METHOD_NAME));
        return methods.stream().findFirst().orElseThrow(() ->
                new IllegalArgumentException("The field validator can only have a method named \"validate\": " + clazz.getName()));
    }
}
