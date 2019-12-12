package com.infoshare.junit5.simple;

import com.infoshare.junit5.domain.Coin;
import com.infoshare.junit5.domain.Product;

public interface VendingMachine {
    BoughtItem dispense() throws ProductNotSelectedException, InsufficientPaymentException;

    void insert(Coin one);

    void select(Product product);
}
