package org.jordifierro.vendingmachine


data class VendingMachine internal constructor(private val productStorage: Set<Product>,
                                               private val coinStorage: Set<Coin>,
                                               private val insertedCoins: Set<Coin>,
                                               private val errorMessage: String?,
                                               private val productDispenser: Set<Product>,
                                               private val coinDispenser: Set<Coin>) {

    companion object {
        fun create(): VendingMachine {
            return VendingMachine(setOf(), setOf(), setOf(), null, setOf(), setOf())
        }
    }

    fun refillProducts(products: Set<Product>) = copy(productStorage = this.productStorage + products)

    fun refillCoins(coins: Set<Coin>) = copy(coinStorage = this.coinStorage + coins)

    fun insertCoins(coins: Set<Coin>) = copy(insertedCoins = this.insertedCoins + coins)

    fun selectProduct(product: Product): VendingMachine {
        return this
    }

    fun refund() = copy(insertedCoins = setOf(), coinDispenser = this.coinDispenser + this.insertedCoins)

    fun errorMessage() = this.errorMessage

    fun dispensedProducts() = this.productDispenser

    fun collectProducts() = copy(productDispenser = setOf())

    fun dispensedCoins() = this.coinDispenser

    fun collectCoins() = copy(coinDispenser = setOf())
}