package de.babsek.demo.axontesting.domain.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class AcceptMoneyTransferCommand(
    @TargetAggregateIdentifier
    val bankAccountId: String,
    val amount: Double,
    val reason: String
)
