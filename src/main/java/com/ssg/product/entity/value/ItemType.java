package com.ssg.product.entity.value;

public enum ItemType {
    NORMAL("일반"),
    CORPORATE("기업회원상품");

    final private String krName;

    ItemType(String krName){
        this.krName = krName;
    }

    public String getKrName() {
        return this.krName;
    }
}
