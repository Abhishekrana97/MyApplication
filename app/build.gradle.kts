plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.google.gms.google.services) // Apply Parcelize plugin
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    testImplementation(libs.androidx.ui.test.junit4.android)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation (libs.androidx.navigation.compose)
    
    // Additional libraries
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.multidex)
    implementation(libs.material)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.gson)

    // Dependency Injection
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // Logger
    implementation(libs.timber)

    // Room database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)

    implementation(libs.ui)
    implementation(libs.material3)
    implementation(libs.androidx.runtime)

    //Hilt
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit.jupiter)

    //Lifecycle Runtime Compose
    implementation(libs.lifecycle.compose)


    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.androidx.material.icons.extended)

    // Timber for logging
    implementation(libs.timber)

    // Coil for Remote Images
    implementation(libs.coil.compose)

    // Charts
    implementation(libs.mpandroidchart)

    // Data Store
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // Testing Lib.
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.core.testing)
    debugImplementation(libs.ui.tooling)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.robolectric)
    testImplementation(libs.truth)
    androidTestImplementation(libs.ui.test.junit4) // Match Compose version
    debugImplementation(libs.ui.test.manifest)


    // Testing Hilt
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)


    // Biometric Auth
    implementation(libs.androidx.biometric)


    implementation(libs.play.services.location)

    // Google Maps SDK for Android
    implementation(libs.places)
    implementation(libs.play.services.maps)

// Google maps Compose
    implementation(libs.maps.compose)

    implementation (libs.android.pdf.viewer)

    implementation (libs.androidx.media3.exoplayer)
    implementation (libs.androidx.media3.ui)
    // Firebase Cloud Firestore
    implementation(libs.firebase.firestore.ktx)
}