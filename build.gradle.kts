plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
//    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
}

