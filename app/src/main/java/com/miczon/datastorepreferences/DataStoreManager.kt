package com.miczon.datastorepreferences

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class DataStoreManager(context: Context) {

    companion object {
        private val Context.dataStore by preferencesDataStore(name = "settings")
    }

    private val dataStore = context.dataStore

    suspend fun <T> putDataToDataStore(key: String, value: T) {
        dataStore.edit { preferences ->
            when (value) {
                is String -> preferences[stringPreferencesKey(key)] = value as String
                is Int -> preferences[intPreferencesKey(key)] = value
                is Boolean -> preferences[booleanPreferencesKey(key)] = value
                is Long -> preferences[longPreferencesKey(key)] = value
                is Double -> preferences[doublePreferencesKey(key)] = value
                is Float -> preferences[floatPreferencesKey(key)] = value
                else -> throw IllegalArgumentException("Unsupported data type")
            }
        }
    }

    suspend fun <T> getDataFromDataStore(key: String, defaultValue: T): Any? {
        return runBlocking {
            dataStore.data.map { preferences ->
                preferences.asMap().forEach { (preferenceKey, preferenceValue) ->
                    Log.e("waqar", "Preference: $preferenceKey -> $preferenceValue")
                }

                when (defaultValue) {
                    is String -> preferences[stringPreferencesKey(key)] ?: defaultValue
                    is Int -> preferences[intPreferencesKey(key)] ?: defaultValue
                    is Boolean -> preferences[booleanPreferencesKey(key)] ?: defaultValue
                    is Long -> preferences[longPreferencesKey(key)] ?: defaultValue
                    is Double -> preferences[doublePreferencesKey(key)] ?: defaultValue
                    is Float -> preferences[floatPreferencesKey(key)] ?: defaultValue
                    else -> defaultValue
                }
            }.first()
        }
    }


//    suspend fun <T> getDataFromDataStore(key: String, defaultValue: T): Any? {
//        return dataStore.data.map { preferences ->
//
//            preferences.asMap().forEach { (preferenceKey, preferenceValue) ->
//                Log.e("waqar", "Preference: $preferenceKey -> $preferenceValue")
//            }
//
//            when (defaultValue) {
//                is String -> preferences[stringPreferencesKey(key)] ?: defaultValue
//                is Int -> preferences[intPreferencesKey(key)] ?: defaultValue
//                is Boolean -> preferences[booleanPreferencesKey(key)] ?: defaultValue
//                is Long -> preferences[longPreferencesKey(key)] ?: defaultValue
//                is Double -> preferences[doublePreferencesKey(key)] ?: defaultValue
//                is Float -> preferences[floatPreferencesKey(key)] ?: defaultValue
//                else -> defaultValue
//            }
//        }.singleOrNull() ?: defaultValue
//    }

    suspend fun clearDataStorePreferences() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

}

