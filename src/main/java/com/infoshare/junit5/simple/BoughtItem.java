package com.infoshare.junit5.simple;

import com.infoshare.junit5.domain.Coin;
import com.infoshare.junit5.domain.Product;

import java.util.List;

public class BoughtItem {
    Product type;
    List<Coin> change;

    public BoughtItem(Product type, List<Coin> change) {
        this.type = type;
        this.change = change;
    }
}
