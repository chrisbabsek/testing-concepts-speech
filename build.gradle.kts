import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.5"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.github.ben-manes.versions") version "0.38.0"
    kotlin("jvm") version "1.5.0"
    kotlin("plugin.spring") version "1.5.0"
    kotlin("plugin.jpa") version "1.5.0"
}

repositories {
    mavenCentral()
}

group = "de.babsek.demo.testingconcepts"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web:2.5.5")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.5.5")

    implementation("com.h2database:h2:1.4.200")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.31")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.31")
    implementation("io.github.microutils:kotlin-logging:2.0.11")

    implementation("org.axonframework:axon-spring-boot-starter:4.5.4")
    implementation("org.axonframework.extensions.kotlin:axon-kotlin:0.1.0")

    runtimeOnly("org.postgresql:postgresql:42.2.24.jre7")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.5")
    testImplementation("com.ninja-squad:springmockk:3.0.1")
    testImplementation("org.testcontainers:junit-jupiter:1.16.0")
    testImplementation("org.axonframework:axon-test:4.5.4")

    testImplementation("io.kotest:kotest-runner-junit5:4.6.1")
    testImplementation("io.kotest:kotest-assertions-core:4.6.1")
    testImplementation("io.kotest:kotest-assertions-json:4.6.1")
    testImplementation("io.kotest:kotest-property:4.6.1")
    testImplementation("io.kotest:kotest-framework-datatest:4.6.1")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
        languageVersion = "1.5"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
