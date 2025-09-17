plugins {
    // Android Application 플러그인 적용
    id("com.android.application")
    // Kotlin Android 플러그인을 쓰지 않는다면 kotlin("android") 는 제거합니다.
    // 만약 버전 카탈로그를 쓰고 계시다면 아래처럼 alias() 대신 직접 id()를 쓰는 편이 문제를 줄일 수 있습니다.
}

android {
    namespace = "com.example.mobiledoctor"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mobiledoctor"
        minSdk = 34
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        // Manifest 에 키 주입
        manifestPlaceholders["MAPS_API_KEY"]   = project.property("MAPS_API_KEY").toString()
        manifestPlaceholders["PLACES_API_KEY"] = project.property("PLACES_API_KEY").toString()
        manifestPlaceholders["PLACES_API_KEY"] = project.findProperty("PLACES_API_KEY") ?: ""

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
    // AndroidX / Material
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // 머티리얼 컴포넌트
    implementation("com.google.android.material:material:1.9.0")

    // Naver 파싱
    implementation("org.jsoup:jsoup:1.15.3")

    // Google Maps & Location
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.android.libraries.places:places:3.1.0")

    // 네트워크 요청
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    // Retrofit 사용 시
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // 기타
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("commons-io:commons-io:2.4")
    implementation(libs.coordinatorlayout)

    // 테스트
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
