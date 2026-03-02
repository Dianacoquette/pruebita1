package com.example.pruebita1.repository

import com.example.pruebita1.model.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface UserRepository : JpaRepository<User, Int> {

    @Query(
        """SELECT u FROM User u WHERE
           (:q IS NULL OR :q = '' OR
               LOWER(u.username)       LIKE LOWER(CONCAT('%', :q, '%')) OR
               LOWER(u.email)          LIKE LOWER(CONCAT('%', :q, '%')) OR
               LOWER(u.nombreCompleto) LIKE LOWER(CONCAT('%', :q, '%')) OR
               LOWER(u.telefono)       LIKE LOWER(CONCAT('%', :q, '%')))
           AND (:activo IS NULL OR u.activo = :activo)
           AND (:desde  IS NULL OR u.fechaCreacion >= :desde)
           AND (:hasta  IS NULL OR u.fechaCreacion <= :hasta)"""
    )
    fun filtrar(
        @Param("q")      q:      String?,
        @Param("activo") activo: Boolean?,
        @Param("desde")  desde:  LocalDateTime?,
        @Param("hasta")  hasta:  LocalDateTime?,
        pageable: Pageable
    ): Page<User>
}