package io.mths.kava.result

import io.mths.kava.GenerateExtensions
import io.mths.kava.ValidationScope
import io.mths.kava.Validator

@GenerateExtensions("result")
class ResultValidator<Type> : Validator<Type, Result<Type>> {
    override val invalid =
        Result.Failure<Type>()

    override fun valid(value: Type) =
        Result.Success(value)

    override fun ValidationScope<*>.validate(wrapper: Result<Type>) =
        when (wrapper) {
            is Result.Success -> wrapper.value
            else -> fail()
        }
}