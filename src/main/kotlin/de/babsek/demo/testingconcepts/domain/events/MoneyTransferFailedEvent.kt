package de.babsek.demo.testingconcepts.domain.events

data class MoneyTransferFailedEvent(
    val bankAccountId: String,
    val targetBankAccountId: String,
    val amount: Double,
    val errorMessage: String?
)
