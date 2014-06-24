package com.kt.bit.csm.blds.cache;

/**
 * Created with IntelliJ IDEA.
 * User: toto
 * Date: 14. 6. 20
 * Time: 오후 2:51
 * To change this template use File | Settings | File Templates.
 */
public class CacheColumn {
    int type;
    String name;
    Object value;

    public CacheColumn(String name, int type, Object value){
        this.name = name;
        this.type = type;
        this.value = value;
    }

    int getType() {
        return type;
    }

    void setType(int type) {
        this.type = type;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    Object getValue() {
        return value;
    }

    void setValue(Object value) {
        this.value = value;
    }


}
