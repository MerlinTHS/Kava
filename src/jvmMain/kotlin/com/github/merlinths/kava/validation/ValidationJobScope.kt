package com.github.merlinths.kava.validation

import com.github.merlinths.kava.ValidScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

class ValidationJobScope<Type>(
    private val job: Job
) : ValidScope<Type> {
    override fun <Type> fail(): Type = runBlocking(job) {
        job.cancel()
        yield()

        throw IllegalStateException()
    }
}