package de.babsek.demo.axontesting

import org.axonframework.springboot.autoconfig.JpaAutoConfiguration
import org.axonframework.springboot.autoconfig.JpaEventStoreAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    exclude = [
        JpaAutoConfiguration::class,
        JpaEventStoreAutoConfiguration::class
        ]
)
class BankApplication

fun main() {
    runApplication<BankApplication>()
}