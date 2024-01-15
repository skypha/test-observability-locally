package com.example.demo

import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClientResponseException.InternalServerError
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.buildAndAwait
import kotlin.random.Random

data class Greeting(val content: String)

@Component
public class GreetingHandler(
        val personRepository: PersonRepository
) {

    fun hello(request: ServerRequest) =
            Random.nextInt(2).let {
                when (it) {
                    0 -> this.personRepository.findById(18).let {
                        it.flatMap { count ->
                            ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(BodyInserters.fromValue(Greeting("Hello, Spring! We have a Person count of $count")))
                        }
                    }
                    else -> this.personRepository.count().let {
                        it.flatMap { count ->
                            ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(BodyInserters.fromValue(Greeting("Hello, Spring! We have a Person count of $count")))
                        }
                    }
                }
            }
}
