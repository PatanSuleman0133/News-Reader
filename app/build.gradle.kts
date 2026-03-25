import com.android.build.api.dsl.DataBinding

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // firebase
    id("com.google.gms.google-services")

    id("kotlin-kapt")
    //test coverage
    id("jacoco")
}

android {
    namespace = "com.example.newsreaderapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.newsreaderapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        //for test coverage
        debug {
            isTestCoverageEnabled = true // Enable test coverage for Debug
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8 // Migrated to 1.8
        targetCompatibility = JavaVersion.VERSION_1_8 // Migrated to 1.8
    }
    kotlinOptions {
        jvmTarget = "1.8" // Migrated to 1.8
    }
    buildFeatures {
        viewBinding = true   //// for viewBinding
        dataBinding = true   //// for dataBinding
    }

    packaging {
        resources {
            excludes += setOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
                "META-INF/DEPENDENCIES",
                "META-INF/NOTICE"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Downgraded Kotlin stdlib to a compatible version
    //implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.22")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Logging interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    // Firebase auth
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
    // Google Play Services (Downgrade for compatibility)
    implementation("com.google.android.gms:play-services-measurement-api:21.0.0")
    implementation("com.google.android.gms:play-services-measurement-impl:21.0.0")

    // Security with SharedPreferences
    implementation("androidx.security:security-crypto:1.1.0-alpha06")

    // Material Components
    implementation("com.google.android.material:material:1.9.0")
    // Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.2")

    // Lifecycle ViewModel Components
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Glide
    implementation("com.github.bumptech.glide:glide:4.15.1")
    androidTestImplementation(libs.androidx.core.testing)
    annotationProcessor("com.github.bumptech.glide:compiler:4.15.1")

    // Room Database
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1") // For Kotlin projects
    implementation("androidx.room:room-ktx:2.6.1") // Kotlin Extensions and Coroutines support
    implementation("androidx.room:room-paging:2.6.1") // Paging 3 Integration

    //splash screen
    implementation ("androidx.core:core-splashscreen:1.0.1")




    //test cases  part
    // Unit Testing
    testImplementation ("junit:junit:4.13.2")
    //testImplementation ("org.mockito:mockito-core:5.2.0")
    testImplementation ("org.mockito:mockito-core:4.0.0")
    //testImplementation ("org.mockito.kotlin:mockito-kotlin:5.1.0")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.0.0")

    // Coroutines Testing
    //testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")

    // Paging 3 Testing
    testImplementation ("androidx.paging:paging-common-ktx:3.2.1")

    // Room Testing
    testImplementation ("androidx.room:room-testing:2.6.1")

    // Android Instrumentation Testing
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    // Jetpack Compose UI Testing
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.6.0")

    // Debugging UI tests
    debugImplementation ("androidx.compose.ui:ui-test-manifest:1.6.0")

    // for Navigation testing in UI
    androidTestImplementation ("androidx.test.espresso:espresso-intents:3.5.1")


    //for test coverage
    androidTestImplementation("org.jacoco:org.jacoco.core:0.8.8")


    testImplementation ("androidx.arch.core:core-testing:2.1.0")

    //fragement testing
    androidTestImplementation ("androidx.fragment:fragment-testing:1.5.5")

    // MockK for Unit Tests
    testImplementation ("io.mockk:mockk:1.13.5")

    // MockK for Android Instrumentation Tests
    androidTestImplementation ("io.mockk:mockk-android:1.13.5")
    // AndroidX Test
    //androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    // Required for using MockWebServer in unit tests
    testImplementation ("com.squareup.okhttp3:mockwebserver:4.9.3")

    // (For Android Instrumented Tests)
    androidTestImplementation ("com.squareup.okhttp3:mockwebserver:4.9.3")


    implementation ("com.squareup.moshi:moshi:1.15.0")
    implementation ("com.squareup.moshi:moshi-kotlin:1.15.0")
    testImplementation ("com.squareup.moshi:moshi-adapters:1.15.0")
    implementation(kotlin("test"))


}

//for test coverage
tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        html.required.set(true)
        xml.required.set(true)
        csv.required.set(false)
    }

    executionData.setFrom(
        fileTree(buildDir).matching {
            include("jacoco/testDebugUnitTest.exec")
        }
    )

    sourceDirectories.setFrom(files("$projectDir/src/main/java"))
    classDirectories.setFrom(files("$buildDir/tmp/kotlin-classes/debug"))
}

// for test coverrage to convert .ce to index.htlm
tasks.register<JacocoReport>("jacocoAndroidTestReport") {
    dependsOn("connectedDebugAndroidTest")

    reports {
        html.required.set(true)
        xml.required.set(true)
        csv.required.set(false)
    }

    executionData.setFrom(fileTree("app/build/outputs/code-coverage/connected/") {
        include("**/*.ec")
    })

    sourceDirectories.setFrom(files("$projectDir/src/main/java"))
    classDirectories.setFrom(files("$buildDir/tmp/kotlin-classes/debug"))
}
