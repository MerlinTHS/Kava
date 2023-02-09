package io.mths.kava.assertions

import io.mths.kava.ValidationScope
import io.mths.kava.validator.ValidationResult


fun assertFailure(block: ValidationScope<*>.() -> Unit) =
    assertResult(ValidationResult.Failure, block)