package de.babsek.demo.testingconcepts.part2mocks

import de.babsek.demo.helloservice.HelloService
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.spyk
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class Spyk {
    val helloService = HelloService()

    lateinit var helloServiceSpy: HelloService

    @BeforeEach
    fun `init spies`() {
        helloServiceSpy = spyk(helloService)
    }

    @Test
    fun `verify method called`() {
        helloServiceSpy.sayHello() shouldBe "Hello"

        verifySequence {
            helloServiceSpy.sayHello()
        }
    }

    @Test
    fun `change behavior in spy`() {
        every { helloServiceSpy.sayHello() } returns "Hi"

        helloServiceSpy.sayHello() shouldBe "Hi"
    }
}
