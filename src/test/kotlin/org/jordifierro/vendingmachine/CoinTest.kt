package org.jordifierro.vendingmachine

import org.junit.Assert.assertEquals
import org.junit.Test

class CoinTest {

    @Test
    fun test_value_of_empty_list_is_0() {
        assertEquals(0,
                     emptyList<Coin>().value())
    }

    @Test
    fun test_value_of_a_list_with_a_single_coin_is_that_list_value() {
        assertEquals(Coin.CENT_10.value,
            listOf(Coin.CENT_10).value())
    }

    @Test
    fun test_value_of_multiple_coins_list_is_the_sum_of_their_values() {
        assertEquals(Coin.CENT_10.value + Coin.EURO_2.value,
            listOf(Coin.CENT_10, Coin.EURO_2).value())
    }

    @Test
    fun test_value_is_correct_sum_when_repeated_elements() {
        assertEquals(Coin.CENT_10.value + Coin.EURO_2.value + Coin.CENT_50.value + Coin.CENT_50.value,
            listOf(Coin.CENT_50, Coin.CENT_10, Coin.CENT_50, Coin.EURO_2).value())
    }
}
