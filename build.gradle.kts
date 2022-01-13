val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
}

group = "info.masoudkarimi"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    // adds Ktor's core component to our project
    implementation("io.ktor:ktor-server-core:$ktor_version")

    // adds the Netty engine to our project, allowing us to use server functionality
    // without having to rely on an external application container
    implementation("io.ktor:ktor-server-netty:$ktor_version")

    //provides an implementation of SLF4J, allowing us to see nicely formatted logs in our console.
    implementation("ch.qos.logback:logback-classic:$logback_version")

    /**
     * provides a convenient mechanism for converting Kotlin objects into a serialized
     * form like JSON, and vice versa. We will use it to format our APIs output, and to
     * consume user input that is structured in JSON. In order to use ktor-serialization,
     * we also have to apply the org.jetbrains.kotlin.plugin.serialization plugin.
     * */
    implementation( "io.ktor:ktor-serialization:$ktor_version")

    /**
     * allows us to test parts of our Ktor application without having to use the
     * whole HTTP stack in the process. We will use this to define unit tests for
     * our project.
     * */
    testImplementation("io.ktor:ktor-server-tests:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}