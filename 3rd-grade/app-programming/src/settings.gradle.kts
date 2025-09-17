pluginManagement {
    repositories {
        // 1) Gradle Plugin Portal – com.android.application, org.jetbrains.kotlin.android 등 플러그인 해석용
        gradlePluginPortal()

        // 2) Google Maven – Android Gradle Plugin, Play-Services 등
        google()

        // 3) Maven Central – 추가적인 플러그인·라이브러리
        mavenCentral()
    }
}
dependencyResolutionManagement {
    // “프로젝트 레벨(settings)에 선언된 저장소만 쓰고,
    // 모듈 레벨 build.gradle.kts 의 repositories {…} 는 무시” 모드
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        google()       // Play-Services, Maps, AndroidX 등
        mavenCentral() // 그 외 라이브러리
    }
}

rootProject.name = "MobileDoctor"
include(":app")
