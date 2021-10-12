package de.babsek.demo.testingconcepts.part2mocks

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import org.junit.jupiter.api.Test

class RelaxedMocks {

    @Test
    fun `use relaxed mock`() {
        val mock = mockk<Service>()

        shouldThrowAny {
            mock.giveAnswerForEveryting()
        }
    }

    @Test
    fun `relaxed unit fun mock`() {
        val mock = mockk<Service>(relaxUnitFun = true)

        shouldNotThrowAny { mock.doSomething() }

        shouldThrowAny { mock.giveAString() }
    }

    @Test
    fun `relaxed mock`() {
        val mock = mockk<Service>(relaxed = true)

        mock.doSomething()
        mock.giveAnswerForEveryting() shouldBe 0
        mock.giveAString() shouldBe ""
    }
}

class Service {

    fun giveAnswerForEveryting(): Int = 42

    fun doSomething() {
    }

    fun giveAString(): String = "a string"
}
