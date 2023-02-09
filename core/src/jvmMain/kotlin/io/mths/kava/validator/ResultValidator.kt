package io.mths.kava.validator

import io.mths.kava.GenerateExtensions
import io.mths.kava.ValidationScope
import io.mths.kava.Validator
import io.mths.kava.result.ValidationResult
import io.mths.kava.scope.validate

@GenerateExtensions("validate")
class ResultValidator : Validator<Unit, ValidationResult> {
    override val invalid =
        ValidationResult.Failure

    override fun valid(value: Unit) =
        ValidationResult.Success

    override fun ValidationScope<*>.validate(
        wrapper: ValidationResult
    ) = Unit
}