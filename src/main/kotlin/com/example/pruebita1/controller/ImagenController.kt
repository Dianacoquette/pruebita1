package com.example.pruebita1.controller

import com.example.pruebita1.model.Imagen
import com.example.pruebita1.repository.ImagenRepository
import com.example.pruebita1.service.CloudinaryService
import jakarta.servlet.http.HttpSession
import org.hibernate.Session
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@Controller
class ImagenController(
    private val cloudinaryService: CloudinaryService,
    private val imagenRepository: ImagenRepository
) {

    @GetMapping("/subir-imagen")
    fun mostrarFormulario(session: HttpSession): String {
        val email = session.getAttribute("emailUsuario") as String?

        if (email == null) {
            return "redirect:/signup"
        }
        return "subir-imagen"
    }

    @PostMapping("/subir-imagen")
    fun subirImagen(
        @RequestParam("file") file: MultipartFile,
        model: Model
    ): String {

        if (file.isEmpty) {
            model.addAttribute("mensaje", "No seleccionaste ninguna imagen 😢")
            return "subir-imagen"
        }

        return try {

            // 1️⃣ Subir imagen a Cloudinary
            val imageUrl = cloudinaryService.uploadFile(file)

            // 2️⃣ Guardar URL en base de datos
            val imagen = Imagen(
                nombre = imageUrl
            )

            imagenRepository.save(imagen)

            model.addAttribute("mensaje", "Imagen subida con éxito")

            "subir-imagen"

        } catch (e: Exception) {

            model.addAttribute(
                "mensaje",
                e.message ?: "Ocurrió un error al subir la imagen"
            )

            "subir-imagen"
        }
    }
}