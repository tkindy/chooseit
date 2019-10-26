package com.tylerkindy.chooseit.di

import dagger.Component
import io.ktor.server.engine.ApplicationEngine
import io.ktor.util.KtorExperimentalAPI

@KtorExperimentalAPI
@Component(modules = [
    MainModule::class
])
interface AppComponent {
    fun server(): ApplicationEngine
}
