package info.masoudkarimi.tasky.data.routes


import info.masoudkarimi.tasky.data.models.*
import info.masoudkarimi.tasky.ext.isEmailValid
import info.masoudkarimi.tasky.plugins.USER
import info.masoudkarimi.tasky.utils.BcryptHasher
import info.masoudkarimi.tasky.utils.JwtProvider
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.core.qualifier.named
import org.koin.ktor.ext.inject
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineCollection
import org.litote.kmongo.eq
import org.litote.kmongo.setValue


fun generateJwtToken(userEmail: String): String? {
    return JwtProvider.createJWT(userEmail)
}

fun Routing.userRouting() {
    val userCollection by inject<CoroutineCollection<UserDto>>(named("users"))

    route("USERS") {
        /**
         * Create new user and return it as response
         * */
        post {
            val userRequest = call.receive<UserRequest>()
            this.context.application.log.info("Registering user: $userRequest")

            if (!userRequest.email.isEmailValid() || userRequest.password.isNullOrBlank()) {
                return@post call.respond(
                    status = HttpStatusCode.BadRequest,
                    errorResponse("User is invalid!")
                )
            }

            userCollection.findOne(UserDto::email eq userRequest.email)?.let {
                return@post call.respond(
                    status = HttpStatusCode.Conflict,
                    errorResponse("Email already registered!")
                )
            }

            val user = UserDto(
                firstName = userRequest.firstName,
                lastName = userRequest.lastName,
                email = userRequest.email,
                password = BcryptHasher.hashPassword(userRequest.password),
                token = generateJwtToken(userRequest.email!!)
            )

            val isSuccess = userCollection.insertOne(user).wasAcknowledged()
            if (!isSuccess) {
                return@post call.respond(
                    status = HttpStatusCode.InternalServerError,
                    errorResponse("Could not insert user")
                )
            }

            val insertedUser = userCollection.findOne(UserDto::email eq userRequest.email) ?: return@post call.respond(
                status = HttpStatusCode.InternalServerError,
                errorResponse("Could not insert user")
            )

            call.respond(
                status = HttpStatusCode.Created,
                successResponse(
                    UserResponse(
                        firstName = insertedUser.firstName,
                        lastName = insertedUser.lastName,
                        email = insertedUser.email,
                        token = insertedUser.token
                    )
                )
            )
        }

        post("/login") {
            val userRequest = call.receive<UserRequest>()
            this.context.application.log.info("User logging in: $userRequest")
            if (!userRequest.email.isEmailValid() || userRequest.password.isNullOrBlank()) {
                return@post call.respond(
                    status = HttpStatusCode.BadRequest,
                    errorResponse("Email or password is invalid!")
                )
            }

            val user = userCollection.findOne(
                and(
                    UserDto::email eq userRequest.email
                )
            ) ?: return@post call.respond(
                status = HttpStatusCode.Unauthorized,
                errorResponse("Email or password is invalid!")
            )

            if (!BcryptHasher.checkPassword(userRequest.password, user.password ?: "")) {
                return@post call.respond(
                    status = HttpStatusCode.Unauthorized,
                    errorResponse("Email or password is invalid!")
                )
            }

            val newToken = generateJwtToken(userRequest.email!!)

            val result = userCollection.updateOne(UserDto::_id eq user._id, setValue(UserDto::token, newToken)).wasAcknowledged()

            if (!result) {
                this.context.application.log.info("Login failed: user=${userRequest.email}")
                call.respond(
                    status = HttpStatusCode.InternalServerError,
                    errorResponse("Something went wrong!")
                )
            }


            return@post call.respond(
                status = HttpStatusCode.OK,
                successResponse(UserResponse(
                    firstName = user.firstName,
                    lastName = user.lastName,
                    email = user.email,
                    token = newToken
                ))
            )
        }
    }

    route(USER) {
        authenticate {
            get {
                val user = context.authentication.principal<UserDto>() ?: return@get call.respond(
                    status = HttpStatusCode.Unauthorized,
                    errorResponse("Authentication error!")
                )

                return@get call.respond(
                    status = HttpStatusCode.OK,
                    successResponse(UserResponse(
                        firstName = user.firstName,
                        lastName = user.lastName,
                        email = user.email,
                        token = user.token
                    ))
                )
            }
        }
    }
}