package de.babsek.demo.userservice

class UserService(
    private val usersRepository: UsersRepository
) {
    fun findAll(): List<User> {
        return usersRepository.findAll()
    }

    fun findByUsername(username: String): User {
        return usersRepository.findByUsername(username)
    }

    fun findByUsernameOrNull(username: String): User? {
        return usersRepository.findByUsernameOrNull(username)
    }

    fun addUser(user: User) {
        when (usersRepository.existsByName(user.username)) {
            true -> throw UserAlreadyExistsException()
            false -> usersRepository.add(user)
        }
    }

    fun deleteUser(username: String) {
        usersRepository.removeByUsername(username)
    }
}
