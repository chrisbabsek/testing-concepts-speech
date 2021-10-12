package de.babsek.demo.testingconcepts.projection

import org.springframework.data.jpa.repository.JpaRepository

interface BankAccountProjectionRepository : JpaRepository<BankAccountProjectionEntity, String> {
    fun findByBankAccountId(bankAccountId: String): BankAccountProjectionEntity?
}
