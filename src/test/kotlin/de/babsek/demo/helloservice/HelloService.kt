package de.babsek.demo.helloservice

class HelloService {
    fun sayHello(): String {
        return "Hello"
    }
}

class NotInTheRightMoodException : RuntimeException()
