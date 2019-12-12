package com.infoshare.junit5.simple;

import com.infoshare.junit5.domain.Coin;
import com.infoshare.junit5.domain.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Basic implementation. Contains unlimited products. Can't give change
 */
public class SimpleVendingMachine implements VendingMachine {

    Product selectedProduct;
    List<Coin> payment = new ArrayList<>();

    @Override
    public BoughtItem dispense() {

        if (selectedProduct==null) {
            throw new ProductNotSelectedException("Product not selected");
        }

        Integer sum = payment.stream().collect(Collectors.summingInt(Coin::getValue));
        if (sum<selectedProduct.getValue()) {
            throw new InsufficientPaymentException("Not enough coins");
        }

        List<Coin> emptyChange = Collections.emptyList();
        BoughtItem boughtItem = new BoughtItem(selectedProduct, emptyChange);
        selectedProduct = null;
        return boughtItem;
    }

    @Override
    public void insert(Coin coin) {
        payment.add(coin);
    }

    @Override
    public void select(Product product) {
        this.selectedProduct = product;
    }
}
