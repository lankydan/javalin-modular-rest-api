package dev.lankydan.people.data

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import java.util.UUID

object People : UUIDTable() {
    val firstName: Column<String> = varchar("first_name", 256)
    val lastName: Column<String> = varchar("last_name", 256)
}

class PersonEntity(id: EntityID<UUID>): UUIDEntity(id) {
    companion object : UUIDEntityClass<PersonEntity>(People)

    var firstName: String by People.firstName
    var lastName: String by People.lastName
}