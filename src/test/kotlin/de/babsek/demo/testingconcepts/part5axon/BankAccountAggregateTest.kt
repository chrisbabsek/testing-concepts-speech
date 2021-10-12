package de.babsek.demo.testingconcepts.part5axon

import de.babsek.demo.testingconcepts.domain.BankAccountAggregate
import de.babsek.demo.testingconcepts.domain.commands.OpenBankAccountCommand
import de.babsek.demo.testingconcepts.domain.commands.TransferMoneyCommand
import de.babsek.demo.testingconcepts.domain.events.BankAccountOpenedEvent
import de.babsek.demo.testingconcepts.domain.events.MoneyTransferArrivedEvent
import de.babsek.demo.testingconcepts.domain.events.MoneyTransferRequestedEvent
import de.babsek.demo.testingconcepts.domain.exceptions.NotEnoughMoneyException
import org.axonframework.test.aggregate.AggregateTestFixture
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class BankAccountAggregateTest {

    val fixture by lazy { AggregateTestFixture(BankAccountAggregate::class.java) }

    val openBankAccountCommand = OpenBankAccountCommand(
        bankAccountId = "1", ownerName = "Christian Babsek"
    )

    val bankAccount1OpenedEvent = BankAccountOpenedEvent(
        bankAccountId = "1", ownerName = "Christian Babsek", initialBalance = 0.0
    )

    val bankAccount2OpenedEvent = BankAccountOpenedEvent(
        bankAccountId = "2", ownerName = "Ted Tester", initialBalance = 0.0
    )

    val moneyTransferArrivedEvent = MoneyTransferArrivedEvent(
        bankAccountId = "1", amount = 500.0, reason = "pay in"
    )

    val transferMoneyCommand = TransferMoneyCommand(
        bankAccountId = "1", targetBankAccount = "2",
        amount = 250.0, reason = "restaurant"
    )

    val moneyTransferRequestedEvent = MoneyTransferRequestedEvent(
        originBankAccountId = "1", targetBankAccountId = "2",
        amount = 250.0, reason = "restaurant"
    )

    @Test
    fun `create new bank account`() {
        fixture
            .`when`(openBankAccountCommand)
            .expectSuccessfulHandlerExecution()
            .expectEvents(bankAccount1OpenedEvent)
    }

    @Test
    fun `do nothing if bank account with same id is already existing`() {
        fixture
            .given(bankAccount1OpenedEvent)
            .`when`(openBankAccountCommand)
            .expectSuccessfulHandlerExecution()
            .expectNoEvents()
    }

    @DisplayName("Transfer Money")
    @Nested
    inner class TransferMoney {

        @Test
        fun `successfully transfer money`() {
            fixture
                .given(bankAccount1OpenedEvent)
                .andGiven(moneyTransferArrivedEvent)
                .`when`(transferMoneyCommand)
                .expectSuccessfulHandlerExecution()
                .expectEvents(moneyTransferRequestedEvent)
        }

        @Test
        fun `fail to transfer money if balance is too low`() {
            fixture
                .given(bankAccount1OpenedEvent)
                .`when`(transferMoneyCommand)
                .expectException(NotEnoughMoneyException::class.java)
        }
    }
}
