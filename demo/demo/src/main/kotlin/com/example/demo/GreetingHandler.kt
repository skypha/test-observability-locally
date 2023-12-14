package com.example.demo

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse

data class Greeting(val content: String)

@Component
public class GreetingHandler {

  public fun hello(request: ServerRequest) =
      ServerResponse.ok()
          .contentType(MediaType.APPLICATION_JSON)
          .body(BodyInserters.fromValue(Greeting("Hello, Spring!")))
}
