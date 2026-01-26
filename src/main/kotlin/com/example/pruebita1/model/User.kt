package com.example.pruebita1.model

import jakarta.persistence.*

@Entity
@Table(name = "USERS")
data class User(

    @Id
    @Column(name = "ID")
    val id: Int = 0,

    @Column(name = "USERNAME")
    val username: String = "",

    @Column(name = "EMAIL")
    val email: String = "",

    @Column(name = "PASSWD")
    val passwd: String = ""
)
