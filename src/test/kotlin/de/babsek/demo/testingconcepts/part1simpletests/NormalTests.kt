package de.babsek.demo.testingconcepts.part1simpletests

import io.kotest.assertions.json.shouldContainJsonKeyValue
import io.kotest.assertions.json.shouldEqualJson
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.*
import io.kotest.matchers.date.shouldBeAfter
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class NormalTests {
    var x = 5

    @BeforeEach
    fun `init tests`() {
        x = 7
    }

    @Test
    fun `simple test`() {
        x shouldBe 7
    }

    @Test
    fun `numbers are greater or smaller`() {
        7 shouldBeGreaterThan 5
    }

    @Test
    fun `true or false`() {
        (7 > 5) shouldBe true
    }

    @Test
    fun `list contains and does not contain element`() {
        (listOf(1, 2) - listOf(1, 2, 3)) should beEmpty()
        (listOf(1, 2) - listOf(2, 3)) shouldHaveSize 1

        listOf(1, 2, 3, 4, 5) shouldContain 1
        listOf(1, 2, 3, 4, 5) shouldContainInOrder listOf(2, 3)
        listOf(1, 2, 3, 4, 5) shouldNotContain 7
    }

    @Test
    fun `something throws`() {
        shouldThrow<IllegalStateException> {
            throw IllegalStateException()
        }
    }

    @Test
    fun `date`() {
        val today = LocalDate.parse("2021-10-14")

        today shouldBeAfter today.minusDays(1)
    }

    @Test
    fun `json`() {
        """{"a": 7}""" shouldEqualJson """{ "a" :7}"""
        testJson
            .shouldContainJsonKeyValue("$.b.field2[1]", 2)
    }
}

@Language("json")
val testJson = """
{
"a": "abcdef",
"b": {
  "field1": "34",
  "field2": [1,2,3]
  }
}
""".trimIndent()
