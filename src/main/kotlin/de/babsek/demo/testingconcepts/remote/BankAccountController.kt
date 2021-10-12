package de.babsek.demo.testingconcepts.remote

import de.babsek.demo.testingconcepts.domain.commands.AcceptMoneyTransferCommand
import de.babsek.demo.testingconcepts.domain.commands.OpenBankAccountCommand
import de.babsek.demo.testingconcepts.domain.commands.TransferMoneyCommand
import de.babsek.demo.testingconcepts.projection.BankAccountProjectionEntity
import de.babsek.demo.testingconcepts.projection.BankAccountProjectionRepository
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping
class BankAccountController(
    private val commandGateway: CommandGateway,
    private val bankAccountProjectionRepository: BankAccountProjectionRepository
) {

    @GetMapping("bankaccounts")
    fun findAll(): List<BankAccountProjectionEntity> {
        return bankAccountProjectionRepository.findAll()
    }

    @GetMapping("bankaccounts/{id}")
    fun findById(@PathVariable id: String): ResponseEntity<BankAccountProjectionEntity> {
        return when (val account = bankAccountProjectionRepository.findByBankAccountId(id)) {
            null -> ResponseEntity.notFound().build()
            else -> ResponseEntity.ok(account)
        }
    }

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

    @ResponseStatus(HttpStatus.ACCEPTED)
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

    @PostMapping("transfers")
    fun transferMoney(@RequestBody request: MoneyTransferDto) {
        commandGateway.sendAndWait<Unit>(
            TransferMoneyCommand(
                bankAccountId = request.originBankAccountId,
                targetBankAccount = request.destinationBankAccountId,
                amount = request.amount,
                reason = request.reason
            )
        )
    }

    data class MoneyTransferDto(
        val originBankAccountId: String,
        val destinationBankAccountId: String,
        val amount: Double,
        val reason: String
    )
}
