package org.jordifierro.vendingmachine


class NoPermissionException: Exception()

enum class User(val canOpenTheBackDoor: Boolean) {
    CUSTOMER(false),
    SUPPLIER(true)
}