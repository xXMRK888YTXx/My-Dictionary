package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.UseCase.CopyFileUseCase.CopyFileUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.CopyFileUseCase.CopyFileUseCaseImpl
import com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupUseCase.CreateBackupUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupUseCase.CreateBackupUseCaseImpl
import com.xxmrk888ytxx.mydictionary.UseCase.FileWritterUseCase.FileWriterUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.FileWritterUseCase.FileWriterUseCaseImpl
import com.xxmrk888ytxx.mydictionary.UseCase.GenerateQuestionForTrainingUseCase.GenerateQuestionForTrainingUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.GenerateQuestionForTrainingUseCase.GenerateQuestionForTrainingUseCaseImpl
import com.xxmrk888ytxx.mydictionary.UseCase.ProvideWordGroupsForTrainingUseCase.ProvideWordGroupsForTrainingUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.ProvideWordGroupsForTrainingUseCase.ProvideWordGroupsForTrainingUseCaseImpl
import com.xxmrk888ytxx.mydictionary.UseCase.ReadFileUseCase.ReadFileUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.ReadFileUseCase.ReadFileUseCaseImpl
import com.xxmrk888ytxx.mydictionary.UseCase.RestoreBackupUseCase.RestoreBackupUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.RestoreBackupUseCase.RestoreBackupUseCaseImpl
import com.xxmrk888ytxx.mydictionary.UseCase.TextToSpeechUseCase.TextToSpeechUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.TextToSpeechUseCase.TextToSpeechUseCaseImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseModule {

    @Binds
    fun bindTextToSpeechUseCase(
        TextToSpeechUseCaseImpl: TextToSpeechUseCaseImpl
    ) : TextToSpeechUseCase

    @Binds
    fun bindGenerateQuestionForTrainingUseCase(
        GenerateQuestionForTrainingUseCaseImpl: GenerateQuestionForTrainingUseCaseImpl
    ) : GenerateQuestionForTrainingUseCase

    @Binds
    fun bindProvideWordGroupsForTrainingUseCase(
        provideWordGroupsForTrainingUseCaseImpl: ProvideWordGroupsForTrainingUseCaseImpl
    ) : ProvideWordGroupsForTrainingUseCase

    @Binds
    fun bindCreateBackupUseCase(
        CreateBackupUseCaseImpl: CreateBackupUseCaseImpl
    ) : CreateBackupUseCase

    @Binds
    fun bindFileWriterUseCase(
        FileWriterUseCaseImpl: FileWriterUseCaseImpl
    ) : FileWriterUseCase

    @Binds
    fun bindCopyFileUseCase(
        CopyFileUseCaseImpl: CopyFileUseCaseImpl
    ) : CopyFileUseCase

    @Binds
    fun bindRestoreBackupUseCase(
        RestoreBackupUseCaseImpl: RestoreBackupUseCaseImpl
    ) : RestoreBackupUseCase

    @Binds
    fun bindReadFileUseCase(
        ReadFileUseCaseImpl: ReadFileUseCaseImpl
    ) : ReadFileUseCase
}