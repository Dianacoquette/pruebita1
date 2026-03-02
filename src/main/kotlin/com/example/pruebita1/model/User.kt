package com.example.pruebita1.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "USERS")
data class User(

    @Id
    @Column(name = "ID")
    val id: Int = 0,

    @Column(name = "USERNAME")
    var username: String = "",

    @Column(name = "EMAIL")
    var email: String = "",

    @Column(name = "PASSWD")
    var passwd: String = "",

    @Column(name = "NOMBRE_COMPLETO")
    var nombreCompleto: String = "",

    @Column(name = "TELEFONO")
    var telefono: String = "",

    @Column(name = "ACTIVO")
    var activo: Boolean = true,

    @Column(name = "FECHA_CREACION", updatable = false)
    var fechaCreacion: LocalDateTime = LocalDateTime.now()
)