package com.infoshare.junit5.exception;

import com.infoshare.junit5.domain.Product;

public class NotEnoughCoinsException extends VendingMachineException {
    public NotEnoughCoinsException(Product type, Integer payment) {
        super(type.name() + " costs " + type.getValue() + ", given payment " + payment);
    }
}
