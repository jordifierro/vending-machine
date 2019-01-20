package org.jordifierro.vendingmachine

import org.junit.Assert.assertEquals
import org.junit.Test

class VendingMachineTest {

    @Test
    fun test_refill_products_adds_products_to_storage() {
        VendingMachine(productStorage = listOf(Product.COKE, Product.COKE))
            .refillProducts(listOf(Product.WATER))
            .shouldReturnVendingMachineWith(productStorage = listOf(Product.COKE, Product.COKE, Product.WATER))
    }

    @Test
    fun test_refill_coins_adds_coins_to_storage() {
        VendingMachine(coinStorage = listOf(Coin.CENT_10, Coin.CENT_5, Coin.EURO_2))
            .refillCoins(listOf(Coin.CENT_20, Coin.EURO_2))
            .shouldReturnVendingMachineWith(coinStorage = listOf(Coin.CENT_10, Coin.CENT_5,
                                                                Coin.EURO_2, Coin.CENT_20, Coin.EURO_2))
    }

    @Test
    fun test_insert_coins_saves_coins_as_inserted() {
        VendingMachine(insertedCoins = listOf(Coin.CENT_10, Coin.CENT_5, Coin.EURO_2))
            .insertCoins(listOf(Coin.CENT_20, Coin.EURO_2))
            .shouldReturnVendingMachineWith(insertedCoins = listOf(Coin.CENT_10, Coin.CENT_5, Coin.EURO_2,
                                                                   Coin.CENT_20, Coin.EURO_2))
    }

    @Test
    fun test_select_out_of_stock_product() {
        VendingMachine()
            .selectProduct(Product.COKE)
            .shouldReturnVendingMachineWith(errorMessage = VendingMachine.ErrorMessage.OUT_OF_STOCK)
    }

    @Test
    fun test_not_enough_money_shows_insert_coin_error() {
        VendingMachine(productStorage = listOf(Product.COKE))
            .selectProduct(Product.COKE)
            .shouldReturnVendingMachineWith(productStorage = listOf(Product.COKE),
                                            errorMessage = VendingMachine.ErrorMessage.INSERT_COINS)
    }

    @Test
    fun test_when_empty_coin_storage_to_return_change_shows_not_money_change_error() {
        VendingMachine(productStorage = listOf(Product.COKE),
                       insertedCoins = listOf(Coin.EURO_2))
            .selectProduct(Product.COKE)
            .shouldReturnVendingMachineWith(productStorage = listOf(Product.COKE),
                                            insertedCoins = listOf(Coin.EURO_2),
                                            errorMessage = VendingMachine.ErrorMessage.NO_MONEY_CHANGE)
    }

    @Test
    fun test_when_not_enough_coins_to_return_exact_change_shows_not_money_change_error() {
        VendingMachine(productStorage = listOf(Product.SPRITE),
                       coinStorage = listOf(Coin.CENT_50, Coin.CENT_20),
                       insertedCoins = listOf(Coin.EURO_2))
            .selectProduct(Product.SPRITE)
            .shouldReturnVendingMachineWith(productStorage = listOf(Product.SPRITE),
                                            coinStorage = listOf(Coin.CENT_50, Coin.CENT_20),
                                            insertedCoins = listOf(Coin.EURO_2),
                                            errorMessage = VendingMachine.ErrorMessage.NO_MONEY_CHANGE)
    }

    @Test
    fun test_when_select_product_succeed_dispenses_product_and_change_and_keeps_the_money() {
        VendingMachine(productStorage = listOf(Product.SPRITE),
            coinStorage = listOf(Coin.CENT_50, Coin.CENT_10),
            insertedCoins = listOf(Coin.EURO_2))
            .selectProduct(Product.SPRITE)
            .shouldReturnVendingMachineWith(productStorage = emptyList(),
                                            coinStorage = listOf(Coin.EURO_2),
                                            coinDispenser = listOf(Coin.CENT_50, Coin.CENT_10),
                                            productDispenser = listOf(Product.SPRITE))
    }

