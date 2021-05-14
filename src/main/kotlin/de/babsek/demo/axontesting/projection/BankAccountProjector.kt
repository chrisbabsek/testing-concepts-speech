package de.babsek.demo.axontesting.projection

import de.babsek.demo.axontesting.domain.events.BankAccountOpenedEvent
import de.babsek.demo.axontesting.domain.events.MoneyTransferArrivedEvent
import de.babsek.demo.axontesting.domain.events.MoneyTransferFailedEvent
import de.babsek.demo.axontesting.domain.events.MoneyTransferRequestedEvent
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@ProcessingGroup("bankAccountProjection")
@Component
class BankAccountProjector(
    private val repository: BankAccountProjectionRepository
) {

    @EventHandler
    fun on(event: BankAccountOpenedEvent) {
        repository.saveAndFlush(
            BankAccountProjectionEntity(
                bankAccountId = event.bankAccountId,
                balance = 0.0
            )
        )
    }

    @EventHandler
    fun on(event: MoneyTransferRequestedEvent) {
        repository
            .findByBankAccountId(event.originBankAccountId)
            ?.let { it.copy(balance = it.balance - event.amount) }
            ?.apply(repository::saveAndFlush)
    }

    @EventHandler
    fun on(event: MoneyTransferArrivedEvent) {
        repository
            .findByBankAccountId(event.bankAccountId)
            ?.let { it.copy(balance = it.balance + event.amount) }
            ?.apply(repository::saveAndFlush)
    }

    @EventHandler
    fun on(event: MoneyTransferFailedEvent) {
        repository
            .findByBankAccountId(event.bankAccountId)
            ?.let { it.copy(balance = it.balance + event.amount) }
            ?.apply(repository::saveAndFlush)
    }
}