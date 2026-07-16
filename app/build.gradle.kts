import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.filmengine"

    compileSdk {
        version = release(36)
    }

    defaultConfig {

        applicationId = "com.example.filmengine"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"


        // API anahtarını local.properties dosyasından alır.
        // Böylece hassas bilgiler direkt kod içinde tutulmaz.
        val localProperties = Properties()
        val localPropertiesFile = rootProject.file("local.properties")

        if (localPropertiesFile.exists()) {
            localProperties.load(FileInputStream(localPropertiesFile))
        }


        // API bağlantısı için gerekli bilgileri uygulamaya aktarır.
        buildConfigField(
            "String",
            "TMDB_API_KEY",
            "\"${localProperties.getProperty("TMDB_API_KEY")}\""
        )

        buildConfigField(
            "String",
            "TMDB_BASE_URL",
            "\"https://api.themoviedb.org/3/\""
        )

        // Film görsellerinin temel adresi.
        buildConfigField(
            "String",
            "TMDB_IMAGE_BASE_URL",
            "\"https://image.tmdb.org/t/p/w500\""
        )
    }


    // Uygulamanın yayınlama ayarları.
    buildTypes {
        release {
            isMinifyEnabled = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }


    // Java ve Kotlin sürüm ayarları.
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }


    // Jetpack Compose kullanımını aktif eder.
    buildFeatures {
        compose = true   //burayı değiştrdim compose için

        // BuildConfig sınıfının oluşturulmasını sağlar.
        buildConfig = true
    }
}


dependencies {

    // Temel Android kütüphaneleri
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)


    // ViewModel ve Lifecycle yönetimi için kullanılır.
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)


    // Ekranlar arası geçiş işlemleri için kullanılır.
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)


    // API bağlantısı için Retrofit kullanılır.
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)


    // Büyük listeleri sayfalı şekilde yüklemek için kullanılır.
    implementation(libs.androidx.paging.runtime.ktx)


    // Yerel veritabanı işlemleri için Room kullanılır.
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)
    ksp(libs.androidx.room.compiler)


    // Asenkron işlemleri yönetmek için kullanılır.
    implementation(libs.kotlinx.coroutines.android)


    // İnternetten görsel yüklemek için kullanılır.
    implementation(libs.coil)


    // Dependency Injection işlemleri için Hilt kullanılır.
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)



    // Compose kütüphanelerinin uyumlu versiyonlarını yönetir.
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)


    // Compose ekranlarını Activity içinde göstermek için kullanılır.
    implementation(libs.androidx.activity.compose)
    // Compose ekranları arasında geçiş yapmak için kullanılır.
    implementation(libs.androidx.navigation.compose)
    // Paging3 ile Compose listelerini kullanmayı sağlar.
    implementation(libs.androidx.paging.compose)
    // Compose içinde ViewModel kullanımını sağlar.
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    // Compose içinde internetten resim göstermek için kullanılır.
    implementation(libs.coil.compose)


    implementation(libs.androidx.hilt.navigation.compose)
}