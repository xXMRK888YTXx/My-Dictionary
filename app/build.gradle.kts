plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id (Deps.Dagger.DaggerKaptPlugin)
    id (Deps.GoogleServices.gmsServicePlugin)
    id (Deps.Firebase.crashlyticsPlugin)
}

android {
    namespace = "com.xxmrk888ytxx.mydictionary"
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = "com.xxmrk888ytxx.mydictionary"
        minSdk = Config.minSdk
        targetSdk = Config.compileSdk
        versionCode = 7
        versionName = Config.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = Config.isR8ProGuardEnableForRelease
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }

        debug {
            isMinifyEnabled = Config.isR8ProGuardEnableForDebug
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = Config.sourceCompatibility
        targetCompatibility = Config.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Deps.Compose.ComposeKotlinCompiler
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(Project.CoreCompose))
    implementation(project(Project.WordGroupScreen))
    implementation(project(Project.CreateWordGroupScreen))
    implementation(project(Project.Database))
    implementation(project(Project.ViewGroupWordsScreen))
    implementation(project(Project.EditWordScreen))
    implementation(project(Project.TTSManager))
    implementation(project(Project.BottomBarScreen))
    implementation(project(Project.TrainingActionsScreen))
    implementation(project(Project.WordTranslateTrainingScreen))
    implementation(project(Project.SettingsScreen))
    implementation(project(Project.LanguageDeterminant))
    implementation(project(Project.WordByEarTrainingScreen))
    implementation(project(Project.CreateBackupScreen))
    implementation(project(Project.ArchiverCreator))
    implementation(project(Project.BackupConverter))
    implementation(project(Project.RestoreBackupScreen))
    implementation(project(Project.ManageLanguageScreen))
    implementation(project(Project.FeatureViewScreen))
    implementation(project(Project.PreferencesStorage))
    implementation(project(Project.AdmobManager))
    implementation(project(Project.AutoBackupToTelegramScreen))
    implementation(project(Project.TelegramApi))
    implementation(project(Project.CryptoManager))
    implementation(project(Project.BackupWorker))

    kapt(Deps.Dagger.DaggerKaptCompiler)

    //Navigation
    implementation(Deps.Compose.Navigation)

    //Firebase
    implementation(platform(Deps.Firebase.FirebaseBom))
    implementation(Deps.Firebase.analytics)
    implementation(Deps.Firebase.crashlytics)

    //Billing
    implementation(Deps.Billing.billing)

}