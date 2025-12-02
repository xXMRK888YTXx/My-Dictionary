plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.xxmrk888ytxx.cryptomanager"
    compileSdk = libs.versions.targetSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.valueOf(libs.versions.javaCompatibilityVersion.get())
        targetCompatibility = JavaVersion.valueOf(libs.versions.javaCompatibilityVersion.get())
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    implementation(project(":CoreAndroid"))

    //Instrumental Test
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.instrumental.test.runner)
    androidTestImplementation(libs.instrumental.test.core)
    androidTestImplementation(libs.instrumental.test.junit)
    androidTestImplementation(libs.instrumental.test.rules)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.mockk.agent)
}
