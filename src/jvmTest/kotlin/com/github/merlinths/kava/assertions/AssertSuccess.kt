package com.github.merlinths.kava.assertions

import com.github.merlinths.kava.scope.ValidationScope
import com.github.merlinths.kava.validator.ValidationResult

fun assertSuccess(block: ValidationScope<*>.() -> Unit) =
    assertResult(ValidationResult.Success, block)