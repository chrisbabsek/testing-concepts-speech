package de.babsek.demo.testingconcepts.domain.events

data class MoneyTransferRequestedEvent(
    val originBankAccountId: String,
    val targetBankAccountId: String,
    val amount: Double,
    val reason: String
)
