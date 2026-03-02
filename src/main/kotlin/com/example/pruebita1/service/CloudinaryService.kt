package com.example.pruebita1.service

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class CloudinaryService {

    private val cloudinary = Cloudinary(
        ObjectUtils.asMap(
            "cloud_name", "dxfdl8pg0",
            "api_key", "565631485166895",
            "api_secret", "xpz5kup07lDpMsD2sLYl1PvdpVs"
        )
    )

    fun uploadFile(file: MultipartFile): String {

        val originalName = file.originalFilename ?: "imagen"

        val uploadResult = cloudinary.uploader().upload(
            file.bytes,
            ObjectUtils.asMap(
                "folder", "contenidos/imagenes",
                "public_id", System.currentTimeMillis().toString() + "_" + originalName,
                "overwrite", true
            )
        )

        return uploadResult["secure_url"].toString()
    }
}