plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    //firebase
    id("com.google.gms.google-services") version "4.3.15" apply false

    // Kotlin Gradle Plugin (kotlin migration)
    id("org.jetbrains.kotlin.jvm") version "1.9.22" apply false

}
