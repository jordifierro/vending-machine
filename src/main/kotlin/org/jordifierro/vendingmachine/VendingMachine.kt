package org.jordifierro.vendingmachine


class ClosedDoorException: Exception()

data class VendingMachine internal constructor(private val isBackDoorOpen: Boolean = false,
                                               private val productStorage: List<Product> = emptyList(),
                                               private val coinStorage: List<Coin> = emptyList(),
                                               private val insertedCoins: List<Coin> = emptyList(),
                                               private val errorMessage: ErrorMessage? = null,
                                               private val productDispenser: List<Product> = emptyList(),
                                               private val coinDispenser: List<Coin> = emptyList()) {

    enum class ErrorMessage {
        OUT_OF_STOCK,
        NO_MONEY_CHANGE,
        INSERT_COINS
    }

    companion object {
        fun create() = VendingMachine()
    }

    fun openBackDoor(user: User): VendingMachine {
        if (user.canOpenTheBackDoor) return copy(isBackDoorOpen = true)
        throw NoPermissionException()
    }

    fun closeBackDoor(): VendingMachine = copy(isBackDoorOpen = false)

    fun refillProducts(products: List<Product>): VendingMachine {
        if (isBackDoorOpen) return copy(productStorage = this.productStorage + products)
        throw ClosedDoorException()
    }

    fun refillCoins(coins: List<Coin>): VendingMachine {
        if (isBackDoorOpen) return copy(coinStorage = this.coinStorage + coins)
        throw ClosedDoorException()
    }

    fun insertCoins(coins: List<Coin>): VendingMachine = copy(insertedCoins = this.insertedCoins + coins)

    fun selectProduct(product: Product): VendingMachine {
        if (product !in this.productStorage)
            return copy(errorMessage = ErrorMessage.OUT_OF_STOCK)

        if (this.insertedCoins.value() < product.cost)
            return copy(errorMessage = ErrorMessage.INSERT_COINS)

        try {
            val changeCoins = calculateChange(availableCoins = coinStorage + insertedCoins,
                                              changeNeeded = insertedCoins.value() - product.cost)
            return copy(productStorage = this.productStorage - product,
                 coinStorage = this.coinStorage + this.insertedCoins - changeCoins,
                 insertedCoins = emptyList(),
                 errorMessage = null,
                 productDispenser = this.productDispenser + product,
                 coinDispenser = this.coinDispenser + changeCoins)
        } catch (e: NoChangeException) {
            return copy(errorMessage = ErrorMessage.NO_MONEY_CHANGE)
        }
    }

    fun refund(): VendingMachine = copy(insertedCoins = emptyList(),
                                        coinDispenser = this.coinDispenser + this.insertedCoins)

    fun errorMessage(): ErrorMessage? = this.errorMessage

    fun dispensedProducts(): List<Product> = this.productDispenser

    fun collectProducts(): VendingMachine = copy(productDispenser = emptyList())

    fun dispensedCoins(): List<Coin> = this.coinDispenser

    fun collectCoins(): VendingMachine = copy(coinDispenser = emptyList())
}