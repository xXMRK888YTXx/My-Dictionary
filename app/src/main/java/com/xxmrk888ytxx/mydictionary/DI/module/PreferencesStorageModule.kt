package com.xxmrk888ytxx.mydictionary.DI.module

import android.content.Context
import com.xxmrk888ytxx.cryptomanager.CryptoManager
import com.xxmrk888ytxx.mydictionary.DI.scope.AppScope
import com.xxmrk888ytxx.preferencesstorage.Cipher
import com.xxmrk888ytxx.preferencesstorage.EncryptedPreferencesStorage
import com.xxmrk888ytxx.preferencesstorage.PreferencesStorage
import dagger.Module
import dagger.Provides

@Module
class PreferencesStorageModule {

    companion object {

        @Provides
        @AppScope
        fun providesPreferencesStorage(context: Context) : PreferencesStorage {
            return PreferencesStorage.Factory().create("PreferencesStorage",context)
        }

        @Provides
        @AppScope
        fun providesEncryptedPreferencesStorage(
            preferencesStorage: PreferencesStorage,
            cryptoManager: CryptoManager
        ) : EncryptedPreferencesStorage {

            val cipher = object : Cipher {
                override fun encrypt(byteArray: ByteArray): ByteArray {
                    return cryptoManager.encryptData(byteArray)
                }

                override fun decrypt(byteArray: ByteArray): ByteArray {
                    return cryptoManager.decryptData(byteArray)
                }

            }


            return EncryptedPreferencesStorage.create(
                preferencesStorage = preferencesStorage,
                cipher = cipher
            )
        }
    }
}