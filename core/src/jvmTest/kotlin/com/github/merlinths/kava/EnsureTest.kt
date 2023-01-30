package com.github.merlinths.kava

import com.github.merlinths.kava.validator.onSuccess
import com.github.merlinths.kava.validator.validate
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