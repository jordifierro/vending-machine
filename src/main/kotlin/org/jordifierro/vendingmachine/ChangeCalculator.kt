package org.jordifierro.vendingmachine


class NoChangeException: Exception()


fun calculateChange(availableCoins: List<Coin>, changeNeeded: Int): List<Coin> {
    if (changeNeeded == 0)
        return emptyList()

    val waysToChange = MutableList(changeNeeded + 1) { emptyList<Coin>() }
    availableCoins.sorted()
                    .reversed()
                    .forEach {
                        for (i in waysToChange.indices.reversed())
                            if (i == 0 || !waysToChange[i].isEmpty())
                                if (i + it.value < waysToChange.count()) {
                                    val coinsToChangeIPlusValue = waysToChange[i] + it
                                    if (waysToChange[i + it.value].isEmpty() ||
                                        waysToChange[i + it.value].count() > coinsToChangeIPlusValue.count())
                                        waysToChange[i + it.value] = coinsToChangeIPlusValue
                                }
                    }

    if (waysToChange[changeNeeded].isEmpty())
        throw NoChangeException()

    return waysToChange[changeNeeded]
}