package de.babsek.demo.axontesting.domain.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class InformFailedMoneyTransferCommand(
    @TargetAggregateIdentifier
    val bankAccountId: String,
    val targetBankAccountId: String,
    val amount: Double,
    val errorMessage: String?
)
