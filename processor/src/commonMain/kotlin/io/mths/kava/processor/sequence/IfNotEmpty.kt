package io.mths.kava.processor.sequence

fun <Type> List<Type>.ifNotEmpty(action: List<Type>.() -> Unit) = apply {
    if (isNotEmpty()) {
        action()
    }
}