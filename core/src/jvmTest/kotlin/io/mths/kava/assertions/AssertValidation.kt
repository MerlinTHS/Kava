package io.mths.kava.assertions

import io.mths.kava.ValidationScope
import io.mths.kava.result.ValidationResult
import io.mths.kava.validator.extensions.validate
import kotlin.test.assertEquals

fun assertResult(
    expected: ValidationResult,
    block: ValidationScope<*>.() -> Unit
) {
    val result = validate(block)

    assertEquals(expected, result)
}