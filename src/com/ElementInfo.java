package com;

/**
 * Created by dai on 16-9-11.
 */
public class ElementInfo {

    /**
     * 元素类型
     */
    private String type;

    /**
     * 元素名称
     */
    private String name;

    public String getType() {
        return type;
    }

    public ElementInfo setType(String type) {
        this.type = type;
        return this;
    }

    public String getName() {
        return name;
    }

    public ElementInfo setName(String name) {
        this.name = name;
        return this;
    }
}
