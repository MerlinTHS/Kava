package io.mths.kava

/**
 * Marks a [Kava-Vanilla](https://github.com/MerlinTHS/Kava/wiki/Vanilla) function.
 * Unlike normal functions, you can use methods of the validation scope inside.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class Kava