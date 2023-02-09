package io.mths.kava

import io.mths.kava.result.onSuccess
import io.mths.kava.validator.validate
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

class EnsureTest {
    @Test
    fun `Omit statements after fail`() {
        validate {
            ensure(false)

            assert(false) { "Code after fail is executed!" }
        } onSuccess {
            fail { "Entered onSuccess block!" }
        }
    }
}