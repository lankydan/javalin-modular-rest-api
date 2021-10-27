package dev.lankydan.application

import dev.lankydan.people.People
import dev.lankydan.web.Controller
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import org.jetbrains.exposed.sql.Database
import org.kodein.di.DI
import org.kodein.di.allInstances

fun main() {

    Database.connect(
        url = "jdbc:postgresql://localhost:5432/postgres",
        driver = "org.postgresql.Driver",
        user = "admin",
        password = "admin"
    )

    val di = DI {
        import(People.module)
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