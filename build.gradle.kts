import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    application
    kotlin("jvm") version "1.3.31"
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

group = "xyz.pavelkorolev.bladerunner"
version = "0.2.0"

application {
    mainClassName = "$group.MainKt"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.github.ajalt:clikt:2.4.0")
    implementation("com.drewnoakes:metadata-extractor:2.13.0")

    testImplementation("org.assertj:assertj-core:3.12.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.2")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")

        setExceptionFormat("full")
        showExceptions = true
        showCauses = true
        showStackTraces = true

        showStandardStreams = false
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Jar> {
    archiveFileName.set("bladerunner.jar")
    manifest {
        attributes(
            mapOf(
                "Main-Class" to application.mainClassName
            )
        )
    }
}