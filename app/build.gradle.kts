plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.oliveira.maia"
    compileSdk = 34

    kapt {
        generateStubs = true
    }

    defaultConfig {
        applicationId = "com.oliveira.maia"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
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
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }

        kotlinOptions {
            jvmTarget = "17"
        }

        buildFeatures {
            compose = true
            viewBinding = true
            dataBinding = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = "1.4.3"
        }
    }

    dependencies {

        ksp("com.google.devtools.ksp:symbol-processing:1.9.10-1.0.13")

        // AndroidX
        implementation("androidx.core:core-ktx:1.12.0")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
        implementation("androidx.activity:activity-compose:1.8.2")

        implementation ("com.google.code.gson:gson:2.9.0")

        // Compose
        implementation(platform("androidx.compose:compose-bom:2023.03.00"))
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-graphics")
        implementation("androidx.compose.ui:ui-tooling-preview")
        implementation("androidx.compose.material3:material3")
        implementation("androidx.core:core-splashscreen:1.0.1")
        implementation("androidx.activity:activity-compose:1.8.2")
        implementation("androidx.compose.ui:ui:1.5.4")
        implementation("androidx.compose.material:material:1.5.4")
        implementation("androidx.compose.foundation:foundation:1.5.4")
        implementation("androidx.navigation:navigation-compose:2.7.6")

        // Dagger Hilt
        implementation("androidx.hilt:hilt-navigation-fragment:1.1.0")
        implementation("com.google.dagger:hilt-android:2.49")
        implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
        kapt("com.google.dagger:hilt-android-compiler:2.49")
        kapt("androidx.hilt:hilt-compiler:1.1.0")
        kapt("com.google.dagger:dagger-android-processor:2.49")


        // Navigation
        val nav_version = "2.7.6"
        implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
        implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
        implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
        implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
        implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
        androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")
        implementation("androidx.navigation:navigation-compose:$nav_version")

        // Room
        val roomVersion = "2.5.0"

        implementation("androidx.room:room-runtime:$roomVersion")
        ksp("androidx.room:room-compiler:2.5.0")
        implementation("androidx.room:room-ktx:$roomVersion")
        implementation("androidx.room:room-rxjava2:$roomVersion")
        implementation("androidx.room:room-rxjava3:$roomVersion")
        implementation("androidx.room:room-guava:$roomVersion")
        testImplementation("androidx.room:room-testing:$roomVersion")
        implementation("androidx.room:room-paging:$roomVersion")

        // Testing
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.5")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
        androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
        debugImplementation("androidx.compose.ui:ui-tooling")
        debugImplementation("androidx.compose.ui:ui-test-manifest")
    }
}
