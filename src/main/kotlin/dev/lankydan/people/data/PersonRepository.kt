package dev.lankydan.people.data

import dev.lankydan.people.web.PersonDto
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class PersonRepository {

    fun findAll(): List<PersonEntity> {
        return transaction {
            PersonEntity.all().toList()
        }
    }

    fun find(id: UUID): PersonEntity? {
        return transaction {
            PersonEntity.findById(id)
        }
    }

    fun persist(person: PersonDto): PersonDto {
        return transaction {
            PersonEntity.new {
                firstName = person.firstName
                lastName = person.lastName
            }
        }.let { PersonDto(it.id.value, it.firstName, it.lastName) }
    }

    fun update(id: UUID, person: PersonDto): PersonEntity? {
        return transaction {
            PersonEntity.findById(id)?.also { entity ->
                entity.firstName = person.firstName
                entity.lastName = person.lastName
            }
        }
    }

    fun delete(id: UUID): Boolean {
        return transaction {
            PersonEntity.findById(id)?.let { entity ->
                entity.delete()
                true
            } ?: false
        }
    }
}