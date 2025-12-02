pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "MyDictionary"
include(":app")
include(":CoreAndroid")
include(":CoreCompose")
include(":WordGroupScreen")
include(":CreateWordGroupScreen")
include(":Database")
include(":ViewGroupWordsScreen")
include(":EditWordScreen")
include(":TTSManager")
include(":BottomBarScreen")
include(":TrainingActionsScreen")
include(":WordTranslateTrainingScreen")
include(":SettingsScreen")
include(":LanguageDeterminant")
include(":WordByEarTrainingScreen")
include(":BaseTrainingComponents")
include(":CreateBackupScreen")
include(":ArchiverCreator")
include(":BackupConverter")
include("RestoreBackupScreen")
include(":ManageLanguageScreen")
include(":FeatureViewScreen")
include(":PreferencesStorage")
include(":AutoBackupToTelegramScreen")
include(":TelegramApi")
include(":CryptoManager")
include(":BackupWorker")
include(":TranslatorScreen")
include(":Translator")
include(":ManageModelsForTranslateScreen")
