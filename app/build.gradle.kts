import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.compose.compiler)
}

// A NEW, MORE ROBUST HELPER FUNCTION
fun getApiKey(propertyKey: String): String {
    val localPropertiesFile = rootProject.file("local.properties")
    if (!localPropertiesFile.exists() || !localPropertiesFile.isFile) {
        return ""
    }

    // Read all lines from the file
    val lines = localPropertiesFile.readLines()

    // Find the line that starts with the key we want
    val apiKeyLine = lines.firstOrNull { it.startsWith(propertyKey) }

    // If found, split by '=' and return the value. Otherwise, return empty.
    return apiKeyLine?.split("=")?.getOrNull(1)?.trim() ?: ""
}

android {
    namespace = "com.bilals.chatbottest"
    compileSdk = 36

    buildFeatures {
        compose = true
        buildConfig = true
    }



    defaultConfig {
        applicationId = "com.bilals.chatbottest"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // CORRECTED: Ensure you are using an escaped quote \" and not two quotes ""
        buildConfigField("String", "GEMINI_API_KEY", "\"${getApiKey("GEMINI_API_KEY")}\"")
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

}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.material)
    // Compose
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)


    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Hilt (Dependency Injection)
    implementation(libs.hilt.android)
    implementation(libs.common)
    kapt(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)


    // Google AI (Gemini)
    implementation(libs.generativeai)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Test Dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

kapt {
    correctErrorTypes = true
}
