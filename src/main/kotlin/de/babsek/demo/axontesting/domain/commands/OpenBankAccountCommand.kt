package de.babsek.demo.axontesting.domain.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class OpenBankAccountCommand(
    @TargetAggregateIdentifier
    val bankAccountId: String,
    val ownerName: String
)
