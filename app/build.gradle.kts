plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.salmee.artai"
    compileSdk = 35
    buildFeatures{
        viewBinding=true
    }

    defaultConfig {
        applicationId = "com.salmee.artai"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.mediation.test.suite)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation ("androidx.recyclerview:recyclerview:1.2.1")
    implementation(platform("com.google.firebase:firebase-bom:33.9.0"))
    //implementation("com.google.firebase:firebase-analytics")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("com.intuit.ssp:ssp-android:1.1.1")
    implementation ("com.intuit.sdp:sdp-android:1.1.1")
}
  // 🚫 Force exclude Google Ads SDK from all dependencies
configurations.all {
    exclude(group = "com.google.android.gms", module = "play-services-ads")
    exclude(group = "com.google.android.gms", module = "play-services-ads-lite")
    exclude(group = "com.google.android.gms", module = "play-services-ads-base")
    exclude(group = "com.google.android.gms", module = "play-services-measurement")
    exclude(group = "com.google.android.gms", module = "play-services-measurement-api")
    exclude(group = "com.google.android.gms", module = "play-services-measurement-sdk-api")
    exclude(group = "com.google.android.gms", module = "play-services-measurement-sdk")
    exclude(group = "com.google.android.gms", module = "play-services-ads-identifier")
}