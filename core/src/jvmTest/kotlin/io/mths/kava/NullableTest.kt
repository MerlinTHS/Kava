package io.mths.kava

import io.mths.kava.assertions.assertFailure
import io.mths.kava.assertions.assertSuccess
import io.mths.kava.validator.`*`
import io.mths.kava.validator.getValue
import io.mths.kava.validator.nullable
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class NullableTest {
    @Nested
    inner class ScopeFunction {
        @Test
        fun `Returns null when it fails`() {
            val result = nullable<String> {
                fail()
            }

            assertNull(result)
        }

        @Test
        fun `Returns non-nullable when it succeeds`() {
            val result = nullable { "Kava" }

            assertNotNull(result)
            assertEquals("Kava", result)
        }
    }

    @Nested
    inner class SnowExtension {
        @Test
        fun `Fails for null`() {
            assertFailure {
                val maybeResult: String? = null
                maybeResult.`*`
            }
        }

        @Test
        fun `Succeeds for non-nullable`() {
            assertSuccess {
                val maybeResult: String? = "Coffee"
                maybeResult.`*`
            }
        }
    }

    @Nested
    inner class Delegation {
        @Test
        fun `Fails for null`() {
            assertFailure {
                val result: String by null

                result // Trigger delegation
            }
        }

        @Test
        fun `Succeeds for not-null`() {
            assertSuccess {
                val result by "Kava"

                result // Trigger delegation
            }
        }
    }
}