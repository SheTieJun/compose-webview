import androidx.annotation.GuardedBy
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import java.io.File
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
object DataStoreKit {
    private val lock = Any()

    @GuardedBy("lock")
    @Volatile
    private var INSTANCE: DataStore<Preferences>? = null


    private fun getValue(): DataStore<Preferences> {
        return INSTANCE ?: synchronized(lock) {
            if (INSTANCE == null) {
                INSTANCE = PreferenceDataStoreFactory.create {
                    File(fileName)
                }
            }
            INSTANCE!!
        }
    }

    init {
        println("create DataStoreKit ${Thread.currentThread().id}")
    }

    val fileName = "dataStoreWeb.preferences_pb"

    val dataStore by lazy { getValue() }

    suspend inline fun <reified T : Any> save(key: String, value: T): Boolean {
        try {
            dataStore.edit {
                when (T::class) {
                    Int::class -> {
                        it[intPreferencesKey(key)] = value as Int
                    }
                    Double::class -> {
                        it[doublePreferencesKey(key)] = value as Double
                    }
                    String::class -> {
                        it[stringPreferencesKey(key)] = value as String
                    }
                    Boolean::class -> {
                        it[booleanPreferencesKey(key)] = value as Boolean
                    }
                    Float::class -> {
                        it[floatPreferencesKey(key)] = value as Float
                    }
                    Long::class -> {
                        it[longPreferencesKey(key)] = value as Long
                    }
                    Set::class -> {
                        it[stringSetPreferencesKey(key)] = value as Set<String>
                    }
                    else -> {
                        throw IllegalArgumentException(" Can't handle 'value' ")
                    }
                }
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * Get Flow by Preferences.Key
     *
     * @param T
     * @param key
     * @return
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Any> get(key: String, defaultValue: T): Flow<T> {
        val data = dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            when (T::class) {
                Int::class -> {
                    it[intPreferencesKey(key)]
                }
                Double::class -> {
                    it[doublePreferencesKey(key)]
                }
                String::class -> {
                    it[stringPreferencesKey(key)]
                }
                Boolean::class -> {
                    it[booleanPreferencesKey(key)]
                }
                Float::class -> {
                    it[floatPreferencesKey(key)]
                }
                Long::class -> {
                    it[longPreferencesKey(key)]
                }
                Set::class -> {
                    it[stringSetPreferencesKey(key)]
                }
                else -> {
                    null
                }
            } ?: defaultValue
        }
        return data as Flow<T>
    }

    /**
     * Get Flow by Preferences.Key
     *
     * @param T
     * @param key
     * @return
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Any> get(key: String): Flow<T?> {
        val data = dataStore.data.catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }.map {
            when (T::class) {
                Int::class -> {
                    it[intPreferencesKey(key)]
                }
                Double::class -> {
                    it[doublePreferencesKey(key)]
                }
                String::class -> {
                    it[stringPreferencesKey(key)]
                }
                Boolean::class -> {
                    it[booleanPreferencesKey(key)]
                }
                Float::class -> {
                    it[floatPreferencesKey(key)]
                }
                Long::class -> {
                    it[longPreferencesKey(key)]
                }
                Set::class -> {
                    it[stringSetPreferencesKey(key)]
                }
                else -> {
                    null
                }
            }
        }
        return data as Flow<T?>
    }

    suspend inline fun <reified T : Any> getFirst(key: String, defaultValue: T): T {
        var resultValue = defaultValue
        dataStore.data.first {
            resultValue = (
                    when (T::class) {
                        Int::class -> {
                            it[intPreferencesKey(key)]
                        }
                        Double::class -> {
                            it[doublePreferencesKey(key)]
                        }
                        String::class -> {
                            it[stringPreferencesKey(key)]
                        }
                        Boolean::class -> {
                            it[booleanPreferencesKey(key)]
                        }
                        Float::class -> {
                            it[floatPreferencesKey(key)]
                        }
                        Long::class -> {
                            it[longPreferencesKey(key)]
                        }
                        Set::class -> {
                            it[stringSetPreferencesKey(key)]
                        }
                        else -> {
                            null
                        }
                    } ?: defaultValue
                    ) as T
            true
        }
        return resultValue
    }

    /**
     * Remove 移除单个key的值
     *
     * @param T
     * @param key
     * @return
     */
    suspend inline fun <reified T : Any> remove(key: String): Boolean {
        try {
            dataStore.edit {
                when (T::class) {
                    Int::class -> {
                        it.remove(intPreferencesKey(key))
                    }
                    Double::class -> {
                        it.remove(doublePreferencesKey(key))
                    }
                    String::class -> {
                        it.remove(stringPreferencesKey(key))
                    }
                    Boolean::class -> {
                        it.remove(booleanPreferencesKey(key))
                    }
                    Float::class -> {
                        it.remove(floatPreferencesKey(key))
                    }
                    Long::class -> {
                        it.remove(longPreferencesKey(key))
                    }
                    Set::class -> {
                        it.remove(stringSetPreferencesKey(key))
                    }
                    else -> {
                        throw IllegalArgumentException(" Can't remove 'key' ")
                    }
                }
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * Clear 清空
     *
     * @return
     */
    suspend fun clear(): Boolean {
        try {
            dataStore.edit {
                it.clear()
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun clearBlock(): Boolean {
        return runBlocking(Dispatchers.IO) {
            try {
                dataStore.edit {
                    it.clear()
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    /**
     * Get first sync
     * 同步方法获取
     * @param T
     * @param key
     * @param defaultValue 默认值
     * @return
     */
    inline fun <reified T : Any> getFirstBlock(key: String, defaultValue: T): T {
        //通过阻塞获取
        return runBlocking(Dispatchers.IO) {
            var resultValue = defaultValue
            dataStore.data.first {
                resultValue = (
                        when (T::class) {
                            Int::class -> {
                                it[intPreferencesKey(key)]
                            }
                            Double::class -> {
                                it[doublePreferencesKey(key)]
                            }
                            String::class -> {
                                it[stringPreferencesKey(key)]
                            }
                            Boolean::class -> {
                                it[booleanPreferencesKey(key)]
                            }
                            Float::class -> {
                                it[floatPreferencesKey(key)]
                            }
                            Long::class -> {
                                it[longPreferencesKey(key)]
                            }
                            Set::class -> {
                                it[stringSetPreferencesKey(key)]
                            }
                            else -> {
                                null
                            }
                        } ?: defaultValue
                        ) as T
                true
            }
            resultValue
        }
    }


    /**
     * Save 保存
     *
     * @param T [Type]
     * @param key
     * @param value
     * @return
     */
    @Suppress("UNCHECKED_CAST")
    inline fun <reified T : Any> saveBlock(key: String, value: T): Boolean {
        return runBlocking(Dispatchers.IO) {
            try {
                dataStore.edit {
                    when (T::class) {
                        Int::class -> {
                            it[intPreferencesKey(key)] = value as Int
                        }
                        Double::class -> {
                            it[doublePreferencesKey(key)] = value as Double
                        }
                        String::class -> {
                            it[stringPreferencesKey(key)] = value as String
                        }
                        Boolean::class -> {
                            it[booleanPreferencesKey(key)] = value as Boolean
                        }
                        Float::class -> {
                            it[floatPreferencesKey(key)] = value as Float
                        }
                        Long::class -> {
                            it[longPreferencesKey(key)] = value as Long
                        }
                        Set::class -> {
                            it[stringSetPreferencesKey(key)] = value as Set<String>
                        }
                        else -> {
                            throw IllegalArgumentException(" Can't handle 'value' ")
                        }
                    }
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}