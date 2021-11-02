package dev.lankydan.people.web

import dev.lankydan.people.data.PersonEntity
import dev.lankydan.people.data.PersonRepository
import dev.lankydan.web.Controller
import io.javalin.apibuilder.ApiBuilder.delete
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.apibuilder.ApiBuilder.put
import io.javalin.apibuilder.EndpointGroup
import io.javalin.http.Context
import io.javalin.http.NotFoundResponse
import java.util.UUID

class PersonController(private val personRepository: PersonRepository) : Controller {

    override val path = "/people"

    override val endpoints = EndpointGroup {
        get("/", ::all)
        get("/{id}", ::get)
        post("/", ::post)
        put("/{id}", ::put)
        delete("/{id}", ::delete)
    }

    private fun all(ctx: Context) {
        val people = personRepository.findAll().map { entity -> entity.toPerson() }
        ctx.json(people)
    }

    private fun get(ctx: Context) {
        val id = UUID.fromString(ctx.pathParam("id"))
        val person = personRepository.find(id)?.toPerson() ?: throw NotFoundResponse()
        ctx.json(person)
    }

    private fun post(ctx: Context) {
        val person = ctx.bodyAsClass<Person>()
        val persisted = personRepository.persist {
            firstName = person.firstName
            lastName = person.lastName
        }.toPerson()
        ctx.json(persisted)
    }

    private fun put(ctx: Context) {
        val id = UUID.fromString(ctx.pathParam("id"))
        val person = ctx.bodyAsClass<Person>()
        val updated = personRepository.update(id) {
            firstName = person.firstName
            lastName = person.lastName
        }?.toPerson() ?: throw NotFoundResponse()
        ctx.json(updated)
    }

    private fun delete(ctx: Context) {
        personRepository.delete(UUID.fromString(ctx.pathParam("id")))
    }

    private fun PersonEntity.toPerson(): Person {
        return Person(
            id.value,
            firstName,
            lastName
        )
    }
}