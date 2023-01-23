package com.github.merlinths.kava.validation

import com.github.merlinths.kava.ValidScope
import com.github.merlinths.kava.validator.Validator
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun <Type, WrapperType> Validator<Type, WrapperType>.launchValidation(
    block: ValidScope<Type>.() -> Type,
    onComplete: (WrapperType) -> Unit
) = runBlocking {
    lateinit var job: Job

    job = launch {
        with(ValidationJobScope<Type>(job)) {
            onComplete(valid(block()))
        }
    }
}

