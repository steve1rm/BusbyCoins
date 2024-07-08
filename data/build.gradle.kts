plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.serialization)
    alias(libs.plugins.realm)
}

android {
    namespace = "me.androidbox.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        /* Retrieves API from local.properties */
        val properties = org.jetbrains.kotlin.konan.properties.Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String", "COIN_RANKING_API_KEY", "${properties.getProperty("COIN_RANKING_API_KEY")}")
        buildConfigField("String", "BASE_COIN_RANKING_ENDPOINT", "\"https://api.coinranking.com/v2\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.timber)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.bundles.ktor)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.ktor)
    implementation(libs.securityCrypto)
    implementation(libs.library.base)
    implementation(libs.coroutines)
    implementation("co.touchlab:kermit:2.0.4")

    testImplementation(libs.truth)
    testImplementation(libs.mockito.kotlin)

    testImplementation(libs.ktor.client.mock)
    testImplementation(libs.coroutinesTest)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}