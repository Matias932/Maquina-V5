plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.maquinav5.cloudgaming"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.maquinav5.cloudgaming"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}
