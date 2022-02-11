package info.masoudkarimi.tasky.features.user.exceptions

object EmailInvalidException : Exception()

object EmailOrPasswordInvalidException : Exception()

object EmailAlreadyRegisteredException : Exception()

object WeakPasswordException : Exception()

class RequiredFieldMissedException(val fieldName: String) : Exception()

class UserGeneralException(message: String): Exception(message)