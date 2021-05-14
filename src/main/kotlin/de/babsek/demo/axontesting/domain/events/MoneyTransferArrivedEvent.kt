package de.babsek.demo.axontesting.domain.events

data class MoneyTransferArrivedEvent(
    val bankAccountId: String,
    val amount: Double,
    val reason: String
)
