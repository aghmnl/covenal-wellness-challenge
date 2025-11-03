plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    alias(libs.plugins.dagger.hilt)
}

android {
    namespace = "com.agus.wellnessapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.agus.wellnessapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Hilt (Dependency Injection)
    implementation(libs.dagger.hilt.android) // Changed
    kapt(libs.dagger.hilt.compiler) // Changed
    implementation(libs.androidx.hilt.navigation.compose) // Changed

    // Coroutines
    implementation(libs.kotlinx.coroutines.android) // Changed

    // Compose (Navigation & ViewModel)
    implementation(libs.androidx.navigation.compose) // Changed
    implementation(libs.androidx.lifecycle.viewmodel.compose) // Changed

    // Retrofit (Networking)
    implementation(libs.squareup.retrofit) // Changed
    implementation(libs.squareup.converter.moshi) // Changed
    implementation(libs.squareup.okhttp.logging.interceptor) // Changed

    // Coil (Image Loading for Compose)
    implementation(libs.coil.compose) // Changed
}