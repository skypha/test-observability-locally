package com.example.demo

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.ErrorResponse
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono


@Component
@Order(-2)
class RouteExceptionHandler(
        errorAttributes: ErrorAttributes?,
        webProperties: WebProperties,
        applicationContext: ApplicationContext?,
        serverCodecConfigurer: ServerCodecConfigurer
): AbstractErrorWebExceptionHandler(errorAttributes, webProperties.resources, applicationContext) {
    init {
        super.setMessageWriters(serverCodecConfigurer.writers)
        super.setMessageReaders(serverCodecConfigurer.readers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse?> {
        return RouterFunctions.route(RequestPredicates.all()) { request: ServerRequest -> this.renderErrorResponse(request) }
    }

    private fun renderErrorResponse(request: ServerRequest): Mono<ServerResponse?> {
        val error = getError(request)
        val httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        return ServerResponse
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(
                        ErrorResponse
                                .builder(error, httpStatus, "An error occurred")
                                .build()
                    )
                )
    }
}
