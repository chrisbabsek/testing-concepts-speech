package de.babsek.demo.axontesting.domain.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class TransferMoneyCommand(
    @TargetAggregateIdentifier
    val bankAccountId: String,
    val destinationBankAccount: String,
    val amount: Double,
    val reason: String
)
