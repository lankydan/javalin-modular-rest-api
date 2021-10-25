package dev.lankydan.people.web

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

// should i hook this up to DI?
// currently everything here is static, however I need a reference to the people repository to get data
// could use kodein for DI
// since the example is so simple, I could skip DI altogether, but then I don't think the example translates across well to other places.
// they can stil be lambda's defined as they are, but they need to exist within a class

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
        val people = personRepository.findAll().map { PersonDto(it.id.value, it.firstName, it.lastName) }
        ctx.json(people)
    }

    private fun get(ctx: Context) {
        val id = UUID.fromString(ctx.pathParam("id"))
        val person = personRepository.find(id) ?: throw NotFoundResponse()
        ctx.json(person)
    }

    private fun post(ctx: Context) {
        ctx.json(personRepository.persist(ctx.bodyAsClass()))
    }

    private fun put(ctx: Context) {
        val id = UUID.fromString(ctx.pathParam("id"))
        val updated = personRepository.update(id, ctx.bodyAsClass()) ?: throw NotFoundResponse()
        ctx.json(updated)
    }

    private fun delete(ctx: Context) {
        personRepository.delete(UUID.fromString(ctx.pathParam("id")))
    }
}