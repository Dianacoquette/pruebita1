package com.example.pruebita1.repository

import com.example.pruebita1.model.Imagen
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImagenRepository : JpaRepository<Imagen, Int>