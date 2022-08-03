plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven ("https://jitpack.io")
}

kotlin {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }
}

dependencies {
    //todo workaround to build iOS Arm64 simulator:
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
}
