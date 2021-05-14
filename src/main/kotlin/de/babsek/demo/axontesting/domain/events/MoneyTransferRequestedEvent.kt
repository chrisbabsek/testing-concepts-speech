package de.babsek.demo.axontesting.domain.events

data class MoneyTransferRequestedEvent(
    val originBankAccountId: String,
    val targetBankAccountId: String,
    val amount: Double,
    val reason: String
)
