package com.github.merlinths.kava.validator

import com.github.merlinths.kava.annotations.Kava
import com.github.merlinths.kava.ValidationScope
import com.github.merlinths.kava.Validator
import com.github.merlinths.kava.scope.validate
import kotlin.reflect.KProperty

@Kava(name = "nullable")
class NullValidator<Type> : Validator<Type, Type?> {
    override val invalid = null

    override fun valid(value: Type) = value

    override fun ValidationScope<*>.validate(wrapper: Type?) =
        wrapper ?: fail()
}

fun <Type> nullable(
    block: ValidationScope<Type>.() -> Type
) = validate(NullValidator(), block)

context (ValidationScope<*>)
operator fun <Type> Type?.getValue(
    thisRef: Any?,
    property: KProperty<*>
): Type =  with(this@ValidationScope) {
    this@getValue.`*`
}

context (ValidationScope<*>)
operator fun <Type> (() -> Type?).getValue(
    thisRef: Any?,
    property: KProperty<*>
): Type = with(this@ValidationScope) {
    this@getValue.`*`
}
context (ValidationScope<*>)
val <Type> Type?.`*`: Type
    get() = with(this@ValidationScope) {
        NullValidator<Type>().run {
            this@ValidationScope.validate(this@`*`)
        }
    }

context (ValidationScope<*>)
val <Type> (() -> Type?).`*`: Type
    get() = with(this@ValidationScope) {
        this@`*`().`*`
    }