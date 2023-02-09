package io.mths.kava.validator

import io.mths.kava.ValidationScope
import io.mths.kava.Validator
import io.mths.kava.scope.validate

class UnitValidator : Validator<Unit, ValidationResult> {
    override val invalid = ValidationResult.Failure

    override fun valid(value: Unit) = ValidationResult.Success


    override fun ValidationScope<*>.validate(wrapper: ValidationResult) =
        Unit
}

enum class ValidationResult {
    Success,
    Failure
}

fun ValidationResult.failed() =
    !succeeded()

fun ValidationResult.succeeded() =
    this == ValidationResult.Success

infix fun ValidationResult.onFailure(block: () -> Unit) = also {
    if (it.failed()) {
        block()
    }
}

infix fun ValidationResult.onSuccess(block: () -> Unit) = also {
    if (it.succeeded()) {
        block()
    }
}

fun onlyValidate(
    block: ValidationScope<*>.() -> Unit
) {
    validate(block)
}

fun validate(
    block: ValidationScope<*>.() -> Unit
): ValidationResult =
    validate(UnitValidator(), block)