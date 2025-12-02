plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = libs.versions.packageName.get()
    compileSdk = libs.versions.targetSdk.get().toInt()

    defaultConfig {
        applicationId = libs.versions.packageName.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 9
        versionName = "1.2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = libs.versions.minifyEnabledRelease.get().toBoolean()
            isShrinkResources = libs.versions.minifyEnabledRelease.get().toBoolean()
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            isMinifyEnabled = libs.versions.minifyEnabledDebug.get().toBoolean()
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
    buildFeatures {
        buildConfig = true
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":CoreCompose"))
    implementation(project(":WordGroupScreen"))
    implementation(project(":CreateWordGroupScreen"))
    implementation(project(":Database"))
    implementation(project(":ViewGroupWordsScreen"))
    implementation(project(":EditWordScreen"))
    implementation(project(":TTSManager"))
    implementation(project(":BottomBarScreen"))
    implementation(project(":TrainingActionsScreen"))
    implementation(project(":WordTranslateTrainingScreen"))
    implementation(project(":SettingsScreen"))
    implementation(project(":LanguageDeterminant"))
    implementation(project(":WordByEarTrainingScreen"))
    implementation(project(":CreateBackupScreen"))
    implementation(project(":ArchiverCreator"))
    implementation(project(":BackupConverter"))
    implementation(project(":RestoreBackupScreen"))
    implementation(project(":ManageLanguageScreen"))
    implementation(project(":FeatureViewScreen"))
    implementation(project(":PreferencesStorage"))
    implementation(project(":AutoBackupToTelegramScreen"))
    implementation(project(":TelegramApi"))
    implementation(project(":CryptoManager"))
    implementation(project(":BackupWorker"))
    implementation(project(":TranslatorScreen"))
    implementation(project(":Translator"))
    implementation(project(":ManageModelsForTranslateScreen"))

    ksp(libs.dagger.compiler)

    //Navigation
    implementation(libs.androidx.navigation.compose)

    //Firebase
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.analytics.ktx)
}
