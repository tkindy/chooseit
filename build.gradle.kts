import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion: String by project
val ktorVersion: String by project
val logbackVersion: String by project
val shadowVersion: String by project
val postgresqlVersion: String by project
val ktormVersion: String by project
val daggerVersion: String by project

buildscript {
    repositories {
        jcenter()
    }
}

plugins {
    kotlin("jvm") version Versions.kotlin
    kotlin("kapt") version Versions.kotlin
    application
    id("com.github.johnrengelman.shadow") version Versions.shadow
    id("com.github.ben-manes.versions") version Versions.versions
}

group = "chooseit-backend"
version = "0.0.1"

application {
    mainClassName = "com.tylerkindy.chooseit.MainKt"
}

task("stage") {
    dependsOn(tasks.build, tasks.clean)
}
tasks.build {
    mustRunAfter(tasks.clean)
}

tasks.withType(KotlinCompile::class) {
    kotlinOptions {
        jvmTarget = "11"
    }
}

tasks.named("shadowJar", ShadowJar::class) {
    manifest {
        attributes("Main-Class" to application.mainClassName)
    }
}

repositories {
    mavenLocal()
    jcenter()
    maven { setUrl("https://kotlin.bintray.com/ktor") }
    maven { setUrl("https://kotlin.bintray.com/kotlinx") }
}

dependencies {
    implementation(Libs.kotlinStdlib)
    implementation(Libs.ktorNetty)
    implementation(Libs.logback)
    implementation(Libs.ktorCore)
    implementation(Libs.ktorLocations)
    implementation(Libs.ktorMetrics)
    implementation(Libs.ktorSessions)
    implementation(Libs.ktorHost)
    implementation(Libs.ktorGson)
    implementation(Libs.ktorClientCore)
    implementation(Libs.ktorClientCoreJvm)
    implementation(Libs.ktorClientApache)
    implementation(Libs.ktorClientJson)
    implementation(Libs.ktorClientGson)
    implementation(Libs.postgresql)
    implementation(Libs.ktorm)

    implementation(Libs.dagger)
    kapt(Libs.daggerCompiler)

    testImplementation(Libs.ktorTest)
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,\\.v\\-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}

tasks.named("dependencyUpdates", DependencyUpdatesTask::class) {
    rejectVersionIf {
        isNonStable(candidate.version)
    }
}
