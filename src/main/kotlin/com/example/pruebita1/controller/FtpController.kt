package com.example.pruebita1.controller

import com.example.pruebita1.service.FtpService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

@Controller
class FtpController(private val ftpService: FtpService) {
    @GetMapping("/subir-imagen")
    fun form(): String {
        return "subir-imagen"
    }

    @PostMapping("/subir-imagen")
    fun upload(
        @RequestParam("file") file: MultipartFile,
        model: Model
    ): String {

        if (file.isEmpty) {
            model.addAttribute("mensaje", "No seleccionaste ninguna imagen")
            return "subir-imagen"
        }

        val resultado = ftpService.uploadFile(file)
        model.addAttribute("mensaje", resultado)

        return "subir-imagen"
    }
}