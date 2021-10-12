package de.babsek.demo.testingconcepts.part6database

import de.babsek.demo.testingconcepts.projection.BankAccountProjectionEntity
import de.babsek.demo.testingconcepts.projection.BankAccountProjectionRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.dao.DataIntegrityViolationException

@DataJpaTest
class BankAccountProjectionDatabaseTest @Autowired constructor(
    private val testEntityManager: TestEntityManager,
    private val repository: BankAccountProjectionRepository
) {

    val testAccount1 = BankAccountProjectionEntity(bankAccountId = "1", balance = 150.0)

    @BeforeEach
    fun `init test items`() {
        testEntityManager.clear()
        testEntityManager.persistAndFlush(testAccount1)
    }

    @Test
    fun `allow to write user to database`() {
        repository.findByBankAccountId("1") shouldBe testAccount1
    }

    @Test
    fun `ensure unique constraint for bank account id`() {
        val newAccountWithSameId = BankAccountProjectionEntity(
            bankAccountId = "1", balance = 750.0
        )

        shouldThrow<DataIntegrityViolationException> {
            repository.saveAndFlush(newAccountWithSameId)
        }
    }
}
