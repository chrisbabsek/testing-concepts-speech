package de.babsek.demo.testingconcepts.domain.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class TransferMoneyCommand(
    @TargetAggregateIdentifier
    val bankAccountId: String,
    val targetBankAccount: String,
    val amount: Double,
    val reason: String
)
