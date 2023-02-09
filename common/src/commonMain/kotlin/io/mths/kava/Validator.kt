package io.mths.kava

interface Validator<Type, WrapperType> {
    val invalid: WrapperType

    fun valid(value: Type): WrapperType

    fun ValidationScope<*>.validate(wrapper: WrapperType): Type
}