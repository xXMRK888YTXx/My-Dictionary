package com.xxmrk888ytxx.mydictionary.domain.SupportedLanguagesForTranslateProvider

import android.annotation.SuppressLint
import com.xxmrk888ytxx.mydictionary.R
import javax.inject.Inject

class SupportedLanguagesForTranslateProviderImpl @Inject constructor(

) : SupportedLanguagesForTranslateProvider {

    @SuppressLint("ResourceType")
    override val supportedLanguageForTranslate: List<SupportedLanguageForTranslate> = listOf(
        SupportedLanguageForTranslate("af", R.string.afrikaans),
        SupportedLanguageForTranslate("ar", R.string.arabic),
        SupportedLanguageForTranslate("be", R.string.belarusian),
        SupportedLanguageForTranslate("bg", R.string.bulgarian),
        SupportedLanguageForTranslate("bn", R.string.bengali),
        SupportedLanguageForTranslate("ca", R.string.catalan),
        SupportedLanguageForTranslate("cs", R.string.czech),
        SupportedLanguageForTranslate("cy", R.string.welsh),
        SupportedLanguageForTranslate("da", R.string.danish),
        SupportedLanguageForTranslate("de", R.string.german),
        SupportedLanguageForTranslate("el", R.string.greek),
        SupportedLanguageForTranslate("en", R.string.english),
        SupportedLanguageForTranslate("eo", R.string.esperanto),
        SupportedLanguageForTranslate("es", R.string.spanish),
        SupportedLanguageForTranslate("et", R.string.estonian),
        SupportedLanguageForTranslate("fa", R.string.persian),
        SupportedLanguageForTranslate("fi", R.string.finnish),
        SupportedLanguageForTranslate("fr", R.string.french),
        SupportedLanguageForTranslate("ga", R.string.irish),
        SupportedLanguageForTranslate("gl", R.string.galician),
        SupportedLanguageForTranslate("gu", R.string.gujarati),
        SupportedLanguageForTranslate("he", R.string.hebrew),
        SupportedLanguageForTranslate("hi", R.string.hindi),
        SupportedLanguageForTranslate("hr", R.string.croatian),
        SupportedLanguageForTranslate("ht", R.string.haitian),
        SupportedLanguageForTranslate("hu", R.string.hungarian),
        SupportedLanguageForTranslate("id", R.string.indonesian),
        SupportedLanguageForTranslate("is", R.string.icelandic),
        SupportedLanguageForTranslate("it", R.string.italian),
        SupportedLanguageForTranslate("ja", R.string.japanese),
        SupportedLanguageForTranslate("ka", R.string.georgian),
        SupportedLanguageForTranslate("kn", R.string.kannada),
        SupportedLanguageForTranslate("ko", R.string.korean),
        SupportedLanguageForTranslate("lt", R.string.lithuanian),
        SupportedLanguageForTranslate("lv", R.string.latvian),
        SupportedLanguageForTranslate("mk", R.string.macedonian),
        SupportedLanguageForTranslate("mr", R.string.marathi),
        SupportedLanguageForTranslate("ms", R.string.malay),
        SupportedLanguageForTranslate("mt", R.string.maltese),
        SupportedLanguageForTranslate("nl", R.string.dutch),
        SupportedLanguageForTranslate("no", R.string.norwegian),
        SupportedLanguageForTranslate("pl", R.string.polish),
        SupportedLanguageForTranslate("pt", R.string.portuguese),
        SupportedLanguageForTranslate("ro", R.string.romanian),
        SupportedLanguageForTranslate("ru", R.string.russian),
        SupportedLanguageForTranslate("sk", R.string.slovak),
        SupportedLanguageForTranslate("sl", R.string.slovenian),
        SupportedLanguageForTranslate("sq", R.string.albanian),
        SupportedLanguageForTranslate("sv", R.string.swedish),
        SupportedLanguageForTranslate("sw", R.string.swahili),
        SupportedLanguageForTranslate("ta", R.string.tamil),
        SupportedLanguageForTranslate("te", R.string.telugu),
        SupportedLanguageForTranslate("th", R.string.thai),
        SupportedLanguageForTranslate("tl", R.string.tagalog),
        SupportedLanguageForTranslate("tr", R.string.turkish),
        SupportedLanguageForTranslate("uk", R.string.ukrainian),
        SupportedLanguageForTranslate("ur", R.string.urdu),
        SupportedLanguageForTranslate("vi", R.string.vietnamese),
        SupportedLanguageForTranslate("zh", R.string.chinese),
    )

    override val supportedLanguageForTranslateMap: Map<String,SupportedLanguageForTranslate> by lazy {
        val outputMap = mutableMapOf<String,SupportedLanguageForTranslate>()

        supportedLanguageForTranslate.forEach {
            outputMap[it.code] = it
        }

        outputMap
    }
}