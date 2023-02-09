package io.mths.kava.assertions

import io.mths.kava.ValidationScope
import io.mths.kava.validator.ValidationResult

fun assertSuccess(block: ValidationScope<*>.() -> Unit) =
    assertResult(ValidationResult.Success, block)