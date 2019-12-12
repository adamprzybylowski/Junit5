package com.infoshare.junit5.domain;

import com.infoshare.junit5.exception.NoChangeException;
import com.infoshare.junit5.exception.OutOfItemException;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Object that holds unlimited, positive amount of given Enum types.
 *
 * @param <T>
 */
public class Dispenser<T extends Enum<T> & Item> {

    Class<T> itemType;
    Map<T, Integer> shelves;

    public Dispenser(Class<T> itemType) {
        shelves = new EnumMap<T, Integer>(itemType);
        this.itemType = itemType;
    }

    /**
     * Add given amount of items. Ignore negative values.
     * @param item
     * @param amount
     */
    public void add(T item, Integer amount) {
        shelves.compute(item, (t, a) -> Math.max(0, a == null ? amount : a + amount));
    }

    /**
     * Remove all items, that is set all amounts to 0.
     */
    public void unload() {
        shelves.keySet().forEach((item -> {
            shelves.put(item, 0);
        }));
    }

    /**
     * return amount of given item
     * @param item
     * @return
     */
    public Integer amountOf(T item) {
        return shelves.get(item);
    }

    /**
     * Return one item of given type. Throw OutOfItemException if there are not items left.
     *
     * @param type
     * @return
     * @throws OutOfItemException
     */
    public T dispense(T type) throws OutOfItemException {
        Integer amount = shelves.getOrDefault(type, 0);
        if (amount == 0) {
            throw new OutOfItemException(type.name());
        }
        shelves.put(type, amount - 1);
        return type;
    }

    /**
     * Remove all items from source and add to shelves
     *
     * @param source
     */
    public void merge(Dispenser<T> source) {
        source.shelves.forEach((coin, amount) -> {
            this.add(coin, amount);
        });
        source.unload();
    }

    /**
     * Remove given amount of items. Amount of item can't be negative.
     *
     * @param item
     * @param amount
     */
    public void remove(T item, Integer amount) {
        shelves.compute(item, (t, a) -> Math.max(0, a == null ? 0 : a - amount));
    }

    /**
     * return a copy of current dispenser
     * @return Dispenser<T>
     */
    public Dispenser<T> copy() {
        Dispenser<T> copy = new Dispenser<>(itemType);
        shelves.forEach((coin, integer) -> {
            copy.add(coin, integer);
        });
        return copy;
    }

    /**
     * Return sum of all item's values.
     * @return sum
     */
    public Integer totalValue() {
        return shelves.entrySet().stream()
                .mapToInt((e) -> e.getKey().getValue() * e.getValue())
                .sum();
    }

    /**
     * Selects items of total value equal to given value
     * and returns them in a new Dispenser.
     *
     * @param value
     * @return
     * @throws NoChangeException
     */
    public Dispenser<T> removeItemsOfValue(Integer value) throws NoChangeException {
        Dispenser<T> change = new Dispenser<>(itemType);
        Integer rest = collectChange(this.copy(), change, value);
        if (rest > 0) {
            throw new NoChangeException();
        }
        this.removeAll(change);
        return change;
    }

    private Integer collectChange(Dispenser<T> availableItems, Dispenser<T> change, Integer rest) {
        if (rest == 0) {
            return rest;
        }
        Iterator<Map.Entry<T, Integer>> iterator = availableItems.shelves.entrySet().iterator();
        if (!iterator.hasNext()) {
            return rest;
        }
        Map.Entry<T, Integer> next = iterator.next();
        T item = next.getKey();
        Integer count = 0;
        Integer amount = next.getValue();
        if (amount > 0) {
            // remove all required coins or all available coins
            count = Math.min(rest / item.getValue(), amount);
            change.add(item, count);
        }
        availableItems.shelves.remove(item);
        return collectChange(availableItems, change, rest - count * item.getValue());
    }

    private void removeAll(Dispenser<T> dispenser) {
        dispenser.shelves.forEach((coin, amount) -> {
            this.remove(coin, amount);
        });
    }

}
