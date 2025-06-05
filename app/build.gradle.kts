plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.jiyulearning"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.jiyulearning"
        minSdk = 31
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:4.11.0")
    testImplementation("org.robolectric:robolectric:4.10")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.6.1")
    // Room框架
    implementation("androidx.room:room-runtime:2.7.1")
    // RecyclerView支持
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    // 如果使用Material Design组件（可选）
    implementation("com.google.android.material:material:1.11.0")
    // OkHttp 4.x
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    // Gson 2.x
    implementation("com.google.code.gson:gson:2.10.1")

    // AndroidX注解支持
    implementation("androidx.annotation:annotation:1.7.1")

    // JSON官方库（部分Android系统已内置）
    implementation("org.json:json:20231013")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")// 必须使用4.x版本
    implementation("org.json:json:20231013")// Android默认库可能版本过低


}