package com.example.tiendaonline

data class User(val nameUser: String, val email: String, val password: String)

object Users {
    val users = mutableListOf<User>()

    init {
        users.add(
            User(
                nameUser = "Admin",
                email = "admin@tienda.com",
                password = "1234"
            )
        )
    }
}