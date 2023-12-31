import org.gradle.api.JavaVersion

object Config {
    const val compileSdk = 33
    const val minSdk = 23
    const val packageName = "com.xxmrk888ytxx.goals"
    const val isR8ProGuardEnableForRelease = true
    const val isR8ProGuardEnableForDebug = false
    const val versionName = "1.2.0"

    //JDK Config
    val sourceCompatibility = JavaVersion.VERSION_17
    val targetCompatibility = JavaVersion.VERSION_17
    const val jvmTarget = "17"
}