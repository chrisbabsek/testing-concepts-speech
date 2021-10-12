package de.babsek.demo.testingconcepts.part4apitests

import com.ninjasquad.springmockk.MockkBean
import de.babsek.demo.testingconcepts.domain.commands.AcceptMoneyTransferCommand
import de.babsek.demo.testingconcepts.projection.BankAccountProjectionEntity
import de.babsek.demo.testingconcepts.projection.BankAccountProjectionRepository
import io.mockk.every
import io.mockk.verifySequence
import org.axonframework.commandhandling.gateway.CommandGateway
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest
class SpringApiTest @Autowired constructor(private val mockMvc: MockMvc) {

    @MockkBean(relaxed = true)
    lateinit var commandGateway: CommandGateway

    @MockkBean
    lateinit var bankAccountProjectionRepository: BankAccountProjectionRepository

    val bankAccount1 = BankAccountProjectionEntity(
        bankAccountId = "1",
        balance = 1_000_000.0
    )

    @Language("json")
    val bankAccount1Json = """{"id":null,"bankAccountId":"1","balance":1000000.0}"""

    @Test
    fun `findAll`() {
        every { bankAccountProjectionRepository.findAll() } returns listOf(
            bankAccount1
        )

        mockMvc.get("/bankaccounts") {
            accept = APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content {
                json("[$bankAccount1Json]")
            }
        }
    }

    @Test
    fun `find bank account by id`() {
        every { bankAccountProjectionRepository.findByBankAccountId("1") } returns bankAccount1

        mockMvc.get("/bankaccounts/1") {
            accept = APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content {
                json(bankAccount1Json)
            }
        }
    }

    @Test
    fun `return 404 on non existing bank account`() {
        every { bankAccountProjectionRepository.findByBankAccountId("42") } returns null

        mockMvc.get("/bankaccounts/42") {
            accept = APPLICATION_JSON
        }.andExpect {
            status { isNotFound() }
        }
    }

    @Test
    fun `pay in money`() {
        val expectedCommand = AcceptMoneyTransferCommand(
            bankAccountId = "1",
            amount = 500.0,
            reason = "pay in"
        )

        mockMvc.post("/bankaccounts/1/payings") {
            contentType = APPLICATION_JSON
            content = """
                {"amount": 500}
            """.trimIndent()
        }.andExpect {
            status { isAccepted() }
        }

        verifySequence {
            commandGateway.sendAndWait(expectedCommand)
        }
    }
}
