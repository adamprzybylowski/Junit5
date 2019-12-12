package com.infoshare.junit5.domain;

public enum Product implements Item {

    PEPSI(3), CHOCOLATE_BAR(4), COOKIES(7);

    private Integer value;

    Product(Integer value) {
        this.value = value;
    };

    public Integer getValue() {
        return value;
    }
}
