package de.babsek.demo.userservice

data class UsersRepository(
    private val users: MutableList<User> = mutableListOf()
) {

    fun findAll(): List<User> = users.toList()

    fun add(user: User) {
        users.add(user)
    }

    fun removeByUsername(username: String) {
        requireNotNull(findByUsernameOrNull(username)) {
            "No such user: $username"
        }
    }

    fun findByUsernameOrNull(username: String): User? = users
        .singleOrNull { it.username == username }

    fun findByUsername(username: String): User = findByUsernameOrNull(username)
        ?: throw UserNotFoundException(username = username)

    fun existsByName(username: String): Boolean = findByUsernameOrNull(username) != null
}
