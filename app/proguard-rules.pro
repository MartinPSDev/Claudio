# ─── Anthropic / Claude app ──────────────────────────────────────────────────
-keep class com.anthropic.claude.** { *; }
-keepclassmembers class com.anthropic.claude.** { *; }

# ─── Kotlinx Serialization ───────────────────────────────────────────────────
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** { *** Companion; }
-keepclasseswithmembers class kotlinx.serialization.json.** { kotlinx.serialization.KSerializer serializer(...); }
-keep,includedescriptorclasses class com.anthropic.claude.**$$serializer { *; }
-keepclassmembers class com.anthropic.claude.** {
    *** Companion;
}
-keepclasseswithmembers class com.anthropic.claude.** {
    kotlinx.serialization.KSerializer serializer(...);
}
-keep class kotlinx.serialization.** { *; }

# ─── Kotlin ───────────────────────────────────────────────────────────────────
-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    static void check*(...);
    static void throw*(...);
}

# ─── OkHttp / Okio ───────────────────────────────────────────────────────────
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-keepattributes Signature
-keepattributes *Annotation*

# ─── Moshi ───────────────────────────────────────────────────────────────────
-keep class com.squareup.moshi.** { *; }
-keep @com.squareup.moshi.JsonQualifier interface *
-keepclassmembers @com.squareup.moshi.JsonClass class * extends java.lang.Enum {
    <fields>;
    **[] values();
}
-keepnames @com.squareup.moshi.JsonClass class *
-dontwarn com.squareup.moshi.**

# ─── Wire (Protocol Buffers) ─────────────────────────────────────────────────
-keep class com.squareup.wire.** { *; }
-keep class * extends com.squareup.wire.Message { *; }
-dontwarn com.squareup.wire.**

# ─── Retrofit ────────────────────────────────────────────────────────────────
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# ─── Room ────────────────────────────────────────────────────────────────────
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# ─── AndroidX ────────────────────────────────────────────────────────────────
-keep class androidx.** { *; }
-keep interface androidx.** { *; }
-dontwarn androidx.**

# ─── Compose ─────────────────────────────────────────────────────────────────
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

# ─── Coroutines ──────────────────────────────────────────────────────────────
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}
-dontwarn kotlinx.coroutines.**

# ─── Firebase ────────────────────────────────────────────────────────────────
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

# ─── Sentry ──────────────────────────────────────────────────────────────────
-keep class io.sentry.** { *; }
-dontwarn io.sentry.**

# ─── Datadog ─────────────────────────────────────────────────────────────────
-keep class com.datadog.** { *; }
-keep class datadog.** { *; }
-dontwarn com.datadog.**

# ─── Segment Analytics ───────────────────────────────────────────────────────
-keep class com.segment.** { *; }
-dontwarn com.segment.**

# ─── AboutLibraries ──────────────────────────────────────────────────────────
-keep class com.mikepenz.aboutlibraries.** { *; }
-dontwarn com.mikepenz.aboutlibraries.**

# ─── Health Connect ──────────────────────────────────────────────────────────
-keep class androidx.health.connect.** { *; }

# ─── Glance (Widgets) ────────────────────────────────────────────────────────
-keep class androidx.glance.** { *; }

# ─── DataStore / Protobuf ────────────────────────────────────────────────────
-keep class * extends com.google.protobuf.GeneratedMessageLite { *; }

# ─── Reflection helpers ──────────────────────────────────────────────────────
-keepattributes SourceFile, LineNumberTable
-renamesourcefileattribute SourceFile
-keepattributes Exceptions, InnerClasses, Signature, Deprecated, EnclosingMethod
