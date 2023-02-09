package io.mths.kava.validator

import io.mths.kava.GenerateExtensions
import io.mths.kava.ValidationScope
import io.mths.kava.Validator
import io.mths.kava.scope.validate
import java.util.Optional
import kotlin.jvm.optionals.getOrElse
import kotlin.reflect.KProperty

@OptIn(ExperimentalStdlibApi::class)
@GenerateExtensions("optional")
class OptionalValidator<Type : Any> : Validator<Type, Optional<Type>> {
    override val invalid =
        Optional.empty<Type>()

    override fun valid(value: Type) =
        Optional.of(value)

    override fun ValidationScope<*>.validate(wrapper: Optional<Type>) =
        wrapper.getOrElse(::fail)
}

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