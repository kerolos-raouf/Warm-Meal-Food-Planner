plugins {
    alias(libs.plugins.android.application)
    id("androidx.navigation.safeargs")
    alias(libs.plugins.google.gms.google.services)

}

android {
    namespace = "com.example.warmmeal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.warmmeal"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.auth)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    implementation(libs.lottie)

    // RXJava Dependency
    implementation(libs.rxjava)
    implementation(libs.rxandroid)


//retrofit and gson converter
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.adapter.rxjava3)

    ///glide dependeny for images
    implementation(libs.glide)

    ///room database
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    // optional - RxJava3 support for Room
    implementation(libs.room.rxjava3)



}