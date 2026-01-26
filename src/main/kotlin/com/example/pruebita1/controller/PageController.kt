package com.example.pruebita1.controller

import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class PageController {

    // 🔒 RAÍZ: SIEMPRE AL SIGNUP
    @GetMapping("/")
    fun root(): String {
        return "redirect:/signup"
    }

    // 🔐 INICIO PROTEGIDO
    @GetMapping("/inicio")
    fun inicio(model: Model, session: HttpSession): String {

        val email = session.getAttribute("emailUsuario") as String?

        // ❌ Si no hay sesión → signup
        if (email == null) {
            return "redirect:/signup"
        }

        // ✅ Si hay sesión → mostrar inicio
        model.addAttribute("emailUsuario", email)
        return "inicio"
    }

    @GetMapping("/persona")
    fun persona(session: HttpSession): String {

        val email = session.getAttribute("emailUsuario") as String?

        // 🔐 proteger también esta vista
        if (email == null) {
            return "redirect:/signup"
        }

        return "persona"
    }

    @GetMapping("/persona/agregar")
    fun agregarPersona(session: HttpSession): String {

        val email = session.getAttribute("emailUsuario") as String?

        if (email == null) {
            return "redirect:/signup"
        }

        return "agregar-persona"
    }

    @GetMapping("/test")
    @ResponseBody
    fun test(): String {
        return "FUNCIONA"
    }
}
