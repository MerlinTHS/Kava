package io.mths.kava.result

sealed interface Result<Type> {
    class Failure<Type> : Result<Type>
    data class Success<Type>(val value: Type) : Result<Type>
}