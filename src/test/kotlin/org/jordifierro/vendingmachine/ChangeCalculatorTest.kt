package org.jordifierro.vendingmachine

import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

class ChangeCalculatorTest {

    @Test
    fun test_0_change_returns_empty_list_of_coins() {
        assertEquals(emptyList<Coin>(), calculateChange(emptyList(), 0))
    }

    @Test
    fun test_empty_change_coins_throws_no_change_exception() {
        try {
            calculateChange(emptyList(), 10)
            fail()
        } catch (e: NoChangeException) {}
    }

    @Test
    fun test_not_exact_change_throws_no_change_exception() {
        try {
            calculateChange(listOf(Coin.CENT_10, Coin.CENT_50), 40)
            fail()
        } catch (e: NoChangeException) {}
    }

    @Test
    fun test_one_coin_change() {
        assertEquals(listOf(Coin.CENT_10), calculateChange(listOf(Coin.CENT_10), 10))
    }

    @Test
    fun test_multiple_coin_change() {
        assertEquals(listOf(Coin.EURO_1, Coin.CENT_20, Coin.CENT_10),
                     calculateChange(listOf(Coin.CENT_10, Coin.CENT_20, Coin.EURO_1), 130))
    }

    @Test
    fun test_returns_minimum_number_of_coins_change() {
        assertEquals(listOf(Coin.CENT_50, Coin.CENT_10),
                     calculateChange(listOf(Coin.CENT_10, Coin.CENT_20, Coin.CENT_20, Coin.CENT_20, Coin.CENT_50),
                                      60))
    }
}
