object Versions {
    object Plugins {
        val shadow = "5.1.0"
        val versions = "0.26.0"
        val liquibase = "2.0.1"
    }

    val kotlin = "1.3.50"
    val ktor = "1.2.5"
    val logback = "1.2.3"
    val ktorm = "2.5"
    val postgresql = "42.2.8"
    val dagger = "2.24"
    val liquibase = "3.6.1"
}

object Libs {
    val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    val ktorNetty = "io.ktor:ktor-server-netty:${Versions.ktor}"
    val logback = "ch.qos.logback:logback-classic:${Versions.logback}"
    val ktorCore = "io.ktor:ktor-server-core:${Versions.ktor}"
    val ktorLocations = "io.ktor:ktor-locations:${Versions.ktor}"
    val ktorMetrics = "io.ktor:ktor-metrics:${Versions.ktor}"
    val ktorSessions = "io.ktor:ktor-server-sessions:${Versions.ktor}"
    val ktorHost = "io.ktor:ktor-server-host-common:${Versions.ktor}"
    val ktorGson = "io.ktor:ktor-gson:${Versions.ktor}"
    val ktorClientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
    val ktorClientCoreJvm = "io.ktor:ktor-client-core-jvm:${Versions.ktor}"
    val ktorClientApache = "io.ktor:ktor-client-apache:${Versions.ktor}"
    val ktorClientJson = "io.ktor:ktor-client-json-jvm:${Versions.ktor}"
    val ktorClientGson = "io.ktor:ktor-client-gson:${Versions.ktor}"
    val postgresql = "org.postgresql:postgresql:${Versions.postgresql}"
    val ktorm = "me.liuwj.ktorm:ktorm-core:${Versions.ktorm}"

    val liquibaseCore = "org.liquibase:liquibase-core:${Versions.liquibase}"

    val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"

    val ktorTest = "io.ktor:ktor-server-tests:${Versions.ktor}"
}
