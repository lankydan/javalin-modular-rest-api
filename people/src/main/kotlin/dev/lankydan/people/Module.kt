package dev.lankydan.people

import dev.lankydan.people.data.PersonRepository
import dev.lankydan.people.web.PersonController
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

object People {
    val module = DI.Module("People module") {
        bindSingleton { PersonRepository() }
        bindSingleton { PersonController(instance()) }
    }
}