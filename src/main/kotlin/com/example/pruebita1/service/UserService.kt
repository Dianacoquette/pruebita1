package com.example.pruebita1.service

import com.example.pruebita1.model.User
import com.example.pruebita1.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.random.Random

@Service
class UserService(private val repo: UserRepository) {

    companion object {
        const val PAGE_SIZE = 5
    }

    private fun generarId(): Int {
        var id: Int
        do { id = Random.nextInt(100_000, 999_999) } while (repo.existsById(id))
        return id
    }

    fun listar(
        pagina:   Int,
        busqueda: String,
        activo:   String,   // "true", "false" o "" (todos)
        desde:    String,   // "yyyy-MM-dd" o ""
        hasta:    String    // "yyyy-MM-dd" o ""
    ): Page<User> {
        val pageable = PageRequest.of(pagina, PAGE_SIZE, Sort.by("fechaCreacion").descending())

        val activoFiltro: Boolean? = when (activo) {
            "true"  -> true
            "false" -> false
            else    -> null
        }

        val desdeFiltro: LocalDateTime? = desde.takeIf { it.isNotBlank() }
            ?.let { LocalDate.parse(it).atStartOfDay() }

        val hastaFiltro: LocalDateTime? = hasta.takeIf { it.isNotBlank() }
            ?.let { LocalDate.parse(it).atTime(23, 59, 59) }

        return repo.filtrar(
            q      = busqueda.trimOrNull(),
            activo = activoFiltro,
            desde  = desdeFiltro,
            hasta  = hastaFiltro,
            pageable = pageable
        )
    }

    private fun String.trimOrNull(): String? = trim().ifBlank { null }

    fun buscarPorId(id: Int): User =
        repo.findById(id).orElseThrow { NoSuchElementException("Usuario $id no encontrado") }

    fun guardar(user: User): User =
        repo.save(user.copy(id = generarId(), fechaCreacion = LocalDateTime.now()))

    fun actualizar(id: Int, datos: User): User {
        val existente = buscarPorId(id)
        return repo.save(existente.copy(
            username       = datos.username,
            email          = datos.email,
            nombreCompleto = datos.nombreCompleto,
            telefono       = datos.telefono,
            activo         = datos.activo,
            passwd         = if (datos.passwd.isNotBlank()) datos.passwd else existente.passwd
        ))
    }

    fun eliminar(id: Int) = repo.deleteById(id)
}