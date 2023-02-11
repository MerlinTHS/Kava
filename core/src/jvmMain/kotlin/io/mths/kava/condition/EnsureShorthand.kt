package io.mths.kava.condition

import io.mths.kava.ValidationScope

context (ValidationScope<*>)
operator fun <Type> Type.invoke(
    assertions: Type.() -> Boolean
) = this.assertions()

context (ValidationScope<*>)
operator fun Boolean.unaryPlus() =
    this@ValidationScope.ensure (this)