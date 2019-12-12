package com.infoshare.junit5;

import com.infoshare.junit5.domain.Coin;
import com.infoshare.junit5.exception.VendingMachineException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CoinBoxTests {

    @DisplayName("should accept all coins")
    @ParameterizedTest(name = "accepts and cancels payment of {0}")
    @EnumSource(Coin.class)
    void should_accept_all_coins(Coin coin) {

        // arrange

        // TODO initialize coinbox before each test case
        fail();
        CoinBox coinbox = new CoinBox();

        // act
        coinbox.insert(coin);

        // assert
        assertAll("compare payment before and after cancellation",
                () -> assertEquals(coin.getValue(), coinbox.currentPayment()),
                () -> assertEquals(coin.getValue(), coinbox.cancel().totalValue())
        );
    }

    @DisplayName("should move accepted payment to storage")
    @ParameterizedTest(name = "accepts and cancels payment of {0}")
    @ValueSource(ints = {5,10,15})
    void should_move_accepted_payment_to_storage(Integer price) throws VendingMachineException {

        // arrange

        // TODO initialize coinbox before each test case
        CoinBox coinbox = new CoinBox();

        // act
        coinbox.load(Coin.ONE, 10);
        coinbox.load(Coin.TWO, 10);
        coinbox.insert(Coin.TEN);
        coinbox.insert(Coin.TEN);
        coinbox.accept(price);

        // assert
        // TODO add better description
        assertAll("description",
                () -> fail(), // TODO implement assertion
                () -> fail()  // TODO implement assertion
        );

    }


}