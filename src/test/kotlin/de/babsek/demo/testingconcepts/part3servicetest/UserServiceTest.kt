package de.babsek.demo.testingconcepts.part3servicetest

import de.babsek.demo.userservice.User
import de.babsek.demo.userservice.UserService
import de.babsek.demo.userservice.UsersRepository
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verifySequence
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class UserServiceTest {
    @MockK
    lateinit var usersRepository: UsersRepository

    @InjectMockKs
    lateinit var subject: UserService

    val ted = User(username = "ted.tester", password = "secret")
    val beate = User(username = "beate.bach", password = "geheim")
    val users = listOf(ted, beate)

    @Test
    fun `find existing user by username`() {
        val ted = users.first()

        every { usersRepository.findByUsername("ted.tester") } returns ted

        subject.findByUsername("ted.tester") shouldBe ted
    }

    @Test
    fun `find mocked user by username`() {
        every { usersRepository.findByUsername("mock").username } returns "mock"

        val user = subject.findByUsername("mock")
        user.username shouldBe "mock"
    }

    @Test
    fun `add user`() {
        every { usersRepository.existsByName(ted.username) } returns false
        every { usersRepository.add(ted) } just runs

        subject.addUser(ted)

        verifySequence {
            usersRepository.existsByName(ted.username)
            usersRepository.add(ted)
        }
    }
}
