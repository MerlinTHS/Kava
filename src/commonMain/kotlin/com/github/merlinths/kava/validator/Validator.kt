package com.github.merlinths.kava.validator

interface Validator<Type, WrapperType> {
    val invalid: WrapperType

    fun valid(value: Type): WrapperType
}