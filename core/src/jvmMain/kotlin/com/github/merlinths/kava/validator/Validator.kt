package com.github.merlinths.kava.validator

import com.github.merlinths.kava.scope.ValidationScope

interface Validator<Type, WrapperType> {
    val invalid: WrapperType

    fun valid(value: Type): WrapperType

    fun ValidationScope<*>.validate(wrapper: WrapperType): Type
}