package com.miczon.datastorepreferences

import android.app.Application

class MyApplication : Application() {

    companion object {

        lateinit var instance: MyApplication

        suspend fun <T> saveData(key: String, value: T) = instance.dataStoreManager.putDataToDataStore(key, value)

        suspend fun <T> retrieveData(key: String, default: T) = instance.dataStoreManager.getDataFromDataStore(key, default)

        suspend fun clearDataStore() = instance.dataStoreManager.clearDataStorePreferences()
    }

    // Create DataStoreManager instance in the Application class
    val dataStoreManager by lazy { DataStoreManager(this) }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}