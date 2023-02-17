plugins {
    id("multiplatform-compose-setup")
    id("android-setup")
}

val os = org.gradle.internal.os.OperatingSystem.current()

val platform = when {
    os.isWindows -> "win"
    os.isMacOsX -> "mac"
    else -> "linux"
}

val jdkVersion = "16"

kotlin {
    sourceSets {

        named("commonMain") {
            dependencies {
            }
        }

        named("androidMain") {
            dependencies {
                implementation(Deps.AndroidX.Activity.activityCompose)
            }
        }

        named("desktopMain") {
            dependencies {
                api("org.openjfx:javafx-base:$jdkVersion:${platform}")
                api("org.openjfx:javafx-graphics:$jdkVersion:${platform}")
                api("org.openjfx:javafx-controls:$jdkVersion:${platform}")
                api("org.openjfx:javafx-fxml:$jdkVersion:${platform}")
                api("org.openjfx:javafx-media:$jdkVersion:${platform}")
                api("org.openjfx:javafx-web:$jdkVersion:${platform}")
                api("org.openjfx:javafx-swing:$jdkVersion:${platform}")
                api("org.jetbrains.kotlinx:kotlinx-coroutines-javafx:1.6.4")
            }
        }
    }
}

