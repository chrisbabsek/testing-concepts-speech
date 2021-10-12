package de.babsek.demo.testingconcepts.domain.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class AcceptMoneyTransferCommand(
    @TargetAggregateIdentifier
    val bankAccountId: String,
    val amount: Double,
    val reason: String
)
