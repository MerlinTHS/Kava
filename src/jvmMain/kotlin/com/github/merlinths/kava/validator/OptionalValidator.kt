package com.github.merlinths.kava.validator

import com.github.merlinths.kava.ValidScope
import com.github.merlinths.kava.validation.validate
import java.util.Optional

class OptionalValidator<Type : Any> : Validator<Type, Optional<Type>> {
    override val invalid = Optional.empty<Type>()

    override fun valid(value: Type) =
        Optional.of(value)
}

fun <Type: Any> optional(
    block: ValidScope<Type>.() -> Type
) = OptionalValidator<Type>()
    .validate(block)