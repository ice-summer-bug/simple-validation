package com.simple.validation;

import java.util.HashMap;
import java.util.Map;

public class SimpleClassWithMap {

    private Map<String, Object> extendInfo = new HashMap<>();

    public Map<String, Object> getExtendInfo() {
        return extendInfo;
    }

    public void setExtendInfo(Map<String, Object> extendInfo) {
        this.extendInfo = extendInfo;
    }
}
