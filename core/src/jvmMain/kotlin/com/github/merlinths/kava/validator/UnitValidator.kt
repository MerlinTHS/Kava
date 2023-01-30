package com.github.merlinths.kava.validator

import com.github.merlinths.kava.annotations.Kava
import com.github.merlinths.kava.ValidationScope
import com.github.merlinths.kava.Validator

open class MyBaseValidator : Validator<Unit, Unit> {
    override val invalid: Unit
        get() = TODO("Not yet implemented")

    override fun valid(value: Unit) {
        TODO("Not yet implemented")
    }

    override fun ValidationScope<*>.validate(wrapper: Unit) {

    }
}

@Kava("noRight")
class NotRight : MyBaseValidator() {

}

/*
    TODO: Create real unit validator and result Validator
 */
@Kava("validate")
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
    com.github.merlinths.kava.scope.validate(UnitValidator(), block)