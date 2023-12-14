package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication class TestObservabilityApplication

fun main(args: Array<String>) {
    runApplication<TestObservabilityApplication>(*args)
}
