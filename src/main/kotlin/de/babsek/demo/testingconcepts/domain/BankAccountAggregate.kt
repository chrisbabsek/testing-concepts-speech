package de.babsek.demo.testingconcepts.domain

import de.babsek.demo.testingconcepts.domain.commands.AcceptMoneyTransferCommand
import de.babsek.demo.testingconcepts.domain.commands.InformFailedMoneyTransferCommand
import de.babsek.demo.testingconcepts.domain.commands.OpenBankAccountCommand
import de.babsek.demo.testingconcepts.domain.commands.TransferMoneyCommand
import de.babsek.demo.testingconcepts.domain.events.BankAccountOpenedEvent
import de.babsek.demo.testingconcepts.domain.events.MoneyTransferArrivedEvent
import de.babsek.demo.testingconcepts.domain.events.MoneyTransferFailedEvent
import de.babsek.demo.testingconcepts.domain.events.MoneyTransferRequestedEvent
import de.babsek.demo.testingconcepts.domain.exceptions.NotEnoughMoneyException
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateCreationPolicy
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.modelling.command.CreationPolicy
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class BankAccountAggregate {
    @AggregateIdentifier
    lateinit var bankAccountId: String
    lateinit var ownerName: String

    var balance: Double = 0.0

    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    @CommandHandler
    fun openBankAccount(command: OpenBankAccountCommand): String {
        if (!this::bankAccountId.isInitialized) {
            AggregateLifecycle.apply(
                BankAccountOpenedEvent(
                    bankAccountId = command.bankAccountId,
                    ownerName = command.ownerName,
                    initialBalance = balance
                )
            )
        }
        return command.bankAccountId
    }

    @EventSourcingHandler
    fun on(event: BankAccountOpenedEvent) {
        bankAccountId = event.bankAccountId
        ownerName = event.ownerName
        balance = event.initialBalance
    }

    @CommandHandler
    fun handleTransferMoney(command: TransferMoneyCommand) {
        if (balance < command.amount) {
            throw NotEnoughMoneyException(
                bankAccountId = command.bankAccountId,
                requestedAmount = command.amount,
                currentBalance = balance
            )
        } else {
            AggregateLifecycle.apply(
                MoneyTransferRequestedEvent(
                    originBankAccountId = command.bankAccountId,
                    targetBankAccountId = command.targetBankAccount,
                    amount = command.amount,
                    reason = command.reason
                )
            )
        }
    }

    @EventSourcingHandler
    fun on(event: MoneyTransferRequestedEvent) {
        balance -= event.amount
    }

    @CommandHandler
    fun acceptMoneyTransfer(command: AcceptMoneyTransferCommand) {
        AggregateLifecycle.apply(
            MoneyTransferArrivedEvent(
                bankAccountId = command.bankAccountId,
                amount = command.amount,
                reason = command.reason
            )
        )
    }

    @EventSourcingHandler
    fun on(event: MoneyTransferArrivedEvent) {
        balance += event.amount
    }

    @CommandHandler
    fun handleFailedMoneyTransfer(command: InformFailedMoneyTransferCommand) {
        AggregateLifecycle.apply(
            MoneyTransferFailedEvent(
                bankAccountId = command.bankAccountId,
                targetBankAccountId = command.targetBankAccountId,
                amount = command.amount,
                errorMessage = null
            )
        )
    }

    @EventSourcingHandler
    fun on(event: MoneyTransferFailedEvent) {
        balance += event.amount
    }
}
