package deck

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DeckServer

fun main(args: Array<String>) {
    runApplication<DeckServer>(*args)
}