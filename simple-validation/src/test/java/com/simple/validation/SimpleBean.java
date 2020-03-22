package com.simple.validation;

import java.io.Serializable;

public class SimpleBean implements Serializable {

    private static final long serialVersionUID = 2673835805978557694L;

    @Validate(order = 2)
    private long id;

    @Validate(order = 1)
    private String name;

    @Validate(order = 3)
    private String description;

    @Validate(order = 4)
    private Integer relatedId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }
}
