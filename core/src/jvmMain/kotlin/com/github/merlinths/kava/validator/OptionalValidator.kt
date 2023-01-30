package com.github.merlinths.kava.validator

import com.github.merlinths.kava.annotations.Kava
import com.github.merlinths.kava.ValidationScope
import com.github.merlinths.kava.Validator
import com.github.merlinths.kava.scope.validate
import java.util.Optional
import kotlin.jvm.optionals.getOrElse
import kotlin.reflect.KProperty

@OptIn(ExperimentalStdlibApi::class)
@Kava(name = "optional")
class OptionalValidator<Type : Any> : Validator<Type, Optional<Type>> {
    override val invalid =
        Optional.empty<Type>()

    override fun valid(value: Type) =
        Optional.of(value)

    override fun ValidationScope<*>.validate(wrapper: Optional<Type>) =
        wrapper.getOrElse(::fail)
}

fun <Type: Any> optional(
    block: ValidationScope<Type>.() -> Type
) = validate(OptionalValidator(), block)

context (ValidationScope<*>)
val <Type: Any> Optional<Type>.`*`: Type
    get() = with(this@ValidationScope) {
        OptionalValidator<Type>().run {
            this@ValidationScope.validate(this@`*`)
        }
    }

context (ValidationScope<*>)
val <Type: Any> (() -> Optional<Type>).`*`: Type
    get() = with(this@ValidationScope) {
        this@`*`().`*`
    }

context (ValidationScope<*>)
operator fun <Type: Any> Optional<Type>.getValue(
    thisRef: Any?,
    property: KProperty<*>
): Type = with(this@ValidationScope) {
    this@getValue.`*`
}

context (ValidationScope<*>)
operator fun <Type : Any> (() -> Optional<Type>).getValue(
    thisRef: Any?,
    property: KProperty<*>
): Type = with(this@ValidationScope) {
    this@getValue.`*`
}