package com.infoshare.junit5.exception;

public class OutOfItemException extends Exception {
    public OutOfItemException(String itemName) {
        super(itemName + " not available");
    }
}
