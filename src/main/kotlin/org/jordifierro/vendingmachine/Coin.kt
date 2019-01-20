package org.jordifierro.vendingmachine


enum class Coin(val value: Int) {
    CENT_5(5),
    CENT_10(10),
    CENT_20(20),
    CENT_50(50),
    EURO_1(100),
    EURO_2(200)
}

fun List<Coin>.value() = this.sumBy { it.value }