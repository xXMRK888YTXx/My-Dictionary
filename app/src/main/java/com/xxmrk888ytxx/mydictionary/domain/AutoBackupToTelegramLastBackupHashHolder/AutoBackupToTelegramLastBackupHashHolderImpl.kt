package com.xxmrk888ytxx.mydictionary.domain.AutoBackupToTelegramLastBackupHashHolder

import androidx.datastore.preferences.core.stringPreferencesKey
import com.xxmrk888ytxx.cryptomanager.CryptoManager
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.LanguageRepository.LanguageRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordGroupRepository.WordGroupRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordPhrasesRepository.WordPhrasesRepository
import com.xxmrk888ytxx.mydictionary.domain.Repositoryes.WordRepository.WordRepository
import com.xxmrk888ytxx.preferencesstorage.PreferencesStorage
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AutoBackupToTelegramLastBackupHashHolderImpl @Inject constructor(
    private val preferencesStorage: PreferencesStorage,
    private val cryptoManager: CryptoManager,
    private val languageRepository: LanguageRepository,
    private val wordRepository: WordRepository,
    private val phrasesRepository: WordPhrasesRepository,
    private val wordGroupRepository: WordGroupRepository
) : AutoBackupToTelegramLastBackupHashHolder {

    private val telegramAutoBackupLastBackupHashKey = stringPreferencesKey("telegramAutoBackupLastBackupHashKey")

    private suspend fun calculateHash() : String {
        val finalString = StringBuffer()

        languageRepository.languageFlow.first().forEach {
            finalString.append(it.id)
            finalString.append(it.name)
        }

        wordGroupRepository.wordGroupsFlow.first().forEach {
            finalString.append(it.id)
            finalString.append(it.name)
            finalString.append(it.imageUrl)
            finalString.append(it.primaryLanguageId)
            finalString.append(it.secondaryLanguageId)
        }

        wordRepository.getWords().first().forEach {
            finalString.append(it.id)
            finalString.append(it.transcriptionText)
            finalString.append(it.translateText)
            finalString.append(it.wordGroupId)
            finalString.append(it.wordText)
        }

        phrasesRepository.wordPhrasesFlowModel.first().forEach {
            finalString.append(it.id)
            finalString.append(it.phraseText)
            finalString.append(it.translateText)
            finalString.append(it.wordId)
        }

        if(finalString.isEmpty()) return ""

        return cryptoManager.hashFromData(finalString.toString().toByteArray())
    }

    override suspend fun updateHash() {
        preferencesStorage.writeProperty(telegramAutoBackupLastBackupHashKey,calculateHash())
    }

    override suspend fun isHaveChanges() : Boolean {
        val hash = calculateHash()

        if(hash.isEmpty()) throw ApplicationDataIsEmptyException()

        return preferencesStorage.getPropertyOrNull(telegramAutoBackupLastBackupHashKey).first() != hash
    }

    override suspend fun reset() {
        preferencesStorage.removeProperty(telegramAutoBackupLastBackupHashKey)
    }
}