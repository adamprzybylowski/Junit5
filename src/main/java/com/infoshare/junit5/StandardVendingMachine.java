package com.infoshare.junit5;

import com.infoshare.junit5.domain.Coin;
import com.infoshare.junit5.domain.Dispenser;
import com.infoshare.junit5.domain.Product;
import com.infoshare.junit5.exception.NotEnoughCoinsException;
import com.infoshare.junit5.exception.OutOfItemException;
import com.infoshare.junit5.exception.ProductNotSelectedException;
import com.infoshare.junit5.exception.VendingMachineException;

/**
 * Contains only loaded products and coins.
 * Gives correct change.
 */
public class StandardVendingMachine implements VendingMachine {

    private final ProductBox productBox;
    private final CoinBox coinBox;
    private Product selectedProduct;

    public StandardVendingMachine(ProductBox productBox, CoinBox coinBox) {
        this.productBox = productBox;
        this.coinBox = coinBox;
    }

    public void insert(Coin coin) {
        coinBox.insert(coin);
    }

    public void select(Product type) {
        this.selectedProduct = type;
    }

    @Override
    public Product dispense() throws OutOfItemException, VendingMachineException {
        if (this.selectedProduct==null) {
            throw new ProductNotSelectedException();
        }
        Integer price = selectedProduct.getValue();
        Integer payment = coinBox.currentPayment();
        if (price > payment) {
            throw new NotEnoughCoinsException(selectedProduct, payment);
        }
        coinBox.accept(price);
        Product item = productBox.dispense(this.selectedProduct);
        this.selectedProduct = null;
        return item;
    }

    @Override
    public Dispenser<Coin> collectChange() {
        return coinBox.collectChange();
    }

    @Override
    public Dispenser<Coin> cancel() {
        this.selectedProduct = null;
        return coinBox.cancel();
    }
}
