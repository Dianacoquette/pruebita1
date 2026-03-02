package com.example.pruebita1.controller

import com.example.pruebita1.repository.ImagenRepository
import jakarta.servlet.http.HttpSession
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class PageController(
    private val imagenRepository: ImagenRepository
) {

    // 🔒 RAÍZ: SIEMPRE AL SIGNUP
    @GetMapping("/")
    fun root(): String {
        return "redirect:/login"
    }


    // 🔐 INICIO PROTEGIDO
    @GetMapping("/inicio")
    fun inicio(model: Model, session: HttpSession): String {

        val email = session.getAttribute("emailUsuario") as String?

        //Si no hay sesión → signup
        if (email == null) {
            return "redirect:/login"
        }


        //Ahora la BD ya tiene la URL completa de Cloudinary
        val imagenesCarrusel = imagenRepository.findAll().map {
            it.nombre   // ← YA ES URL COMPLETA
        }


        // 📦 Mandar datos a la vista
        model.addAttribute("emailUsuario", email)
        model.addAttribute("imagenesCarrusel", imagenesCarrusel)

        return "inicio"
    }

    @GetMapping("/persona")
    fun persona(session: HttpSession): String {

        val email = session.getAttribute("emailUsuario") as String?

        // 🔐 proteger también esta vista
        if (email == null) {
            return "redirect:/login"
        }

        return "persona"
    }

    @GetMapping("/persona/agregar")
    fun agregarPersona(session: HttpSession): String {

        val email = session.getAttribute("emailUsuario") as String?

        if (email == null) {
            return "redirect:/login"
        }

        return "agregar-persona"
    }

    @GetMapping("/test")
    @ResponseBody
    fun test(): String {
        return "FUNCIONA"
    }
}
