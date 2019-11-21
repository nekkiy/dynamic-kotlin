
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.41"
    `java-library`
}

repositories {
    jcenter()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable")
    implementation("org.jetbrains.kotlin:kotlin-script-util")

    implementation("org.jetbrains.kotlin:kotlin-scripting-compiler-embeddable")

    testImplementation("org.jetbrains.kotlin:kotlin-test:kotlin-test-junit")
    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.1")
}
