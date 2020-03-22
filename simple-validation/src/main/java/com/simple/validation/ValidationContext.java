package com.simple.validation;

import java.util.HashMap;
import java.util.Map;

public class ValidationContext {

    private Map<String, Object> contextMap = new HashMap<>();

    private Map<String, String> errorMsgMap = new HashMap<>();

    public Map<String, Object> getContextMap() {
        return contextMap;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public ValidationContext addErrorMessage(String name, String errorMsg) {
        errorMsgMap.put(name, errorMsg);
        return this;
    }

    public ValidationContext addErrorMessage(String name, String errorMsg, Object... args) {
        errorMsg = String.format(errorMsg, args);
        errorMsgMap.put(name, errorMsg);
        return this;
    }

    public void setContextMap(Map<String, Object> contextMap) {
        this.contextMap = contextMap;
    }

    public ValidationContext withContextMap(Map<String, Object> contextMap) {
        this.contextMap = contextMap;
        return this;
    }

    public ValidationContext addAttribute(String key, Object value) {
        contextMap.put(key, value);
        return this;
    }

    public Object getAttribute(String key) {
        return contextMap.get(key);
    }
}
