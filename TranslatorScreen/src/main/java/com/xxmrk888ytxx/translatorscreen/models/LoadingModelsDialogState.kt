package com.xxmrk888ytxx.translatorscreen.models

sealed interface LoadingModelsDialogState {

    object Hidden : LoadingModelsDialogState

    object OfferToDownload : LoadingModelsDialogState

    object Loading : LoadingModelsDialogState

    object Error : LoadingModelsDialogState

}