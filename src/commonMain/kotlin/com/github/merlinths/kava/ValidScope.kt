package com.github.merlinths.kava

interface ValidScope<ScopeType> {
    /**
     * This method stops execution of the current [ValidScope].
     *
     * The Scope returns an invalid result ( like null or an empty [Optional] ).
     *
     * @return The return type only exists to make the compiler happy. The
     * execution is stopped before anything gets returned.
     */
    fun <Type> fail(): Type

    /**
     * This method as to be a member of ValidScope because
     * nullable context receivers are treated like non-nullables.
     *
     * - "If the receiver type is nullable, the question mark is omitted"
     * See [Context Receiver KEEP](https://github.com/Kotlin/KEEP/blob/master/proposals/context-receivers.md)
     */
    operator fun <Type: Any> Type?.component1(): Type =
        this ?: fail()
}