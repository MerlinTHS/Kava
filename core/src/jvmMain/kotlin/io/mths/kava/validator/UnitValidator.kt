package io.mths.kava.validator

import io.mths.kava.result.ValidationResult
import io.mths.kava.ValidationScope
import io.mths.kava.Validator
import io.mths.kava.result.onFailure
import io.mths.kava.scope.validate

class UnitValidator : Validator<Unit, Unit> {
    override val invalid = Unit

    override fun valid(value: Unit) = Unit

    override fun ValidationScope<*>.validate(
        wrapper: Unit
    ) = Unit
}

fun kava(
    block: ValidationScope<*>.() -> Unit
): Unit = validate(UnitValidator(), block)