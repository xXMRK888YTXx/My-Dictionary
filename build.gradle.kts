// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(Deps.KotlinSerialization.classPath)
    }
}
plugins {
    id("com.google.gms.google-services") version "4.3.15" apply false
    id("com.google.firebase.crashlytics") version "2.9.7" apply false
    id ("com.android.application") version "8.1.0" apply false
    id ("com.android.library") version "8.1.0" apply false
    id ("org.jetbrains.kotlin.android") version Deps.KotlinVersion apply false
}