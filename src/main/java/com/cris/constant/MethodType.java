package com.cris.constant;

/**
 * Author:Mr.Cris
 * Date:2018-06-18 20:33
 */
public enum MethodType {

    METHOD_TYPE_QUERYLIST(0,"queryList"),
    METHOD_TYPE_QUERYONE(1,"queryOne"),
    METHOD_TYPE_INSERT(2,"insert"),
    METHOD_TYPE_UPDATE(3,"update"),
    METHOD_TYPE_DELETE(4,"delete"),
    ;

    private Integer key;

    private String value;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    MethodType(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
