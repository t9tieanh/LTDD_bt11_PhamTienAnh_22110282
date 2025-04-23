plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.firebase"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.firebase"
        minSdk = 24
        targetSdk = 35
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
}

dependencies {
    // Cơ bản
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")

    //cloudinary
    implementation ("com.cloudinary:cloudinary-android:3.0.2")



    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.google.android.material:material:1.8.0")
    // Google Play Services cho đăng nhập Google
    implementation("com.google.android.gms:play-services-auth:20.4.1")

    // Firebase BOM (phải đứng trước các Firebase dependencies)
    implementation(platform("com.google.firebase:firebase-bom:31.2.3"))

    // Media3 ExoPlayer cho phát video
    implementation("androidx.media3:media3-exoplayer:1.2.1")
    implementation("androidx.media3:media3-ui:1.2.1")

    // Firebase dependencies
    implementation("com.google.firebase:firebase-auth:21.0.1")
    implementation("com.google.firebase:firebase-database:20.0.5")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-storage")

    // Firebase UI cho database
    implementation("com.firebaseui:firebase-ui-database:8.0.1")

    // Retrofit - Mạng
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    // Gson - Chuyển đổi giữa đối tượng Java và JSON
    implementation ("com.google.code.gson:gson:2.10.1")
    // https://mvnrepository.com/artifact/com.pierfrancescosoffritti.androidyoutubeplayer/core
    implementation("com.pierfrancescosoffritti.androidyoutubeplayer:core:12.1.1")
}

