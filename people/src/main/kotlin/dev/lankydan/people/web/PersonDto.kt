package dev.lankydan.people.web

import java.util.UUID

data class PersonDto(val id: UUID? = null, val firstName: String, val lastName: String)