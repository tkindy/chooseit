package com.tylerkindy.chooseit.di

import dagger.Component
import io.ktor.server.engine.ApplicationEngine

@Component(modules = [
    MainModule::class
])
interface AppComponent {
    fun server(): ApplicationEngine
}
