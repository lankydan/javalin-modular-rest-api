package dev.lankydan.people.web

import java.util.UUID

data class Person(val id: UUID? = null, val firstName: String, val lastName: String)