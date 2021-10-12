package de.babsek.demo.testingconcepts.domain.eventhandler

import mu.KotlinLogging
import org.axonframework.config.ProcessingGroup
import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component

val logger = KotlinLogging.logger {}

/**
 * [EventHandler] implementation that logs [Any] event. This handler
 * is supposed to be used as non-tracking, i.e. subscribing event processor.
 */
@Component
@ProcessingGroup("logging")
class LoggingEventHandler {

    @EventHandler
    fun log(event: Any) {
        logger.info { event }
    }
}
