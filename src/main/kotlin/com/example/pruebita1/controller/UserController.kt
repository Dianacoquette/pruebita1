package com.example.pruebita1.controller

import com.example.pruebita1.model.User
import com.example.pruebita1.repository.UserRepository
import com.example.pruebita1.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
class UserController(
    private val userRepository: UserRepository,
    private val userService: UserService
) {

    /* ══════════════════════════════════════════
       RUTAS HARDCODE (las que ya tenías)
    ══════════════════════════════════════════ */

    @GetMapping("/hardcode")
    fun pantallaBoton(): String = "hardcode"

    @PostMapping("/hardcode")
    fun insertarUsuarios(model: Model): String {
        val usuarios = listOf(
            User(17, "nana",     "nana@email.com", "1234"),
            User(18, "uis",      "iiu@email.com",  "abcd"),
            User(19, "sikekeke", "keke@email.com",  "pass")
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

    /* ══════════════════════════════════════════
       CRUD DE USUARIOS  →  /usuario
    ══════════════════════════════════════════ */

    // ── LISTA ──
    @GetMapping("/usuario")
    fun lista(
        model: Model,
        @RequestParam(defaultValue = "0")  pagina:   Int,
        @RequestParam(defaultValue = "")   busqueda: String,
        @RequestParam(defaultValue = "")   activo:   String,
        @RequestParam(defaultValue = "")   desde:    String,
        @RequestParam(defaultValue = "")   hasta:    String
    ): String {
        val page = userService.listar(pagina, busqueda, activo, desde, hasta)
        model.addAttribute("usuarios",       page.content)
        model.addAttribute("paginaActual",   pagina)
        model.addAttribute("totalPaginas",   page.totalPages)
        model.addAttribute("totalRegistros", page.totalElements)
        model.addAttribute("busqueda",       busqueda)
        model.addAttribute("activo",         activo)
        model.addAttribute("desde",          desde)
        model.addAttribute("hasta",          hasta)
        return "usuario/lista"
    }

    // ── AGREGAR ──
    @GetMapping("/usuario/agregar")
    fun agregar(model: Model): String {
        model.addAttribute("usuario", User())
        model.addAttribute("modo", "agregar")
        return "usuario/formulario"
    }

    @PostMapping("/usuario/guardar")
    fun guardar(
        @ModelAttribute usuario: User,
        redirectAttrs: RedirectAttributes
    ): String {
        userService.guardar(usuario)
        redirectAttrs.addFlashAttribute("mensaje", "Usuario agregado correctamente ✓")
        return "redirect:/usuario"
    }

    // ── EDITAR ──
    @GetMapping("/usuario/editar/{id}")
    fun editar(@PathVariable id: Int, model: Model): String {
        model.addAttribute("usuario", userService.buscarPorId(id))
        model.addAttribute("modo", "editar")
        return "usuario/formulario"
    }

    @PostMapping("/usuario/actualizar/{id}")
    fun actualizar(
        @PathVariable id: Int,
        @ModelAttribute usuario: User,
        redirectAttrs: RedirectAttributes
    ): String {
        userService.actualizar(id, usuario)
        redirectAttrs.addFlashAttribute("mensaje", "Usuario actualizado correctamente ✓")
        return "redirect:/usuario"
    }

    // ── ELIMINAR ──
    @PostMapping("/usuario/eliminar/{id}")
    fun eliminar(
        @PathVariable id: Int,
        redirectAttrs: RedirectAttributes
    ): String {
        userService.eliminar(id)
        redirectAttrs.addFlashAttribute("mensaje", "Usuario eliminado correctamente ✓")
        return "redirect:/usuario"
    }
}