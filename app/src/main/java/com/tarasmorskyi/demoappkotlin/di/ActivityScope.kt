package com.tarasmorskyi.demoappkotlin.di

import dagger.releasablereferences.CanReleaseReferences
import javax.inject.Scope


@Scope
@Retention
@CanReleaseReferences
annotation class ActivityScope