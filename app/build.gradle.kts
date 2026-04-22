plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
    id("com.mikepenz.aboutlibraries.plugin")
}

android {
    namespace = "com.anthropic.claude"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.anthropic.claude"
        minSdk = 32
        targetSdk = 36
        versionCode = 26041620
        versionName = "1.260416.20"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        debug {
            isMinifyEnabled = false
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
        buildConfig = true
    }
}

dependencies {
    // ─── Compose ────────────────────────────────────────────────────────────
    implementation("androidx.compose.ui:ui:1.10.4")
    implementation("androidx.compose.ui:ui-tooling-preview:1.10.4")
    implementation("androidx.compose.ui:ui-graphics:1.10.4")
    implementation("androidx.compose.ui:ui-text:1.10.4")
    implementation("androidx.compose.ui:ui-unit:1.10.4")
    implementation("androidx.compose.foundation:foundation:1.10.4")
    implementation("androidx.compose.foundation:foundation-layout:1.10.4")
    implementation("androidx.compose.animation:animation:1.10.4")
    implementation("androidx.compose.animation:animation-core:1.10.4")
    implementation("androidx.compose.runtime:runtime:1.10.4")
    implementation("androidx.compose.runtime:runtime-saveable:1.10.4")
    implementation("androidx.compose.material:material:1.9.1")
    implementation("androidx.compose.material3:material3:1.5.0-alpha12")
    implementation("androidx.compose.material3.adaptive:adaptive:1.2.0")
    implementation("androidx.compose.material3.adaptive:adaptive-layout:1.2.0")
    implementation("androidx.compose.material3.adaptive:adaptive-navigation:1.2.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.10.4")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.10.4")

    // ─── Activity / Lifecycle ────────────────────────────────────────────────
    implementation("androidx.activity:activity:1.13.0")
    implementation("androidx.activity:activity-ktx:1.13.0")
    implementation("androidx.activity:activity-compose:1.13.0")
    implementation("androidx.lifecycle:lifecycle-runtime:2.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.10.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.10.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.10.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.10.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.10.0")
    implementation("androidx.lifecycle:lifecycle-livedata-core:2.10.0")
    implementation("androidx.lifecycle:lifecycle-livedata-core-ktx:2.10.0")
    implementation("androidx.lifecycle:lifecycle-service:2.10.0")
    implementation("androidx.lifecycle:lifecycle-process:2.10.0")

    // ─── Core ────────────────────────────────────────────────────────────────
    implementation("androidx.core:core:1.18.0")
    implementation("androidx.core:core-ktx:1.18.0")
    implementation("androidx.core:core-splashscreen:1.2.0")
    implementation("androidx.core:core-remoteviews:1.1.0")

    // ─── Navigation ──────────────────────────────────────────────────────────
    implementation("androidx.navigation:navigation-runtime:2.7.7")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigationevent:navigationevent:1.0.0")
    implementation("androidx.navigationevent:navigationevent-compose:1.0.0")

    // ─── Fragment / AppCompat ────────────────────────────────────────────────
    implementation("androidx.fragment:fragment:1.8.8")
    implementation("androidx.fragment:fragment-ktx:1.8.8")
    implementation("androidx.appcompat:appcompat:1.7.1")

    // ─── Room ────────────────────────────────────────────────────────────────
    implementation("androidx.room:room-runtime:2.8.4")
    ksp("androidx.room:room-compiler:2.8.4")

    // ─── DataStore ───────────────────────────────────────────────────────────
    implementation("androidx.datastore:datastore:1.3.0-alpha06")
    implementation("androidx.datastore:datastore-preferences:1.3.0-alpha06")
    implementation("androidx.datastore:datastore-core:1.3.0-alpha06")
    implementation("androidx.datastore:datastore-preferences-core:1.3.0-alpha06")

    // ─── SQLite ──────────────────────────────────────────────────────────────
    implementation("androidx.sqlite:sqlite:2.6.2")
    implementation("androidx.sqlite:sqlite-bundled:2.6.2")

    // ─── SavedState ──────────────────────────────────────────────────────────
    implementation("androidx.savedstate:savedstate:1.4.0")
    implementation("androidx.savedstate:savedstate-ktx:1.4.0")
    implementation("androidx.savedstate:savedstate-compose:1.4.0")

    // ─── Startup ─────────────────────────────────────────────────────────────
    implementation("androidx.startup:startup-runtime:1.2.0")

    // ─── WorkManager ─────────────────────────────────────────────────────────
    implementation("androidx.work:work-runtime:2.11.1")
    implementation("androidx.work:work-runtime-ktx:2.11.1")

    // ─── Glance (Widgets) ────────────────────────────────────────────────────
    implementation("androidx.glance:glance:1.2.0-alpha01")
    implementation("androidx.glance:glance-appwidget:1.2.0-alpha01")
    implementation("androidx.glance:glance-material3:1.2.0-alpha01")
    implementation("androidx.glance:glance-preview:1.2.0-alpha01")
    implementation("androidx.glance:glance-appwidget-preview:1.2.0-alpha01")

    // ─── Health Connect ──────────────────────────────────────────────────────
    implementation("androidx.health.connect:connect-client:1.1.0")

    // ─── Security / Auth ─────────────────────────────────────────────────────
    implementation("androidx.security:security-crypto:1.0.0")
    implementation("androidx.biometric:biometric:1.1.0")
    implementation("androidx.credentials:credentials:1.5.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.5.0")

    // ─── Browser / WebKit ────────────────────────────────────────────────────
    implementation("androidx.browser:browser:1.9.0")
    implementation("androidx.webkit:webkit:1.15.0")
    implementation("androidx.javascriptengine:javascriptengine:1.0.0")

    // ─── Window / Display ────────────────────────────────────────────────────
    implementation("androidx.window:window:1.5.1")
    implementation("androidx.window:window-core:1.5.1")
    implementation("androidx.graphics:graphics-shapes:1.0.1")
    implementation("androidx.graphics:graphics-path:1.0.1")

    // ─── UI Misc ─────────────────────────────────────────────────────────────
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.viewpager2:viewpager2:1.1.0-beta02")
    implementation("androidx.metrics:metrics-performance:1.0.0")
    implementation("androidx.tracing:tracing:2.0.0-alpha02")
    implementation("androidx.tracing:tracing-ktx:2.0.0-alpha02")
    implementation("androidx.profileinstaller:profileinstaller:1.4.1")

    // ─── Google Material ─────────────────────────────────────────────────────
    implementation("com.google.android.material:material:1.9.0")

    // ─── Kotlin Coroutines ───────────────────────────────────────────────────
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.10.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-guava:1.10.2")

    // ─── Kotlinx Serialization ───────────────────────────────────────────────
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")

    // ─── OkHttp ──────────────────────────────────────────────────────────────
    implementation("com.squareup.okhttp3:okhttp:5.5.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.5.0")

    // ─── Moshi ───────────────────────────────────────────────────────────────
    implementation("com.squareup.moshi:moshi:1.15.2")
    implementation("com.squareup.moshi:moshi-kotlin:1.15.2")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.2")

    // ─── Retrofit ────────────────────────────────────────────────────────────
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.11.0")

    // ─── Firebase ────────────────────────────────────────────────────────────
    implementation(platform("com.google.firebase:firebase-bom:33.13.0"))
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")

    // ─── Google Play Billing ─────────────────────────────────────────────────
    implementation("com.android.billingclient:billing-ktx:8.3.0")

    // ─── Google Identity / Sign-In ───────────────────────────────────────────
    implementation("com.google.android.gms:play-services-auth:21.3.0")

    // ─── Wire (Protocol Buffers) ─────────────────────────────────────────────
    implementation("com.squareup.wire:wire-runtime:5.5.0")
    implementation("com.squareup.wire:wire-grpc-client:5.5.0")
    implementation("com.squareup.wire:wire-moshi-adapter:5.5.0")

    // ─── Kotlinx DateTime ────────────────────────────────────────────────────
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.2")

    // ─── Segment Analytics ───────────────────────────────────────────────────
    implementation("com.segment.analytics.kotlin:android:1.19.1")

    // ─── Lyft Kronos (NTP clock) ─────────────────────────────────────────────
    implementation("com.lyft.kronos:kronos-android:0.0.1-alpha11")

    // ─── AboutLibraries ──────────────────────────────────────────────────────
    implementation("com.mikepenz:aboutlibraries-core:11.3.0")
    implementation("com.mikepenz:aboutlibraries-compose-m3:11.3.0")

    // ─── Sentry ──────────────────────────────────────────────────────────────
    implementation("io.sentry:sentry-android:8.8.0")

    // ─── Datadog ─────────────────────────────────────────────────────────────
    implementation("com.datadoghq:dd-sdk-android-rum:2.18.1")
    implementation("com.datadoghq:dd-sdk-android-logs:2.18.1")
    implementation("com.datadoghq:dd-sdk-android-trace:2.18.1")
    implementation("com.datadoghq:dd-sdk-android-okhttp:2.18.1")
}
