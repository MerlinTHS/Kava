package io.mths.kava.validator

import io.mths.kava.GenerateExtensions
import io.mths.kava.ValidationScope
import io.mths.kava.Validator
import io.mths.kava.scope.validate
import kotlin.reflect.KProperty

@GenerateExtensions("nullable")
class NullValidator<Type> : Validator<Type, Type?> {
    override val invalid = null

    override fun valid(value: Type) = value

    override fun ValidationScope<*>.validate(wrapper: Type?) =
        wrapper ?: fail()
}