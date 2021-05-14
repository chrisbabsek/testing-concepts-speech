package de.babsek.demo.axontesting.domain.events

data class BankAccountOpenedEvent(
    val bankAccountId: String,
    val ownerName: String,
    val initialBalance: Double
)
