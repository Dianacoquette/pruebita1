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

        // 1️⃣ Validar campos vacíos
        if (email.isBlank() || password.isBlank()) {
            model.addAttribute("error", "Todos los campos son obligatorios")
            return "login"
        }

        // 2️⃣ Validar correo
        if (!email.contains("@")) {
            model.addAttribute("error", "Correo inválido")
            return "login"
        }

        // 3️⃣ Validar reCAPTCHA
        if (!recaptchaService.validar(recaptcha)) {
            model.addAttribute("error", "Confirma que no eres un robot 🤖")
            return "login"
        }

        // 4️⃣ GUARDAR EMAIL EN SESIÓN
        session.setAttribute("emailUsuario", email)

        // 5️⃣ IR AL INICIO
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

        // 1️⃣ Validar nombre
        if (nombre.length < 3) {
            model.addAttribute("error", "El nombre debe tener al menos 3 caracteres")
            return "signup"
        }

        // 2️⃣ Validar correo
        if (!email.contains("@")) {
            model.addAttribute("error", "Correo inválido")
            return "signup"
        }

        // 3️⃣ Validar contraseña
        if (password.length < 6) {
            model.addAttribute("error", "La contraseña debe tener mínimo 6 caracteres")
            return "signup"
        }

        // 4️⃣ Validar reCAPTCHA
        if (!recaptchaService.validar(recaptcha)) {
            model.addAttribute("error", "Confirma que no eres un robot 🤖")
            return "signup"
        }

        // 5️⃣ GUARDAR EMAIL EN SESIÓN
        session.setAttribute("emailUsuario", email)

        // 6️⃣ IR AL INICIO
        return "redirect:/inicio"
    }
}
