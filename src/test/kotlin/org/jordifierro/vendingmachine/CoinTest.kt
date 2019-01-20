package org.jordifierro.vendingmachine

import org.junit.Assert.assertEquals
import org.junit.Test

class CoinTest {

    @Test
    fun test_list_of_coins_value_is_sum_of_coin_values() {
        assertEquals(0,
                     emptyList<Coin>().value())
        assertEquals(Coin.CENT_10.value,
                     listOf(Coin.CENT_10).value())
        assertEquals(Coin.CENT_10.value + Coin.EURO_2.value,
                     listOf(Coin.CENT_10, Coin.EURO_2).value())
        assertEquals(Coin.CENT_10.value + Coin.EURO_2.value + Coin.CENT_50.value + Coin.CENT_50.value,
                     listOf(Coin.CENT_50, Coin.CENT_10, Coin.CENT_50, Coin.EURO_2).value())
    }
}
