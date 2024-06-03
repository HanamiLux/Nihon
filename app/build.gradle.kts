plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.compilerKsp)
    alias(libs.plugins.googleService)
}

android {
    namespace = "com.example.nihonhistory"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nihonhistory"
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

    packagingOptions {
        resources {
            excludes += "/META-INF/spring.handlers"
            excludes += "META-INF/spring.tooling"
            excludes += "META-INF/spring.schemas"
            excludes += "META-INF/license.txt"
            excludes += "META-INF/notice.txt"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}



dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    //Firebase authentication
    implementation(libs.firebase.auth.ktx)
    //Room
    ksp(libs.androidx.room.ksp)
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)

    //MVVM Architecture
    implementation(libs.androidx.lifecycle.viewmodel)
    //Coroutines
    implementation(libs.kotlinx.coroutines)
    //PDF view
    implementation(libs.pdf.viewer)
    //Circled image views
    implementation(libs.circleImageView)
    //Firebase version control
    implementation(platform(libs.firebase.bom))
    //Bcrypt
    implementation(libs.bcrypt)
    //Yaml support
    implementation(libs.jackson.core)
    implementation(libs.jackson.databind)
    implementation(libs.jackson.module.kotlin)
    implementation(libs.jackson.dataformat.yaml)
    //Gson
    implementation(libs.gson)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}