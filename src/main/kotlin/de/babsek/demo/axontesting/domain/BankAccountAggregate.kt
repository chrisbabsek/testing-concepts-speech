package de.babsek.demo.axontesting.domain

import de.babsek.demo.axontesting.domain.commands.OpenBankAccountCommand
import de.babsek.demo.axontesting.domain.events.BankAccountOpenedEvent
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateCreationPolicy
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.modelling.command.CreationPolicy
import org.axonframework.spring.stereotype.Aggregate

@Aggregate
class BankAccountAggregate() {
    @AggregateIdentifier
    lateinit var bankAccountId: String
    lateinit var ownerName: String

    var balance: Double = 0.0

    @CreationPolicy(AggregateCreationPolicy.CREATE_IF_MISSING)
    @CommandHandler
    fun openBankAccount(command: OpenBankAccountCommand) {
        AggregateLifecycle.apply(
            BankAccountOpenedEvent(
                bankAccountId = command.bankAccountId,
                ownerName = command.ownerName,
                initialBalance = balance
            )
        )
    }

    @EventSourcingHandler
    fun on(event: BankAccountOpenedEvent) {
        bankAccountId = event.bankAccountId
        ownerName = event.ownerName
        balance = event.initialBalance
    }
}