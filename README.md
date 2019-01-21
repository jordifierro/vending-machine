# Vending Machine

The goal is to design a small software component that handles the operation of a Vending Machine. We want you to think in how a Vending Machine works in real-life, and implement methods that provide the interactions that both a customer and a supplier can perform. This machine accepts only coins, and lets you select one product at a time. The functionality that will need to be provided by your software component is described below:

- Accepts coins of 0.05€, 0.10€, 0.20€, 0.50€, 1€, 2€ 
- Allow user to select products Coke(1.50€), Sprite(1.40€), Water(0.90€) 
- Allow user to take refund by canceling the request.
- Return selected product and remaining change if any
- Allow the vending machine supplier to refill beverages & coins for change.

## Solution

This library modelizes a Vending Machine with a functional programming approach.
Machine is composed by the following parts:

- A door that can only be opened by supplier.
- A product storage that can be refilled.
- A coin storage that can be refilled.
- An inserted coins bucket.
- An error message screen to show the errors like out of stock.
- A product dispenser to return the bought products.
- A coin dispenser to return the change.

And there are the following elements modelized:

- User
- Product
- Coin
- VendingMachine

Integration tests are examples of how this library can be used.
They can be found in `LibraryTest`. This is an example:

```kotlin
VendingMachine.create()
            .openBackDoor(User.SUPPLIER)
            .refillProducts(listOf(Product.WATER))
            .refillCoins(listOf(Coin.CENT_5, Coin.CENT_5))
            .closeBackDoor()
            .insertCoins(listOf(Coin.EURO_1))
            .selectProduct(Product.WATER)
            .collectProducts()
            .collectCoins()
```

There is a treatment difference between errors and exceptions:
* Controlled errors are displayed as an error message of the machine,
for example when a product is out of stock.
* Exceptions are thrown when there are wrong usages,
for example when a customer tries to open the back door.

The algorithm to solve the coin change uses a dynamic programming approach
to calculate if there is a way to return change with a set of coins,
and also returns the minimum amount of coins.

Every part is implemented focusing on readability, being easily modified and tested unitarily.
