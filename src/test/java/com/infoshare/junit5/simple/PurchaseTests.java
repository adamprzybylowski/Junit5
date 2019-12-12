package com.infoshare.junit5.simple;

import com.infoshare.junit5.domain.Coin;
import com.infoshare.junit5.domain.Product;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseTests {

    private VendingMachine machine;

    @Test
    public void should_dispense_pepsi() {

        // arrange
        machine = new SimpleVendingMachine();

        // act
        machine.select(Product.PEPSI);
        machine.insert(Coin.ONE);
        machine.insert(Coin.TWO);
        BoughtItem item = machine.dispense();

        // assert
        assertTrue(Product.PEPSI.equals(item.type));
        assertFalse(Product.CHOCOLATE_BAR.equals(item.type));

        assertEquals(Product.PEPSI, item.type);
        assertNotEquals(Product.COOKIES, item.type);
        assertSame(Product.PEPSI, item.type);
    }

    @Test
    public void should_dispense_cookies() {
        // arrange
        machine = new SimpleVendingMachine();

        // act
        machine.select(Product.COOKIES);
        machine.insert(Coin.FIVE);
        machine.insert(Coin.TWO);
        BoughtItem item = machine.dispense();

        // assert
        assertTrue(Product.COOKIES.equals((item.type)));
        assertFalse(Product.CHOCOLATE_BAR.equals(item.type));

        assertEquals(Product.COOKIES, item.type);
        assertNotEquals(Product.CHOCOLATE_BAR, item.type);
        assertSame(Product.COOKIES, item.type);

    }

 //   @Disabled
    @Test
    void should_not_dispense_product_unless_it_was_selected() {

        // TODO enable test
     //   fail();

        // arrange
        machine = new SimpleVendingMachine();

        // act


        // assert
        assertThrows(ProductNotSelectedException.class, () -> {
            machine.dispense();
        });

    }

    @Test
    public void given_exact_amount_of_coins_wont_give_change() {

        // TODO implement test case
       // fail();

        // arrange
        machine = new SimpleVendingMachine();

        // act
        machine.insert(Coin.FIVE);
        machine.insert(Coin.TWO);
        machine.select(Product.COOKIES);
        BoughtItem dispense = machine.dispense();

        // assert
        assertTrue(dispense.change.size()==0);
    }

    @Test
    public void given_more_coins_wont_give_change() {

        // TODO implement test case
       // fail();

        // arrange
        machine = new SimpleVendingMachine();

        // act
        machine.insert(Coin.FIVE);
        machine.insert(Coin.FIVE);
        machine.select(Product.PEPSI);
        BoughtItem dispense = machine.dispense();

        // assert
        assertTrue( dispense.change.size()==0);
    }

    @Test
    void given_not_enough_coins_wont_dispense_product() {

        // TODO implement test case
        fail();

        // arrange

        // act

        // assert

    }

}
