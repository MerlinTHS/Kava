package com.github.merlinths.kava

fun ValidScope<*>.ensure(condition: Boolean) {
    if (!condition) {
        fail<Unit>()
    }
}

fun <Type> ValidScope<*>.ensure(
    value: Type,
    assertions: Type.() -> Boolean
) {
    ensure (value.assertions())
}