package de.babsek.demo.axontesting.projection

import org.springframework.data.jpa.repository.JpaRepository

interface BankAccountProjectionRepository : JpaRepository<BankAccountProjectionEntity, String> {
    fun findByBankAccountId(bankAccountId: String): BankAccountProjectionEntity?
}