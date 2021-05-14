package de.babsek.demo.axontesting.domain.exceptions

class NotEnoughMoneyException(
    bankAccountId: String,
    requestedAmount: Double,
    currentBalance: Double
) : RuntimeException(
    "The bank account $bankAccountId balance is too low to transfer an amount of $requestedAmount! Current balance: $currentBalance"
)