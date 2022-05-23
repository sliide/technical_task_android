package com.slide.test.users.rules

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * Overrides all Schedulers with a custom TestScheduler.
 */
class RxTestSchedulerRule(private val testScheduler: TestScheduler = TestScheduler()) : Scheduler(), TestRule {

    override fun apply(base: Statement, description: Description?): Statement {
        RxJavaPlugins.setIoSchedulerHandler { _ -> testScheduler }
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        RxJavaPlugins.setNewThreadSchedulerHandler { testScheduler }
        RxJavaPlugins.setSingleSchedulerHandler { testScheduler }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        return base
    }

    override fun createWorker() = testScheduler.createWorker()

    fun triggerActions() = testScheduler.triggerActions()
}
