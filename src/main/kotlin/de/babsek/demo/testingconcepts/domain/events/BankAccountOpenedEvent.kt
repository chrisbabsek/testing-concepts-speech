package de.babsek.demo.testingconcepts.domain.events

data class BankAccountOpenedEvent(
    val bankAccountId: String,
    val ownerName: String,
    val initialBalance: Double
)
