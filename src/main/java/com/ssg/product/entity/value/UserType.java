package com.ssg.product.entity.value;

public enum UserType {
    NORMAL("일반"),
    CORPORATE("기업회원");

    final private String krName;

    UserType(String krName){
        this.krName = krName;
    }

    public String getKrName() {
        return this.krName;
    }
}
