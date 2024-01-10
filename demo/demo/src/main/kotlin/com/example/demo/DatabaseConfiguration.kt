package com.example.demo

import io.r2dbc.spi.ConnectionFactories
import io.r2dbc.spi.ConnectionFactory
import io.r2dbc.spi.ConnectionFactoryOptions
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EnableR2dbcRepositories
class R2DBCConfiguration : AbstractR2dbcConfiguration() {

    @PostConstruct
    fun setupDatabase() {
        val template = R2dbcEntityTemplate(connectionFactory())

        template.getDatabaseClient().sql("CREATE TABLE if not exists person" +
                "(id VARCHAR(255) PRIMARY KEY," +
                "name VARCHAR(255)," +
                "age INT)")
                .fetch()
                .rowsUpdated().block()
    }

    override fun connectionFactory(): ConnectionFactory =
            ConnectionFactories.get(ConnectionFactoryOptions.builder()
                    .option(ConnectionFactoryOptions.DRIVER, "postgresql")
                    .option(ConnectionFactoryOptions.HOST, "localhost")
                    .option(ConnectionFactoryOptions.DATABASE, "example")
                    .option(ConnectionFactoryOptions.USER, "example")
                    .option(ConnectionFactoryOptions.PASSWORD, "example")
                    .option(ConnectionFactoryOptions.PORT, 5432)
                    .build()
            )
}