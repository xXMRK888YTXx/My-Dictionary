package com.xxmrk888ytxx.mydictionary.DI.module

import com.xxmrk888ytxx.mydictionary.UseCase.CopyFileUseCase.CopyFileUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.CopyFileUseCase.CopyFileUseCaseImpl
import com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupUseCase.CreateBackupUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.CreateBackupUseCase.CreateBackupUseCaseImpl
import com.xxmrk888ytxx.mydictionary.UseCase.FileWritterUseCase.FileWriterUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.FileWritterUseCase.FileWriterUseCaseImpl
import com.xxmrk888ytxx.mydictionary.UseCase.GenerateQuestionForTrainingUseCase.GenerateQuestionForTrainingUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.GenerateQuestionForTrainingUseCase.GenerateQuestionForTrainingUseCaseImpl
import com.xxmrk888ytxx.mydictionary.UseCase.OpenEmailAppForWriteDeveloperUseCase.OpenEmailAppForWriteDeveloperUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.OpenEmailAppForWriteDeveloperUseCase.OpenEmailAppForWriteDeveloperUseCaseImpl
import com.xxmrk888ytxx.mydictionary.UseCase.OpenPrivacyPolicyUseCase.OpenPrivacyPolicyUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.OpenPrivacyPolicyUseCase.OpenPrivacyPolicyUseCaseImpl
import com.xxmrk888ytxx.mydictionary.UseCase.OpenSourceCodeUseCase.OpenSourceCodeUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.OpenSourceCodeUseCase.OpenSourceCodeUseCaseImpl
import com.xxmrk888ytxx.mydictionary.UseCase.OpenTermsOfUseUseCase.OpenTermsOfUseUseCase
import com.xxmrk888ytxx.mydictionary.UseCase.OpenTermsOfUseUseCase.OpenTermsOfUseUseCaseImpl
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
        textToSpeechUseCaseImpl: TextToSpeechUseCaseImpl
    ) : TextToSpeechUseCase

    @Binds
    fun bindGenerateQuestionForTrainingUseCase(
        generateQuestionForTrainingUseCaseImpl: GenerateQuestionForTrainingUseCaseImpl
    ) : GenerateQuestionForTrainingUseCase

    @Binds
    fun bindProvideWordGroupsForTrainingUseCase(
        provideWordGroupsForTrainingUseCaseImpl: ProvideWordGroupsForTrainingUseCaseImpl
    ) : ProvideWordGroupsForTrainingUseCase

    @Binds
    fun bindCreateBackupUseCase(
        createBackupUseCaseImpl: CreateBackupUseCaseImpl
    ) : CreateBackupUseCase

    @Binds
    fun bindFileWriterUseCase(
        fileWriterUseCaseImpl: FileWriterUseCaseImpl
    ) : FileWriterUseCase

    @Binds
    fun bindCopyFileUseCase(
        copyFileUseCaseImpl: CopyFileUseCaseImpl
    ) : CopyFileUseCase

    @Binds
    fun bindRestoreBackupUseCase(
        restoreBackupUseCaseImpl: RestoreBackupUseCaseImpl
    ) : RestoreBackupUseCase

    @Binds
    fun bindReadFileUseCase(
        readFileUseCaseImpl: ReadFileUseCaseImpl
    ) : ReadFileUseCase

    @Binds
    fun bindOpenPrivacyPolicyUseCase(
        openPrivacyPolicyUseCaseImpl:OpenPrivacyPolicyUseCaseImpl
    ) : OpenPrivacyPolicyUseCase

    @Binds
    fun bindOpenTermsOfUseUseCase(
        openTermsOfUseUseCaseImpl: OpenTermsOfUseUseCaseImpl
    ) : OpenTermsOfUseUseCase

    @Binds
    fun bindOpenSourceCodeUseCase(
        openSourceCodeUseCaseImpl: OpenSourceCodeUseCaseImpl
    ) : OpenSourceCodeUseCase

    @Binds
    fun bindOpenEmailAppForWriteDeveloperUseCase(
        openEmailAppForWriteDeveloperUseCaseImpl: OpenEmailAppForWriteDeveloperUseCaseImpl
    ) : OpenEmailAppForWriteDeveloperUseCase
}