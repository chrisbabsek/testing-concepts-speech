package de.babsek.demo.axontesting.remote

import de.babsek.demo.axontesting.domain.commands.AcceptMoneyTransferCommand
import de.babsek.demo.axontesting.domain.commands.OpenBankAccountCommand
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class BankAccountController(
    private val commandGateway: CommandGateway
) {

    @PostMapping("bankaccounts")
    fun createBankAccount(@RequestBody request: BankAccountDto): String {
        val command = OpenBankAccountCommand(
            bankAccountId = request.bankAccountId,
            ownerName = request.ownerName
        )
        return commandGateway.sendAndWait(command)
    }

    data class BankAccountDto(
        val bankAccountId: String,
        val ownerName: String
    )

    @PostMapping("bankaccounts/{bankAccountId}/payings")
    fun payInMoney(@PathVariable bankAccountId: String, @RequestBody request: PayInMoneyDto) {
        val command = AcceptMoneyTransferCommand(
            bankAccountId = bankAccountId,
            amount = request.amount,
            reason = "pay in"
        )
        commandGateway.sendAndWait<Unit>(command)
    }

    data class PayInMoneyDto(
        val amount: Double
    )

}