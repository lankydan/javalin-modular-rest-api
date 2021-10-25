package dev.lankydan.people.application

import dev.lankydan.people.data.PersonRepository
import dev.lankydan.people.web.PersonController
import dev.lankydan.web.Controller
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import org.jetbrains.exposed.sql.Database
import org.kodein.di.DI
import org.kodein.di.allInstances
import org.kodein.di.bindSingleton
import org.kodein.di.instance

fun main() {

    Database.connect(
        url = "jdbc:postgresql://localhost:5432/postgres",
        driver = "org.postgresql.Driver",
        user = "admin",
        password = "admin"
    )

    val di = DI {
        bindSingleton { PersonRepository() }
        bindSingleton { PersonController(instance()) }
    }
    Javalin.create { config ->
        // Turned on as it's an example and makes it easier to see what's going on
        config.enableDevLogging()
    }.apply {
        routes {
            val controllers: List<Controller> by di.allInstances()
            controllers.forEach { path(it.path, it.endpoints) }
        }
    }.start(8080)
}