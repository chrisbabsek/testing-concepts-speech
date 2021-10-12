package de.babsek.demo.testingconcepts.part2mocks

import de.babsek.demo.helloservice.HelloService
import de.babsek.demo.helloservice.NotInTheRightMoodException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class SimpleMocks {

    @Test
    fun `use simple mock`() {
        val person = mockk<Person> {
            every { firstName } returns "Max"
        }

        person.firstName shouldBe "Max"

        shouldThrowAny { person.lastName }
    }

    @Test
    fun `use mock with andThen`() {
        val person = mockk<Person> {
            every { age } returns 17 andThen 18
        }

        person.age shouldBe 17
        person.age shouldBe 18
    }

    @Test
    fun `let mock throw exception`() {
        val helloServiceMock = mockk<HelloService> {
            every { sayHello() } throws NotInTheRightMoodException()
        }

        shouldThrow<NotInTheRightMoodException> {
            helloServiceMock.sayHello()
        }
    }

    data class Person(val firstName: String, val lastName: String, val age: Int)
}
