package com.github.merlinths.kava.assertions

import com.github.merlinths.kava.scope.ValidationScope
import com.github.merlinths.kava.validator.ValidationResult
import com.github.merlinths.kava.validator.validate
import kotlin.test.assertEquals

fun assertResult(
    expected: ValidationResult,
    block: ValidationScope<*>.() -> Unit
) {
    val result = validate(block)

    assertEquals(expected, result)
}