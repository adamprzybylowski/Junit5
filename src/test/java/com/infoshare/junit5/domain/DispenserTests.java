package com.infoshare.junit5.domain;

import com.infoshare.junit5.exception.NoChangeException;
import com.infoshare.junit5.exception.OutOfItemException;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DispenserTests {

    private Dispenser<Coin> dispenser;

    @BeforeEach
    void setUp() {
        dispenser = new Dispenser<>(Coin.class);
    }

    @Test
    void should_add_loaded_items_of_same_type() {

        // act
        dispenser.add(Coin.ONE, 5);
        dispenser.add(Coin.ONE, 3);
        dispenser.add(Coin.TWO, 3);
        dispenser.add(Coin.TWO, 2);

        // act
        // TODO try out other Integer assertions
        fail();

        assertThat(dispenser.amountOf(Coin.ONE)).isPositive();
        assertThat(dispenser.amountOf(Coin.ONE)).isBetween(0, 10);
        assertThat(dispenser.amountOf(Coin.ONE)).isEqualTo(8);

        assertThat(dispenser.amountOf(Coin.TWO)).isNotZero();
        assertThat(dispenser.amountOf(Coin.TWO)).isNotNegative();
        assertThat(dispenser.amountOf(Coin.TWO)).isEqualTo(5);

    }

    @Test
    void should_be_empty_after_unloading() {

        // act

        dispenser.add(Coin.ONE, 5);
        dispenser.add(Coin.TWO, 5);
        dispenser.unload();

        // TODO replace with Assertions.assertThat
        fail();
        assertEquals(0, dispenser.amountOf(Coin.ONE));
        assertEquals(0, dispenser.amountOf(Coin.TWO));
    }

    @ParameterizedTest(name = "{0} ONEs + {1} TWOs = {2}")
    @CsvSource({
            "0, 0, 0",
            "2, 0, 2",
            "4, 4, 12",
            "2, 2, 6"
    })
    void should_calculate_total_value_of_ones_and_twos(Integer ones, Integer twos, Integer total) {
        dispenser.add(Coin.ONE, ones);
        dispenser.add(Coin.TWO, twos);

        // TODO replace with Assertions.assertThat
        fail();
        assertEquals(total, dispenser.totalValue());
    }

    // TODO implement using @ParametrizedTest and @CsvSource
    void should_calculate_total_value_of_fives_and_tens() {
        fail();
    }

    @Test
    void should_remove_dispensed_items() throws Exception {
        dispenser.add(Coin.ONE, 5);
        dispenser.add(Coin.TWO, 5);
        dispenser.dispense(Coin.TWO);
        dispenser.dispense(Coin.ONE);
        dispenser.dispense(Coin.ONE);

        assertThat(dispenser.amountOf(Coin.ONE)).isEqualTo(3);
        assertThat(dispenser.amountOf(Coin.TWO)).isEqualTo(4);
    }

    // catch exception JUnit style
    @Test
    void should_throw_out_of_item_exception_junit_style() {
        assertThrows(OutOfItemException.class, () -> {
            dispenser.dispense(Coin.TWO);
        });
    }

    // catch exception AssertJ style
    @Test
    void should_throw_out_of_item_exception_assertj_style() {
        assertThatThrownBy(() -> {
            dispenser.dispense(Coin.TWO);
        })
                .hasMessage("TWO not available")
                .hasNoCause()
                .isInstanceOf(OutOfItemException.class);
    }

    @Test
    void should_merge_content_of_another_dispenser() {

        // arrange
        dispenser.add(Coin.ONE, 5);
        dispenser.add(Coin.TWO, 5);
        Dispenser<Coin> another = new Dispenser<>(Coin.class);
        another.add(Coin.ONE, 5);
        another.add(Coin.TWO, 1);

        // act
        dispenser.merge(another);

        assertAll(
                () -> assertEquals(10, dispenser.amountOf(Coin.ONE)),
                () -> assertEquals(6, dispenser.amountOf(Coin.TWO)),
                () -> assertEquals(0, another.amountOf(Coin.ONE)),
                () -> assertEquals(0, another.amountOf(Coin.TWO))
        );
    }

    @Test
    void should_copy_itself() {
        dispenser.add(Coin.ONE, 23);
        dispenser.add(Coin.TWO, 12);
        Dispenser<Coin> copy = dispenser.copy();

        // TODO implement test case
        fail();
    }

    @ParameterizedTest(name = "use {0} ONEs, {1} TWOs, {2} FIVEs to count {3}")
    @CsvSource({
            "2, 0, 0, 2",
            "4, 4, 0, 5",
            "0, 0, 0, 0",
            "2, 2, 0, 2",
            "2, 2, 0, 6"
    })
    void should_return_given_value_as_new_dispenser(Integer ones, Integer twos, Integer fives, Integer valueToRemove) throws NoChangeException {
        dispenser.add(Coin.ONE, ones);
        dispenser.add(Coin.TWO, twos);
        dispenser.add(Coin.FIVE, fives);
        Dispenser<Coin> change = this.dispenser.removeItemsOfValue(valueToRemove);
        assertEquals(valueToRemove, change.totalValue());
    }

    @ParameterizedTest(name = " use {0} ONEs, {1} TWOs and {2} FIVEs to count {3}")
    @CsvSource({
            "0, 0, 0, 2",
            "4, 4, 0, 15",
            "2, 2, 1, 12",
            "1, 1, 1, 4"
    })
    void should_throw_no_change_exception(Integer ones, Integer twos, Integer fives, Integer valueToRemove) throws NoChangeException {
        assertThrows(NoChangeException.class, () -> {
            dispenser.add(Coin.ONE, ones);
            dispenser.add(Coin.TWO, twos);
            dispenser.add(Coin.FIVE, fives);
            Dispenser<Coin> change = this.dispenser.removeItemsOfValue(valueToRemove);
        });
    }

    @Test
    void should_ignore_negative_value() throws NoChangeException {
        dispenser.add(Coin.ONE, 10);
        dispenser.add(Coin.TWO, 10);
        dispenser.add(Coin.FIVE, 10);
        Dispenser<Coin> change = this.dispenser.removeItemsOfValue(-1);
        assertEquals(0, change.totalValue());
    }

    @ParameterizedTest
    @MethodSource("intProvider")
    void should_calculate_change(int amount) throws Exception {
        dispenser.add(Coin.ONE, 10);
        dispenser.add(Coin.TWO, 10);
        dispenser.add(Coin.FIVE, 10);
        Dispenser<Coin> change = this.dispenser.removeItemsOfValue(amount);
        assertTrue(change.totalValue() > 0);
    }

    static Stream<Integer> intProvider() {
        return Stream.of(1, 12, 7);
    }

    @ParameterizedTest
    @MethodSource("argumentsProvider")
    void should_calculate_change(int amount, int result) throws Exception {
        dispenser.add(Coin.ONE, 10);
        dispenser.add(Coin.TWO, 10);
        dispenser.add(Coin.FIVE, 10);
        Dispenser<Coin> change = this.dispenser.removeItemsOfValue(amount);
        assertEquals(result, change.totalValue());
    }

    static Stream<Arguments> argumentsProvider() {
        return Stream.of(
                arguments(1, 1),
                arguments(12, 12),
                arguments(7, 7)
        );
    }

    // TODO implement using @ParametrizedTest and @MethodSource
    //@ParameterizedTest
    //@MethodSource("fiveAndTensProvider")
    void should_calculate_total_value_of_fives_and_tens(int fives, int tens, int total) {
        fail();
    }

    @Test
    void should_not_add_negative_amounts() {
        // act
        dispenser.add(Coin.ONE, -10);

        // assert
        // TODO implement assertions
        fail();
    }

    @Test
    void amount_cant_be_lower_than_zero_after_removal() {
        dispenser.remove(Coin.ONE, 10);

        // assert
        // TODO implement assertions
        fail();
    }
}
