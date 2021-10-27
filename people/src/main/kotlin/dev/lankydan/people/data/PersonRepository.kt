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

    fun persist(person: PersonEntity.() -> Unit): PersonEntity {
        return transaction {
            PersonEntity.new(person)
        }
    }

    fun update(id: UUID, person: PersonEntity.() -> Unit): PersonEntity? {
        return transaction {
            PersonEntity.findById(id)?.also { entity ->
                person(entity)
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