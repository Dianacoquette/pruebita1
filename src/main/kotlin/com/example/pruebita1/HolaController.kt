package com.example.pruebita1

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HolaController {

    @GetMapping("/")
    fun hola(): String {
        return "Hola mundo 🌎 desde Spring Boot con Kotlin 🚀"
    }
}