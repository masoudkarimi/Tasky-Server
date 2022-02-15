package info.masoudkarimi.tasky.features.user.resource

import info.masoudkarimi.tasky.data.models.successResponse
import info.masoudkarimi.tasky.features.user.data.dao.UserDAO
import info.masoudkarimi.tasky.features.user.data.dao.toUserDTO
import info.masoudkarimi.tasky.features.user.domain.UserRepository
import info.masoudkarimi.tasky.features.user.domain.model.UserRequestDTO
import info.masoudkarimi.tasky.plugins.USER
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject


fun Application.userRoutes() {
    routing {
        userRegisterRoute()
        userLoginRoute()
        getUserInfoRoute()
    }
}

fun Route.userRegisterRoute() {
    val userRepository by inject<UserRepository>()

    post("$USER/auth/register") {
        val userRequest = call.receive<UserRequestDTO>()
        call.respond(HttpStatusCode.Created, successResponse(userRepository.register(userRequest).toUserDTO()))
    }
}

fun Route.userLoginRoute() {
    val userRepository by inject<UserRepository>()

    post("$USER/auth/login") {
        val userRequest = call.receive<UserRequestDTO>()
        call.respond(HttpStatusCode.OK, successResponse(userRepository.login(userRequest).toUserDTO()))
    }
}

fun Route.getUserInfoRoute() {
    authenticate {
        get(USER) {
            val user = context.authentication.principal<UserDAO>() ?: kotlin.run {
                return@get call.respond(HttpStatusCode.Unauthorized)
            }

            return@get call.respond(
                status = HttpStatusCode.OK,
                successResponse(user.toUserDTO())
            )
        }
    }
}