package dev.lankydan.application

import dev.lankydan.people.People
import dev.lankydan.web.Controller
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.path
import org.jetbrains.exposed.sql.Database
import org.kodein.di.DI
import org.kodein.di.allInstances
import java.util.Properties

class Application

fun main() {

    connectToDatabase()

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

private fun connectToDatabase() {
    Properties().run {
        load(Application::class.java.classLoader.getResourceAsStream("application.properties"))
        Database.connect(
            url = getProperty("database.url"),
            driver = getProperty("database.driver"),
            user = getProperty("database.user"),
            password = getProperty("database.password")
        )
    }
}