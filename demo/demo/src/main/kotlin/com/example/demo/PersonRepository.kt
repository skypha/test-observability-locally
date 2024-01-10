package com.example.demo

import org.springframework.data.repository.reactive.ReactiveCrudRepository

public interface PersonRepository : ReactiveCrudRepository<Person, Long> {

}