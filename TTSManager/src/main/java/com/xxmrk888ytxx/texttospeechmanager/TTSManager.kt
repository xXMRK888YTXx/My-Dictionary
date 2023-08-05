package com.xxmrk888ytxx.texttospeechmanager

import android.content.Context

/**
 * [Ru]
 * Интерфейс для воспроизведения текста с помощью синтезатора речи
 */

/**
 * [En]
 * Interface for text playback using a speech synthesizer
 */
interface TTSManager {


    /**
     * [Ru]
     * Выполняет инициализацию [TTSManager].
     * Этот метод должен быть вызван перед использованием функции [speck].
     */

    /**
     * [En]
     * Performs initialization of [TTSManager].
     * This method must be called before using the [speck] function.
     */
    fun init()

    /**
     * [Ru]
     *  Метод для воспроизведения текста
     *
     *@param text - текст для воспроизведения
     *@param utteranceId - идентификатор запроса на воспроизведение текста
     */

    /**
     * [En]
     * Method for Text playback
     *
     * @param text - text for playback
     * @param utteranceId - ID of the text playback request
     */

    fun speck(
        text:String,
        utteranceId:String = "Default",
        languageCode:String? = null
    ) : Result<Unit>

    companion object {
        fun create(context: Context,onInitSuccessful:() -> Unit = {}, onInitFailed: () -> Unit = {}) : TTSManager {
            return TTSManagerImpl(context, onInitSuccessful, onInitFailed)
        }
    }
}