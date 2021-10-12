package de.babsek.demo.testingconcepts.domain.events

data class MoneyTransferArrivedEvent(
    val bankAccountId: String,
    val amount: Double,
    val reason: String
)
