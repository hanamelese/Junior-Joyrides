plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.trial_junior"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.trial_junior"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.hilt.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Dagger-Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    ksp(libs.androidx.hilt.compiler)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Room
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // Gson
    implementation(libs.gson)

    // Logging
    implementation(libs.okhttp.logging.interceptor)

    // Image rendering using Coil
    implementation(libs.coil.compose)

    // Navigation Compose
    implementation(libs.androidx.navigation.compose)

    // ExoPlayer for video playback
    implementation(libs.exoplayer.core)
    implementation(libs.exoplayer.ui)
    implementation(libs.exoplayer.dash)
    implementation(libs.exoplayer.hls)
    implementation(libs.exoplayer.common)

    // Android YouTube Player Chromecast Sender
    implementation(libs.youtubeplayer.chromecast.sender)

    // Security Crypto
    implementation(libs.androidx.security.crypto)

    // DataStore Preferences
    implementation(libs.androidx.datastore.preferences)

    // Accompanist Pager and Pager Indicators
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    // Lottie Compose
    implementation(libs.lottie.compose)
}







//plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.kotlin.compose)
//    alias(libs.plugins.hilt.android)
//    alias(libs.plugins.ksp)
//}
//
//android {
//    namespace = "com.example.trial_junior"
//    compileSdk = 35
//
//    defaultConfig {
//        applicationId = "com.example.trial_junior"
//        minSdk = 24
//        targetSdk = 35
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_17
//    }
//
//    kotlinOptions {
//        jvmTarget = "17"
//    }
//
//    buildFeatures {
//        compose = true
//    }
//
//    composeOptions {
//        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
//    }
//}
//
//dependencies {
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//    implementation(libs.androidx.material.icons.extended)
//    implementation(libs.androidx.hilt.navigation.compose)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)
//
//    // Dagger-Hilt
//    implementation(libs.hilt.android)
//    ksp(libs.hilt.compiler)
//    ksp(libs.androidx.hilt.compiler)
//
//    // Coroutines
//    implementation(libs.kotlinx.coroutines.core)
//    implementation(libs.kotlinx.coroutines.android)
//
//    // Room
//    implementation(libs.androidx.room.ktx)
//    implementation(libs.androidx.room.runtime)
//    ksp(libs.androidx.room.compiler)
//
//    // Retrofit
//    implementation(libs.retrofit)
//    implementation(libs.retrofit.converter.gson)
//
//    // Gson
//    implementation(libs.gson)
//
//    // Logging
//    implementation(libs.okhttp.logging.interceptor)
//
//    // Image rendering using Coil
//    implementation(libs.coil.compose)
//
//    // Navigation Compose (Added)
//    implementation(libs.androidx.navigation.compose)
//
//    // ExoPlayer for video playback (Added)
//    implementation(libs.exoplayer.core)
//    implementation(libs.exoplayer.ui)
//    implementation(libs.exoplayer.dash)
//    implementation(libs.exoplayer.hls)
//    implementation(libs.exoplayer.common)
//
//    // Android YouTube Player Chromecast Sender (Added)
//    implementation(libs.youtubeplayer.chromecast.sender)
//
//    // Security Crypto (Added)
//    implementation(libs.androidx.security.crypto)
//
//    // DataStore Preferences (Added)
//    implementation(libs.androidx.datastore.preferences)
//}











//plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
//    alias(libs.plugins.kotlin.compose)
//    alias(libs.plugins.hilt.android)
//    alias(libs.plugins.ksp)
//    // Removed: kapt plugin since we're using KSP for Hilt
//}
//
//android {
//    namespace = "com.example.trial_junior"
//    compileSdk = 35
//
//    defaultConfig {
//        applicationId = "com.example.trial_junior"
//        minSdk = 24
//        targetSdk = 35
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_17
//        targetCompatibility = JavaVersion.VERSION_17
//    }
//
//    kotlinOptions {
//        jvmTarget = "17"
//    }
//
//    buildFeatures {
//        compose = true
//    }
//
//    composeOptions {
//        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
//    }
//}
//
//dependencies {
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.ui)
//    implementation(libs.androidx.ui.graphics)
//    implementation(libs.androidx.ui.tooling.preview)
//    implementation(libs.androidx.material3)
//    implementation(libs.androidx.material.icons.extended)
//    implementation(libs.androidx.hilt.navigation.compose)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.ui.test.junit4)
//    debugImplementation(libs.androidx.ui.tooling)
//    debugImplementation(libs.androidx.ui.test.manifest)
//
//    // Dagger-Hilt
//    implementation(libs.hilt.android)
//    ksp(libs.hilt.compiler) // Using KSP instead of kapt
//    ksp(libs.androidx.hilt.compiler)
//
//    // Coroutines
//    implementation(libs.kotlinx.coroutines.core)
//    implementation(libs.kotlinx.coroutines.android)
//
//    // Room
//    implementation(libs.androidx.room.ktx)
//    implementation(libs.androidx.room.runtime)
//    ksp(libs.androidx.room.compiler)
//
//    // Retrofit
//    implementation(libs.retrofit)
//
//    // Gson
//    implementation(libs.gson)
//    implementation(libs.retrofit.converter.gson)
//
//    // Logging
//    implementation(libs.okhttp.logging.interceptor)
//
//    // Image rendering using Coil
//    implementation(libs.coil.compose)
//
//    // Navigation Compose
//    implementation(libs.androidx.navigation.compose)
//
//    // ExoPlayer for video playback
//    implementation(libs.exoplayer.core)
//    implementation(libs.exoplayer.ui)
//    implementation(libs.exoplayer.dash)
//    implementation(libs.exoplayer.hls)
//    implementation(libs.exoplayer.common)
//
//    // Android YouTube Player Chromecast Sender
//    implementation(libs.youtubeplayer.chromecast.sender)
//
//    // Security Crypto
//    implementation(libs.androidx.security.crypto)
//
//    // DataStore Preferences
//    implementation(libs.androidx.datastore.preferences)
//}