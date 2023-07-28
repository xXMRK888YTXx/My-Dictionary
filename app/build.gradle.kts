plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id (Deps.Dagger.DaggerKaptPlugin)
}

android {
    namespace = "com.xxmrk888ytxx.mydictionary"
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = "com.xxmrk888ytxx.mydictionary"
        minSdk = Config.minSdk
        targetSdk = Config.compileSdk
        versionCode = 1
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

        release {
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
    implementation(project(Project.LanguageIndicator))
    implementation(project(Project.WordByEarTrainingScreen))
    implementation(project(Project.CreateBackupScreen))
    implementation(project(Project.ArchiverCreator))
    implementation(project(Project.BackupConverter))
    implementation(project(Project.RestoreBackupScreen))
    implementation(project(Project.ManageLanguageScreen))
    implementation(project(Project.FeatureViewScreen))


    kapt(Deps.Dagger.DaggerKaptCompiler)

    //Navigation
    implementation(Deps.Compose.Navigation)

}