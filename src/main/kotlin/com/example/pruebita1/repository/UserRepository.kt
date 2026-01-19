package com.example.pruebita1.repository

import com.example.pruebita1.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int>
