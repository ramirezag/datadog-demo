plugins {
    kotlin("jvm") version "1.9.22"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // For writing logs to app.log
    implementation("org.slf4j:log4j-over-slf4j:2.0.11")
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("net.logstash.logback:logstash-logback-encoder:7.4")
    // For sending custom traces
    implementation("com.datadoghq:dd-trace-api:1.27.0")
    implementation("io.opentracing:opentracing-api:0.33.0")
    implementation("io.opentracing:opentracing-util:0.33.0")
    // For sending custom metrics via dogstatsd
    implementation("com.datadoghq:java-dogstatsd-client:4.3.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
