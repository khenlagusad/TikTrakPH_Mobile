
plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile"

    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.itismob.s01.lagusad.khenwalter.tiktrakph_mobile"

        minSdk = 24
        targetSdk = 37

        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    // Generate ViewBinding classes from XML layouts
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Existing dependencies
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)

    // Navigation Component
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // RecyclerView
    implementation(libs.recyclerview)

    // Lifecycle: ViewModel and LiveData
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)

    // Unit testing
    testImplementation(libs.junit)

    // Instrumented UI testing
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
}
