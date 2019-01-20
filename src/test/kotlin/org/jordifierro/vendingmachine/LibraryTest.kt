package org.jordifierro.vendingmachine

import org.junit.Assert.assertEquals
import org.junit.Test

class LibraryTest {

    @Test
    fun test_successfully_buy_a_product() {
        VendingMachine.create()
            .refillProducts(listOf(Product.WATER, Product.WATER))
            .refillCoins(listOf(Coin.CENT_5, Coin.CENT_5))
            .insertCoins(listOf(Coin.EURO_1))
            .selectProduct(Product.WATER)
            .checkProductsInTheDispenser(listOf(Product.WATER))
            .checkCoinsInTheDispenser(listOf(Coin.CENT_5, Coin.CENT_5))
    }

    @Test
    fun test_collect_products_and_coins_empties_the_dispenser() {
        VendingMachine.create()
            .refillProducts(listOf(Product.WATER, Product.WATER))
            .refillCoins(listOf(Coin.CENT_5, Coin.CENT_5))
            .insertCoins(listOf(Coin.EURO_1))
            .selectProduct(Product.WATER)
            .checkProductsInTheDispenser(listOf(Product.WATER))
            .checkCoinsInTheDispenser(listOf(Coin.CENT_5, Coin.CENT_5))
            .collectCoins()
            .checkProductsInTheDispenser(listOf(Product.WATER))
            .checkCoinsInTheDispenser(emptyList())
            .collectProducts()
            .checkProductsInTheDispenser(emptyList())
            .checkCoinsInTheDispenser(emptyList())
    }

    @Test
    fun test_when_buy_twice_and_no_stock() {
        VendingMachine.create()
            .refillProducts(listOf(Product.WATER))
            .refillCoins(listOf(Coin.CENT_5, Coin.CENT_5, Coin.CENT_10))
            .insertCoins(listOf(Coin.EURO_1))
            .selectProduct(Product.WATER)
            .checkProductsInTheDispenser(listOf(Product.WATER))
            .checkCoinsInTheDispenser(listOf(Coin.CENT_10))
            .collectCoins()
            .collectProducts()
            .insertCoins(listOf(Coin.EURO_1, Coin.CENT_50))
            .selectProduct(Product.WATER)
            .checkProductsInTheDispenser(emptyList())
            .checkCoinsInTheDispenser(emptyList())
            .checkShowsErrorMessage(VendingMachine.ErrorMessage.OUT_OF_STOCK)
    }

    @Test
    fun test_not_enough_change_after_and_before_refill() {
        VendingMachine.create()
            .refillProducts(listOf(Product.COKE))
            .insertCoins(listOf(Coin.EURO_2))
            .selectProduct(Product.COKE)
            .checkProductsInTheDispenser(listOf())
            .checkCoinsInTheDispenser(listOf())
            .checkShowsErrorMessage(VendingMachine.ErrorMessage.NO_MONEY_CHANGE)
            .refillCoins(listOf(Coin.CENT_50))
            .selectProduct(Product.COKE)
            .checkProductsInTheDispenser(listOf(Product.COKE))
            .checkCoinsInTheDispenser(listOf(Coin.CENT_50))
            .checkShowsErrorMessage(null)
    }

    @Test
    fun test_not_enough_coins_to_buy() {
        VendingMachine.create()
            .refillProducts(listOf(Product.WATER))
            .insertCoins(listOf(Coin.CENT_50))
            .selectProduct(Product.WATER)
            .checkProductsInTheDispenser(listOf())
            .checkCoinsInTheDispenser(listOf())
            .checkShowsErrorMessage(VendingMachine.ErrorMessage.INSERT_COINS)
    }

    @Test
    fun test_not_exact_change() {
        VendingMachine.create()
            .refillProducts(listOf(Product.SPRITE))
            .refillCoins(listOf(Coin.CENT_20))
            .insertCoins(listOf(Coin.CENT_50, Coin.EURO_1))
            .selectProduct(Product.SPRITE)
            .checkShowsErrorMessage(VendingMachine.ErrorMessage.NO_MONEY_CHANGE)
            .insertCoins(listOf(Coin.CENT_10))
            .selectProduct(Product.SPRITE)
            .checkShowsErrorMessage(null)
            .checkCoinsInTheDispenser(listOf(Coin.CENT_20))
            .checkProductsInTheDispenser(listOf(Product.SPRITE))
    }

    @Test
    fun test_refund_dispenses_inserted_money() {
        VendingMachine.create()
            .insertCoins(listOf(Coin.CENT_20, Coin.CENT_5))
            .insertCoins(listOf(Coin.EURO_2, Coin.CENT_5))
            .refund()
            .checkCoinsInTheDispenser(listOf(Coin.CENT_20, Coin.CENT_5, Coin.EURO_2, Coin.CENT_5))
    }

    @Test
    fun test_products_and_money_are_accumulated_on_the_dispenser_if_noone_collects_them() {
        VendingMachine.create()
            .refillProducts(listOf(Product.WATER, Product.WATER))
            .refillCoins(listOf(Coin.CENT_5, Coin.CENT_5, Coin.CENT_10))
            .insertCoins(listOf(Coin.EURO_1))
            .selectProduct(Product.WATER)
            .insertCoins(listOf(Coin.EURO_1))
            .selectProduct(Product.WATER)
            .checkProductsInTheDispenser(listOf(Product.WATER, Product.WATER))
            .checkCoinsInTheDispenser(listOf(Coin.CENT_10, Coin.CENT_5, Coin.CENT_5))
    }

    @Test
    fun test_successfully_buy_two_products_using_first_coins_to_return_second_change() {
        VendingMachine.create()
            .refillProducts(listOf(Product.COKE, Product.SPRITE))
            .insertCoins(listOf(Coin.EURO_1))
            .insertCoins(listOf(Coin.CENT_10, Coin.CENT_10, Coin.CENT_10, Coin.CENT_10, Coin.CENT_10))
            .selectProduct(Product.COKE)
            .insertCoins(listOf(Coin.EURO_1, Coin.CENT_50))
            .selectProduct(Product.SPRITE)
            .checkProductsInTheDispenser(listOf(Product.COKE, Product.SPRITE))
            .checkCoinsInTheDispenser(listOf(Coin.CENT_10))
    }
}

private fun VendingMachine.checkProductsInTheDispenser(products: List<Product>): VendingMachine {
    assertEquals(products, this.dispensedProducts())
    return this
}

private fun VendingMachine.checkCoinsInTheDispenser(coins: List<Coin>): VendingMachine {
    assertEquals(coins, this.dispensedCoins())
    return this
}

private fun VendingMachine.checkShowsErrorMessage(errorMessage: VendingMachine.ErrorMessage?): VendingMachine {
    assertEquals(errorMessage, this.errorMessage())
    return this
}
