package com.infoshare.junit5.domain;

public enum Coin implements Item {

    TEN(10), FIVE(5), TWO(2), ONE(1);

    private Integer value;

    Coin(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
