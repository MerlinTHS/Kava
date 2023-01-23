package com.github.merlinths.kava

import java.util.Optional

interface JvmValidScope<ScopeType> : ValidScope<ScopeType> {
    operator fun <Type: Any> Optional<Type>.component1(): Type =
        if (isPresent)
            this.get()
        else
            fail()
}