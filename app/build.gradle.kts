plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.stocksnap"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.stocksnap"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    packaging {
        resources {
            excludes += "/META-INF/DEPENDENCIES"
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.material:material-icons-extended:1.5.0")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("com.google.android.material:material:1.9.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-compiler:2.47")

    // Room
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    // CameraX (placeholders)
    implementation("androidx.camera:camera-camera2:1.2.3")
    implementation("androidx.camera:camera-lifecycle:1.2.3")
    implementation("androidx.camera:camera-view:1.2.3")

    // ML Kit (dependencies are placeholders; offline usage requires bundling the libraries)
    implementation("com.google.mlkit:text-recognition:16.0.0")
    implementation("com.google.mlkit:barcode-scanning:17.2.0")

    // ZXing for QR
    implementation("com.google.zxing:core:3.5.1")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")

    // Coil for image loading
    implementation("io.coil-kt:coil-compose:2.4.0")


    // Google Sign-In (Firebase Auth)
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    // Firebase BoM and libraries
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")

    // Zip utility for backup
    implementation("androidx.sqlite:sqlite-ktx:2.4.0")

    // WorkManager
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    // Coroutines support for Tasks (used with ML Kit Task.await)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")


}
