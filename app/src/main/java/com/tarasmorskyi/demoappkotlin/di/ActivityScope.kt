package com.tarasmorskyi.demoappkotlin.di

import dagger.releasablereferences.CanReleaseReferences
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import javax.inject.Scope


@Scope
@Retention(RetentionPolicy.RUNTIME)
@CanReleaseReferences
annotation class ActivityScope