package com.infoshare.junit5.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.*;

public class DispenserLoadingTests {

    @ParameterizedTest
    @EnumSource(Coin.class)
    void should_load_and_count_value_of_all_coins(Coin c) {
        Dispenser<Coin> dispenser = new Dispenser<>(Coin.class);
        dispenser.add(c, 1);
        assertAll("amount and value is correct",
                () -> assertEquals(1, dispenser.amountOf(c)),
                () -> assertEquals(c.getValue(), dispenser.totalValue())
        );
    }

    // TODO implement using @ParameterizedTest and @EnumSource
    void should_load_and_count_value_of_all_products(Product p) {
        fail();
    }

}
