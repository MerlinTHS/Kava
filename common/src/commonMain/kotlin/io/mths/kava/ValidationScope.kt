package io.mths.kava

/**
 * Provides a scope to deal with validation logic.
 * Everytime a possible invalid value like an empty Optional or null is accessed,
 * the scope immediately skips the remaining code inside and returns an invalid result.
 */
interface ValidationScope<ScopeType> {
    /**
     * This method stops execution of the current [ValidationScope].
     *
     * The Scope returns an invalid result ( like null or an empty [java.util.Optional] ).
     *
     * */
    fun fail(): Nothing

    /**
     * Runs [block] in the current scope and uses validator
     * to return a wrapped result.
     */
    fun <WrapperType> host(
        validator: Validator<ScopeType, WrapperType>,
        block: ValidationScope<ScopeType>.() -> ScopeType
    ): WrapperType
}