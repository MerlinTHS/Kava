package com.github.merlinths.kava

import com.github.merlinths.kava.assertions.assertFailure
import com.github.merlinths.kava.assertions.assertSuccess
import com.github.merlinths.kava.validator.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.Optional
import kotlin.test.assertEquals

class OptionalTest {
    @Nested
    inner class ScopeFunction {
        @Test
        fun `Returns present optional when it succeeds`() {
            val result = optional { "Kava" }

            assert(result.isPresent)
            assertEquals("Kava", result.get())
        }

        @Test
        fun `Returns empty optional when it fails`() {
            val result = optional {
                ensure (false)
            }

            assert(result.isEmpty)
        }
    }

    @Nested
    inner class SnowExtension {
        @Test
        fun `Fails for empty optional`() {
            assertFailure {
                Optional.empty<String>().`*`
            }
        }

        @Test
        fun `Succeeds for present optional`() {
            assertSuccess {
                Optional.of("Kava").`*`
            }
        }
    }

    @Nested
    inner class Delegation {
        @Test
        fun `Fails for empty optional`() {
            assertFailure {
                val result by Optional.empty<String>()

                result // Trigger delegation
            }
        }

        @Test
        fun `Succeeds for present optional`() {
            assertSuccess {
                val result by Optional.of("Kava")

                result // Trigger delegation
            }
        }
    }
}