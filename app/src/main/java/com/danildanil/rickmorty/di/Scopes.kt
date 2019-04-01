package com.danildanil.rickmorty.di

import javax.inject.Scope

@Scope
annotation class PerActivity

@MustBeDocumented
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerApplication

@Scope
annotation class PerFragment