import com.adarshr.gradle.testlogger.theme.ThemeType

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val ktorm_version = "3.3.0"
val hikari_version = "5.0.1"
val mysql_version = "8.0.22"
val swagger_version = "2.0.0"
val jackson_version = "2.9.8"
val kotestVersion = "5.6.0"

plugins {
    kotlin("jvm") version "1.8.21"
    id("io.ktor.plugin") version "2.3.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.21"
    id("com.adarshr.test-logger") version "3.2.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

group = "studymoa"
version = "0.0.1"
application {
    mainClass.set("app.ApplicationKt")

    // val isDevelopment: Boolean = true
    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

testlogger {
    theme = ThemeType.STANDARD
    showExceptions = true
    showStackTraces = true
    showFullStackTraces = false
    showCauses = true
    slowThreshold = 500
    // showSummary = false
    showSummary = true
    showSimpleNames = true
    showPassed = true
    showSkipped = true
    showFailed = true
    showOnlySlow = false
    showStandardStreams = false
    showPassedStandardStreams = true
    showSkippedStandardStreams = true
    showFailedStandardStreams = true
}

dependencies {
    implementation("org.ktorm:ktorm-support-mysql:$ktorm_version")

    implementation("io.ktor:ktor-server-config-yaml:$ktor_version")
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-double-receive-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-swagger-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-openapi:$ktor_version")
    implementation("io.ktor:ktor-server-hsts-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-cors-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

    // implementation("io.ktor:ktor-serialization-jackson:$ktor_version")

	// auth-jwt
	implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")
	// implementation("com.auth0:java-jwt:4.4.0")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")

    // database
    implementation("org.ktorm:ktorm-core:$ktorm_version")
    implementation("mysql:mysql-connector-java:$mysql_version")
    implementation("com.zaxxer:HikariCP:$hikari_version")
    // implementation("org.ktorm:ktorm-jackson:$ktorm_version")

    // swagger
    implementation("io.github.smiley4:ktor-swagger-ui:$swagger_version")

	implementation("org.ktorm:ktorm-jackson:3.3.0")
	implementation("io.ktor:ktor-serialization-jackson:$ktor_version")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jackson_version")

    // aws s3
    implementation("aws.smithy.kotlin:aws-signing-crt:0.21.0")
    implementation("aws.sdk.kotlin:s3-jvm:0.26.0-beta")

	// // https://mvnrepository.com/artifact/org.jetbrains.kotlin/kotlin-reflect
	// runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.8.22")

	// https://mvnrepository.com/artifact/com.fasterxml.jackson.module/jackson-module-kotlin
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.8")
    // implementation("io.swagger.core.v3:swagger-annotations:2.2.8")

    // implementation("io.github.juanchosaravia.autodsl:annotation:0.0.11")
    // implementation("io.github.juanchosaravia.autodsl:processor:0.0.11")

    // https://github.com/coobird/thumbnailator/tree/master
    // https://mvnrepository.com/artifact/net.coobird/thumbnailator
    implementation("net.coobird:thumbnailator:0.4.1")

    // kotest BDD
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")

    // https://mvnrepository.com/artifact/com.h2database/h2
    testImplementation("com.h2database:h2:2.2.220")

    implementation("org.yaml:snakeyaml:1.28")
}