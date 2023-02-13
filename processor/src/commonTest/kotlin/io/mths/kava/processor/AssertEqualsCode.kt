package io.mths.kava.processor

import org.intellij.lang.annotations.Language
import kotlin.test.assertEquals

fun assertEqualsCode(
    @Language("kotlin") expected: String,
    @Language("kotlin") actual: String
) = assertEquals(
    expected.removeWhitespaces(),
    actual.removeWhitespaces()
)

private fun String.removeWhitespaces() =
    replace("\\s".toRegex(), "")