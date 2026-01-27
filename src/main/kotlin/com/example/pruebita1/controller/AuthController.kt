package com.example.pruebita1.controller

import com.example.pruebita1.service.RecaptchaService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import jakarta.servlet.http.HttpSession

@Controller
class AuthController(
    private val recaptchaService: RecaptchaService
) {

    // ---------------- LOGIN ----------------

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @PostMapping("/login")
    fun procesarLogin(
        @RequestParam email: String,
        @RequestParam password: String,
        @RequestParam("g-recaptcha-response") recaptcha: String,
        model: Model,
        session: HttpSession
    ): String {

        // 1️⃣ LIMPIAR ESPACIOS
        val emailLimpio = email.trim()
        val passwordLimpio = password.trim()

        // 2️⃣ Validar campos vacíos o solo espacios
        if (emailLimpio.isEmpty() || passwordLimpio.isEmpty()) {
            model.addAttribute("error", "No puedes ingresar solo espacios")
            return "login"
        }

        // 3️⃣ Validar correo
        if (!emailLimpio.contains("@")) {
            model.addAttribute("error", "Correo inválido")
            return "login"
        }

        // 4️⃣ Validar reCAPTCHA
        if (!recaptchaService.validar(recaptcha)) {
            model.addAttribute("error", "Confirma que no eres un robot")
            return "login"
        }

        // 5️⃣ GUARDAR EMAIL EN SESIÓN
        session.setAttribute("emailUsuario", emailLimpio)

        // 6️⃣ IR AL INICIO
        return "redirect:/inicio"
    }

    // ---------------- SIGNUP ----------------

    @GetMapping("/signup")
    fun signup(): String {
        return "signup"
    }

    @PostMapping("/signup")
    fun procesarSignup(
        @RequestParam nombre: String,
        @RequestParam email: String,
        @RequestParam password: String,
        @RequestParam("g-recaptcha-response") recaptcha: String,
        model: Model,
        session: HttpSession
    ): String {

        // 1️⃣ LIMPIAR ESPACIOS
        val nombreLimpio = nombre.trim()
        val emailLimpio = email.trim()
        val passwordLimpio = password.trim()

        // 2️⃣ Validar campos con solo espacios
        if (nombreLimpio.isEmpty() || emailLimpio.isEmpty() || passwordLimpio.isEmpty()) {
            model.addAttribute("error", "No se permiten campos con solo espacios")
            return "signup"
        }

        // 3️⃣ Validar nombre
        if (nombreLimpio.length < 3) {
            model.addAttribute("error", "El nombre debe tener al menos 3 caracteres")
            return "signup"
        }

        // 4️⃣ Validar correo
        if (!emailLimpio.contains("@")) {
            model.addAttribute("error", "Correo inválido")
            return "signup"
        }

        // 5️⃣ Validar contraseña
        if (passwordLimpio.length < 6) {
            model.addAttribute("error", "La contraseña debe tener mínimo 6 caracteres")
            return "signup"
        }

        // 6️⃣ Validar reCAPTCHA
        if (!recaptchaService.validar(recaptcha)) {
            model.addAttribute("error", "Confirma que no eres un robot")
            return "signup"
        }

        // 7️⃣ GUARDAR EMAIL EN SESIÓN
        session.setAttribute("emailUsuario", emailLimpio)

        // 8️⃣ IR AL INICIO
        return "redirect:/inicio"
    }
}
