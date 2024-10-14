import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.0.21"
    kotlin("plugin.spring") version "2.0.21"
    kotlin("plugin.serialization") version "2.0.21"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "tv.tracker"
version = "0.0.1-SNAPSHOT"

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
        jvmTarget.set(JvmTarget.JVM_21)
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlinx.serialization.ExperimentalSerializationApi")
        }
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-tomcat")
    implementation("org.springframework:spring-web")
    implementation("org.springframework:spring-webmvc")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.1")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Database
    implementation("org.jetbrains.exposed:exposed-spring-boot-starter:0.55.0")
    implementation("org.jetbrains.exposed:exposed-kotlin-datetime:0.55.0")
    implementation("org.liquibase:liquibase-core")
    runtimeOnly("org.postgresql:postgresql")

    // Test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation(platform("io.kotest:kotest-bom:5.9.1"))
    testImplementation("io.kotest:kotest-runner-junit5")
    testImplementation("io.kotest:kotest-assertions-core")
    testImplementation("io.kotest:kotest-assertions-json")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.3.0")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
