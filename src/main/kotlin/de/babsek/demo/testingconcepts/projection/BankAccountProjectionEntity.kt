package de.babsek.demo.testingconcepts.projection

import javax.persistence.*

@Table(name = "bankaccountprojection")
@Entity
data class BankAccountProjectionEntity(
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    @Column(name = "bankaccountid", unique = true)
    val bankAccountId: String,
    @Column(name = "balance")
    val balance: Double
)
