package com.github.merlinths.kava.annotations

/**
 * Custom validators marked with this annotation will be used to generate
 * - a scope function called [name]
 * - snowflake extensions
 * - validated delegations
 *
 */
annotation class Kava(
    val name: String
)