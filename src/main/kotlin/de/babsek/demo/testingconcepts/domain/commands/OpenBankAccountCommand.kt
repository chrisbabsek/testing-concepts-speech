package de.babsek.demo.testingconcepts.domain.commands

import org.axonframework.modelling.command.TargetAggregateIdentifier

data class OpenBankAccountCommand(
    @TargetAggregateIdentifier
    val bankAccountId: String,
    val ownerName: String
)