    @Test
    fun test_when_dispensing_add_product_and_coins_to_already_dispensed() {
        VendingMachine(productStorage = listOf(Product.SPRITE),
            coinStorage = listOf(Coin.CENT_50, Coin.CENT_10),
            insertedCoins = listOf(Coin.EURO_2),
            coinDispenser = listOf(Coin.EURO_1),
            productDispenser = listOf(Product.WATER))
            .selectProduct(Product.SPRITE)
            .shouldReturnVendingMachineWith(productStorage = emptyList(),
                coinStorage = listOf(Coin.EURO_2),
                coinDispenser = listOf(Coin.EURO_1, Coin.CENT_50, Coin.CENT_10),
                productDispenser = listOf(Product.WATER, Product.SPRITE))
    }


    @Test
    fun test_when_more_money_than_needed_dispenses_it_back() {
        VendingMachine(productStorage = listOf(Product.COKE),
            coinStorage = emptyList(),
            insertedCoins = listOf(Coin.CENT_50, Coin.EURO_1, Coin.EURO_2, Coin.EURO_2))
            .selectProduct(Product.COKE)
            .shouldReturnVendingMachineWith(productStorage = emptyList(),
                coinStorage = listOf(Coin.CENT_50, Coin.EURO_1),
                coinDispenser = listOf(Coin.EURO_2, Coin.EURO_2),
                productDispenser = listOf(Product.COKE))
    }

    @Test
    fun test_refund_puts_inserted_coins_in_coin_dispenser() {
        VendingMachine(insertedCoins = listOf(Coin.CENT_10, Coin.CENT_5, Coin.EURO_2))
            .refund()
            .shouldReturnVendingMachineWith(insertedCoins = emptyList(),
                                            coinDispenser = listOf(Coin.CENT_10, Coin.CENT_5, Coin.EURO_2))
    }

    @Test
    fun test_error_message_returns_correct_attribute() {
        assertEquals(
            VendingMachine.ErrorMessage.OUT_OF_STOCK,
                     VendingMachine(errorMessage = VendingMachine.ErrorMessage.OUT_OF_STOCK).errorMessage())
    }

    @Test
    fun test_dispensed_products_returns_correct_attribute() {
        assertEquals(listOf(Product.COKE, Product.WATER),
                     VendingMachine(productDispenser = listOf(Product.COKE, Product.WATER)).dispensedProducts())
    }

    @Test
    fun test_collect_products_empties_the_product_dispenser() {
        VendingMachine(productDispenser = listOf(Product.WATER))
            .collectProducts()
            .shouldReturnVendingMachineWith(productDispenser = emptyList())
    }

    @Test
    fun test_dispensed_coins_returns_correct_attribute() {
        assertEquals(listOf(Coin.EURO_2, Coin.CENT_10),
                     VendingMachine(coinDispenser = listOf(Coin.EURO_2, Coin.CENT_10)).dispensedCoins())
    }

    @Test
    fun test_collect_coins_empties_the_coins_dispenser() {
        VendingMachine(coinDispenser = listOf(Coin.CENT_10))
            .collectCoins()
            .shouldReturnVendingMachineWith(coinDispenser = emptyList())
    }
}

fun VendingMachine.shouldReturnVendingMachineWith(productStorage: List<Product> = emptyList(),
                                                  coinStorage: List<Coin> = emptyList(),
                                                  insertedCoins: List<Coin> = emptyList(),
                                                  errorMessage: VendingMachine.ErrorMessage? = null,
                                                  productDispenser: List<Product> = emptyList(),
                                                  coinDispenser: List<Coin> = emptyList()): VendingMachine {
    assertEquals(VendingMachine(productStorage = productStorage,
                                coinStorage = coinStorage,
                                insertedCoins = insertedCoins,
                                errorMessage = errorMessage,
                                productDispenser = productDispenser,
                                coinDispenser = coinDispenser),
                 this)
    return this
}