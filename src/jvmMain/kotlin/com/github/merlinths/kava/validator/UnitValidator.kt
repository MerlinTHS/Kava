package com.github.merlinths.kava.validator

class UnitValidator : Validator<Unit, Unit> {
    override val invalid = Unit

    override fun valid(value: Unit) = Unit
}