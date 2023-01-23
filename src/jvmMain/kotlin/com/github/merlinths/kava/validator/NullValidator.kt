package com.github.merlinths.kava.validator

import com.github.merlinths.kava.ValidScope
import com.github.merlinths.kava.validation.validate

class NullValidator<Type> : Validator<Type, Type?> {
    override val invalid = null

    override fun valid(value: Type) = value
}

fun <Type> nullable(
    block: ValidScope<Type>.() -> Type
) = NullValidator<Type>()
    .validate(block)