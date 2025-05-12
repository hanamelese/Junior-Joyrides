// Top-level build file where you can add configuration options common to all sub-projects/modules.

// Removed: buildscript block
// Reason: Hilt plugin is now defined in libs.versions.toml
// buildscript {
//     dependencies {
//         classpath("com.google.dagger:hilt-android-gradle-plugin:2.47")
//     }
// }

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false // Changed: Used alias from libs.versions.toml instead of id("com.android.library") version "8.1.1"
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    // Reason: Not needed at project level if removed from module level
    alias(libs.plugins.hilt.android) apply false // Added: Hilt plugin via libs.versions.toml
    alias(libs.plugins.ksp) apply false // Changed: Updated version to match Kotlin 2.0.0 via libs.versions.toml
    alias(libs.plugins.kotlin.kapt) apply false // Added: Defined in libs.versions.toml
}