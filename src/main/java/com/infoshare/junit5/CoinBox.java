package com.infoshare.junit5;

import com.infoshare.junit5.domain.Coin;
import com.infoshare.junit5.domain.Dispenser;
import com.infoshare.junit5.exception.VendingMachineException;

public class CoinBox {

    private Dispenser<Coin> storage = new Dispenser(Coin.class);
    private Dispenser<Coin> change = new Dispenser<>(Coin.class);
    private Dispenser<Coin> payment = new Dispenser<>(Coin.class);

    public CoinBox() {
        unload();
    }

    public Integer storageValue() {
        return storage.totalValue();
    }

    /**
     * remove all coins from storage
     */
    private void unload() {
        storage.unload();
    }

    /**
     * add given amount of denomination to storage
     *
     * @param denomination
     * @param amount
     */
    void load(Coin denomination, Integer amount) {
        storage.add(denomination, amount);
    }

    /**
     * Insert coin that will be used as payment
     *
     * @param denomination
     */
    void insert(Coin denomination) {
        payment.add(denomination, 1);
    }

    /**
     * Take given value of coins as payment and
     * - add accepted coins to storage
     * - remove change from storage and add to change
     */
    void accept(Integer price) throws VendingMachineException {
        Integer changeValue = payment.totalValue() - price;
        storage.merge(payment);
        change.merge(storage.removeItemsOfValue(changeValue));
    }

    /**
     * return all inserted coins
     */
    Dispenser<Coin> cancel() {
        Dispenser<Coin> cancelled = this.payment.copy();
        this.payment.unload();
        return cancelled;
    }

    /**
     * return change as coins and their amount
     * @return Map<Coin, Integer>
     */
    public Dispenser<Coin> collectChange() {
        Dispenser<Coin> changeToCollect = this.change.copy();
        this.change.unload();
        return changeToCollect;
    }

    public Integer currentPayment() {
        return payment.totalValue();
    }
}
