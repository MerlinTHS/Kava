package io.mths.kava.scope.throwing

/**
 * This exception is used to skip remaining code in validation scopes.
 * **Try to avoid catching it manually!**
 */
object ValidationException : Exception("Validation failed!")