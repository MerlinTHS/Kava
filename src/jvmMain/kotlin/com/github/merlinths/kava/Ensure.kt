package com.github.merlinths.kava

import com.github.merlinths.kava.scope.ValidationScope
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@OptIn(ExperimentalContracts::class)
fun ValidationScope<*>.ensure(
    condition: Boolean
) {
    contract {
        returns() implies condition
    }
    if (!condition) {
        fail<Unit>()
    }
}

inline fun <Type> ValidationScope<*>.ensure(
    value: Type,
    assertions: Type.() -> Boolean
) {
    ensure (value.assertions())
}

inline fun ValidationScope<*>.ensure(
    assertions: () -> Boolean
) {
    ensure(assertions())
}