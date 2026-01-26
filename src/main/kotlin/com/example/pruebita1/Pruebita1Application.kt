package com.example.pruebita1

import com.example.pruebita1.model.User
import com.example.pruebita1.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class Pruebita1Application {


	/*
	@Bean
	fun init(userRepository: UserRepository) = CommandLineRunner {

		val user = User(
			id = 10,
			username = "sdsd",
			email = "asdd@email.com",
			passwd = "4423"
		)

		userRepository.save(user)

		println("Usuario insertado en la DB")
	}

	 */
}

fun main(args: Array<String>) {
	runApplication<Pruebita1Application>(*args)
}
