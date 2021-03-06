package team.bakkas.applicationquery

import kotlinx.coroutines.CoroutineExceptionHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerResponse
import team.bakkas.common.exceptions.ShopNotFoundException

@SpringBootApplication(scanBasePackages = ["team.bakkas"])
class ApplicationQueryApplication

fun main(args: Array<String>) {
    runApplication<ApplicationQueryApplication>(*args)
}