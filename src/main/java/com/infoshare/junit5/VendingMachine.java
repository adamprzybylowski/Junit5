package com.infoshare.junit5;

import com.infoshare.junit5.domain.Coin;
import com.infoshare.junit5.domain.Dispenser;
import com.infoshare.junit5.domain.Product;
import com.infoshare.junit5.exception.OutOfItemException;
import com.infoshare.junit5.exception.VendingMachineException;

/**
 * - gives change, if possible
 * - keeps count of loaded products
 * - keeps count of loaded coins
 * - dispenses product
 * - returns collected change
 */
public interface VendingMachine {
    void insert(Coin coin);
    void select(Product type);

    Product dispense() throws VendingMachineException, OutOfItemException;
    Dispenser<Coin> collectChange();
    Dispenser<Coin> cancel();
}
