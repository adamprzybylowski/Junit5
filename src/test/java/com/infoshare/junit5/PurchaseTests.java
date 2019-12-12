package com.infoshare.junit5;

import com.infoshare.junit5.domain.Coin;
import com.infoshare.junit5.domain.Dispenser;
import com.infoshare.junit5.domain.Product;
import com.infoshare.junit5.exception.NoChangeException;
import com.infoshare.junit5.exception.NotEnoughCoinsException;
import com.infoshare.junit5.exception.OutOfItemException;
import com.infoshare.junit5.exception.ProductNotSelectedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("VendingMachine")
public class PurchaseTests {

    @Nested
    @DisplayName("should dispense product \uD83D\uDE42")
    class SuccessfulPurchase {

        StandardVendingMachine machine;

        @BeforeEach
        private void setup() {
            ProductBox productBox = new ProductBox();
            productBox.load(Product.PEPSI, 5);
            CoinBox coinBox = new CoinBox();
            machine = new StandardVendingMachine(productBox, coinBox);
        }

        @Test
        @DisplayName("when given exact amount of coins")
        void purchase_product_without_change() throws Exception {

            // arrange

            // act
            machine.insert(Coin.ONE);
            machine.insert(Coin.TWO);
            machine.select(Product.PEPSI);

            // assert
               assertNotNull(machine.dispense());
            // TODO implement assertion
          //  fail();
        }

        @Test
        @DisplayName("only once")
        void dispense_only_one_product_after_purchase() throws Exception {

            // arrange

            assertThrows(ProductNotSelectedException.class, () -> {

                // act
                machine.insert(Coin.ONE);
                machine.insert(Coin.TWO);
                machine.select(Product.PEPSI);

                // assert
                machine.dispense();
                machine.dispense();
            });
        }

        @Nested
        @DisplayName("and should return change")
        class ReturnChange {

            private VendingMachine machine;
            private CoinBox coinBox;

            @BeforeEach
            void setup() {
                ProductBox productBox = new ProductBox();
                productBox.load(Product.COOKIES, 5);
                coinBox = new CoinBox();

                // insert 180
                coinBox.load(Coin.ONE, 10);
                coinBox.load(Coin.TWO, 10);
                coinBox.load(Coin.FIVE, 10);
                coinBox.load(Coin.TEN, 10);
                machine = new StandardVendingMachine(productBox, coinBox);
            }

            @Test
            @DisplayName("when more coins were inserted")
            void insert_coins_then_select() throws Exception {

                // act

                machine.insert(Coin.FIVE);
                machine.insert(Coin.FIVE);
                machine.insert(Coin.FIVE);
                machine.select(Product.COOKIES);

                // assert

                assertEquals(Product.COOKIES, machine.dispense());
                assertEquals(8, machine.collectChange().totalValue());
            }

            @Test
            @DisplayName("and update stored coins")
            void remove_change_from_coinbox() throws Exception {

                // act

                // insert 15
                machine.insert(Coin.FIVE);
                machine.insert(Coin.FIVE);
                machine.insert(Coin.FIVE);
                machine.select(Product.COOKIES);
                machine.dispense();
                machine.collectChange();

                // assert

                assertEquals(180 + Product.COOKIES.getValue(), coinBox.storageValue());
                assertEquals(0, coinBox.currentPayment());
            }

        }
    }



    @Nested
    @DisplayName("shouldn't dispense product \uD83D\uDE41 ")
    class FailedPurchase {

        @Test
        @DisplayName("when it run out of products")
        void order_is_cancelled_when_not_enough_products() {
            assertThrows(OutOfItemException.class, () -> {

                // TODO implement test case
                fail();
            });
        }

        @Nested
        @DisplayName("and should return payment")
        class ReturnChange {

            private VendingMachine machine;

            @BeforeEach
            void setup() {
                ProductBox productBox = new ProductBox();
                productBox.load(Product.COOKIES, 5);
                CoinBox coinBox = new CoinBox();
                machine = new StandardVendingMachine(productBox, coinBox);
            }

            @Test
            @DisplayName("when user cancels transation")
            void should_return_payment_after_cancellation() {

                CoinBox coinBox = new CoinBox();
                ProductBox productBox = new ProductBox();
                StandardVendingMachine machine = new StandardVendingMachine(productBox, coinBox);

                 machine.insert(Coin.FIVE);
                 Dispenser<Coin> returnedPayment = machine.cancel();

                 assertTrue(returnedPayment = machine.cancel());


                ;
                // TODO implement test case
               // fail();
            }

            @Test
            @DisplayName("when not enough coins were inserted")
            void given_not_enough_coins_wont_dispense_product() throws Exception {
                assertThrows(NotEnoughCoinsException.class, () -> {

                    machine.insert(Coin.FIVE);
                    machine.select(Product.COOKIES);

                    machine.dispense();
                    Dispenser<Coin> change = machine.collectChange();
                    assertEquals(5, change.totalValue());
                });
            }

            @Test
            @DisplayName("when machine can't return change")
            void order_is_cancelled_when_machine_cant_return_change() {
                assertThrows(NoChangeException.class, () -> {


                      //arrenge
                       machine.insert(Coin.FIVE);
                    machine.insert(Coin.FIVE);
                    machine.select(Product.COOKIES);
                    machine.dispense();


                    // TODO implement test case
                   // fail();
                });
            }

        }

    }
}
