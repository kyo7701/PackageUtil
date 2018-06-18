package com.cris.constant;

/**
 * Author:Mr.Cris
 * Date:2017-09-03 23:44
 */
public enum PackageType {

    DAO(0,"dao"),SERVICE(1,"service"),CONTROLLER(2,"controller"),ENTITY(3,"entity");

    private Integer code;
    private String value;

    PackageType() {

    }

    PackageType(Integer code,String value){
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
