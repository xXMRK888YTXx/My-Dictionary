package com.xxmrk888ytxx.addwordscreen.models

import com.xxmrk888ytxx.coreandroid.ShareInterfaces.Logger
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class PhrasesHolder(
    private val logger: Logger,
) {

    private val phrasesMap: MutableMap<Int, PhrasesModel> = mutableMapOf()

    private var lastId: Int = 0

    fun addPhrases(realId: Int) {
        phrasesMap[lastId] = PhrasesModel(realId, lastId, "", "")

        lastId += 1

        updatePhrasesFlow()
    }

    fun removePhrases(localId: Int) {
        phrasesMap.remove(localId)

        updatePhrasesFlow()
    }

    private val _phrasesFlow: MutableStateFlow<ImmutableList<PhrasesModel>> = MutableStateFlow(
        persistentListOf()
    )

    val phrasesModel = _phrasesFlow.asStateFlow().onStart {
        updatePhrasesFlow()
    }

    fun updatePhrases(localId: Int, onUpdate: (PhrasesModel) -> PhrasesModel) {
        try {
            phrasesMap[localId] = onUpdate(phrasesMap[localId]!!)
            updatePhrasesFlow()
        } catch (e: NullPointerException) {
            logger.error(e, LOG_TAG)
        }
    }

    private fun updatePhrasesFlow() {
        _phrasesFlow.update {
            phrasesMap.map { it.value }.toImmutableList()
        }
    }

    companion object {
        private const val LOG_TAG = "PhrasesHolder"
    }
}