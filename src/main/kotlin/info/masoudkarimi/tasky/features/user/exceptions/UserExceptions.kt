package info.masoudkarimi.tasky.features.user.exceptions

object EmailInvalidExceptions : Exception()

object EmailAlreadyRegisteredExceptions : Exception()

object WeakPasswordException : Exception()

class RequiredFieldMissedException(val fieldName: String) : Exception()

class UserRegistrationGeneralException(message: String): Exception(message)