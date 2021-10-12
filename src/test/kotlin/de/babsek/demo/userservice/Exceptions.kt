package de.babsek.demo.userservice

class UserAlreadyExistsException : RuntimeException()

data class UserNotFoundException(val username: String) : RuntimeException(
    "User $username not found!"
)
