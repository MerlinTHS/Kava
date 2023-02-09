package io.mths.kava

/**
 * Custom validators marked with this annotation will be used to generate
 * - a scope function called [scopeName]
 * - snowflake extensions
 * - validated delegations
 *
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class GenerateExtensions(
    val scopeName: String
)