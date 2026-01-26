package com.example.pruebita1.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class RecaptchaService {

    private val secretKey = "6LfWPVcsAAAAANtohBZnZzi6hdkh76tR-9X1P5RW"

    fun validar(token: String): Boolean {

        val url = "https://www.google.com/recaptcha/api/siteverify" +
                "?secret=$secretKey&response=$token"

        val restTemplate = RestTemplate()
        val response = restTemplate.postForObject(
            url,
            null,
            Map::class.java
        )

        return response?.get("success") as Boolean
    }
}