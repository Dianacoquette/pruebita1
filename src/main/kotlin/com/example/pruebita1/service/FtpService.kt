package com.example.pruebita1.service

import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream

@Service
class FtpService {
    private val server = "FTPSERVER.somee.com"
    private val user = "dianalaobi"
    private val password = "Dididada127"
    private val remoteDir = "/www.FTPSERVER.somee.com/imagenes"
    private val port = 21

    fun uploadFile(file: MultipartFile): String {
        val ftpClient = FTPClient()

        return try {
            // 1️⃣ Conectarse
            ftpClient.connect(server)
            ftpClient.login(user, password)

            // 2️⃣ Configuración obligatoria
            ftpClient.enterLocalPassiveMode()
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE)

            // 3️⃣ Ir a la carpeta correcta
            val dirExists = ftpClient.changeWorkingDirectory(remoteDir)

            if (!dirExists) {
                return "La carpeta /imagenes no existe o no tienes permisos"
            }

            // 4️⃣ Subir archivo
            val fileName = file.originalFilename ?: "imagen.png"
            val uploaded = ftpClient.storeFile(fileName, file.inputStream)

            if (uploaded) {
                "Imagen subida correctamente"
            } else {
                "Error al subir imagen (permisos o nombre)"
            }

        } catch (e: Exception) {
            "Error FTP: ${e.message}"
        } finally {
            if (ftpClient.isConnected) {
                ftpClient.logout()
                ftpClient.disconnect()
            }
        }
    }

}