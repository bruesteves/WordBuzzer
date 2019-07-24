package com.wordbuzzer.domain.thread

import io.reactivex.Scheduler

interface PostExecutionThread {
    val scheduler : Scheduler
}