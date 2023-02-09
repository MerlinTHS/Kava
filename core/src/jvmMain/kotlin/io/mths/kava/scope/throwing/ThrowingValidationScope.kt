package io.mths.kava.scope.throwing

import io.mths.kava.ValidationScope
import io.mths.kava.Validator

/**
 * A validation scope that skips remaining code by throwing a [ValidationException].
 *
 *  Note: **Be careful when catching exceptions too generally!**
 */
class ThrowingValidationScope<ScopeType> : ValidationScope<ScopeType> {
    override fun fail() =
        throw ValidationException

    override fun <WrapperType> host(
        validator: Validator<ScopeType, WrapperType>,
        block: ValidationScope<ScopeType>.() -> ScopeType
    ) = with(validator) {
        try {
            valid(value = block())
        } catch (exception: ValidationException) {
            invalid
        }
    }
}