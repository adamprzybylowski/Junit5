package com.infoshare.junit5;

import com.infoshare.junit5.domain.Dispenser;
import com.infoshare.junit5.domain.Product;
import com.infoshare.junit5.exception.OutOfItemException;

public class ProductBox {

    private Dispenser dispenser = new Dispenser(Product.class);

    public ProductBox() {
        unload();
    }

    private void unload() {
        dispenser.unload();
    }

    void load(Product product, Integer amount){
        dispenser.add(product, amount);
    }

    Product dispense(Product type) throws OutOfItemException {
        return (Product) dispenser.dispense(type);
    }
}
