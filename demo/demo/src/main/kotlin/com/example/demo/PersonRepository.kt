package com.example.demo

import io.micrometer.observation.annotation.Observed
import org.springframework.data.repository.reactive.ReactiveCrudRepository

@Observed(name = "PersonRepository", contextualName = "PersonRepository")
interface PersonRepository : ReactiveCrudRepository<Person, Long> {

}