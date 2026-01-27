package com.example.pruebita1.controller

import com.example.pruebita1.model.User
import com.example.pruebita1.repository.UserRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping

@Controller
class UserController(
    private val userRepository: UserRepository
) {

    // 📄 PANTALLA CON BOTÓN
    @GetMapping("/hardcode")
    fun pantallaBoton(): String {
        return "hardcode"
    }

    // 🔘 BOTÓN PRESIONADO
    @PostMapping("/hardcode")
    fun insertarUsuarios(model: Model): String {

        val usuarios = listOf(
            User(14, "nana", "nana@email.com", "1234"),
            User(15, "uis", "iiu@email.com", "abcd"),
            User(16, "sikekeke", "keke@email.com", "pass")
        )

        val guardados = mutableListOf<User>()

        usuarios.forEach { user ->
            if (!userRepository.existsById(user.id)) {
                guardados.add(userRepository.save(user))
            }
        }

        model.addAttribute("usuarios", guardados)

        return "resultado"
    }
}
