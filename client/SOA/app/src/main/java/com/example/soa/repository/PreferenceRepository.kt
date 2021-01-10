package com.example.soa.repository

import android.content.Context
import android.content.SharedPreferences

interface IPreference {
    operator fun set(key: String, value: String)

    operator fun set(key: String, value: Boolean)

    operator fun set(key: String, value: Int)

    operator fun set(key: String, value: Float)

    operator fun set(key: String, value: Long)

    operator fun get(key: String): String?

    operator fun get(key: String, defaultValue: Boolean): Boolean

    operator fun get(key: String, defaultValue: Int): Int

    operator fun get(key: String, defaultValue: Float): Float

    operator fun get(key: String, defaultValue: Long): Long

    fun remove(key: String)
}

class PreferenceRepository(context: Context, name: String) : IPreference {

    private val sLock = Any()

    private val settings: SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    /**
     * Saves the given preference string value for the given key.
     *
     * @param value the string value to be stored
     * @param key   the key corresponding to the given value
     */
    override fun set(key: String, value: String) {
        synchronized(sLock) {
            val editor = settings.edit()
            editor.putString(key, value)
            editor.apply()
        }
    }

    /**
     * Saves the given preference boolean value for the given key.
     *
     * @param value the boolean value to be stored
     * @param key   the key corresponding to the given value
     */
    override fun set(key: String, value: Boolean) {
        synchronized(sLock) {
            val editor = settings.edit()
            editor.putBoolean(key, value)
            editor.apply()
        }
    }

    /**
     * Saves the given preference int value for the given key.
     *
     * @param value the int value to be stored
     * @param key   the key corresponding to the given value
     */
    @Synchronized
    override fun set(key: String, value: Int) {
        synchronized(sLock) {
            val editor = settings.edit()
            editor.putInt(key, value)
            editor.apply()
        }
    }

    /**
     * Saves the given preference float value for the given key.
     *
     * @param value the float value to be stored
     * @param key   the key corresponding to the given value
     */
    @Synchronized
    override fun set(key: String, value: Float) {
        synchronized(sLock) {
            val editor = settings.edit()
            editor.putFloat(key, value)
            editor.apply()
        }
    }

    /**
     * Saves the given preference long value for the given key.
     *
     * @param value the long value to be stored
     * @param key   the key corresponding to the given value
     */
    @Synchronized
    override fun set(key: String, value: Long) {
        synchronized(sLock) {
            val editor = settings.edit()
            editor.putLong(key, value)
            editor.apply()
        }
    }

    /**
     * Returns the preference value corresponding to the given key or default value if no value for the given key is found.
     *
     * @param key          the key of the value to get
     * @return Preference value corresponding to the given key or default value if no value for the given key is found
     */
    @Synchronized
    override fun get(key: String): String? = settings.getString(key, null)

    /**
     * Returns the preference value corresponding to the given key or default value if no value for the given key is found.
     *
     * @param key          the key of the value to get
     * @param defaultValue default value to use if no value for the given key is found
     * @return Preference value corresponding to the given key or default value if no value for the given key is found
     */
    @Synchronized
    override fun get(key: String, defaultValue: Boolean): Boolean =
        settings.getBoolean(key, defaultValue)

    /**
     * Returns the preference value corresponding to the given key or default value if no value for the given key is found.
     *
     * @param key          the key of the value to get
     * @param defaultValue default value to use if no value for the given key is found
     * @return Preference value corresponding to the given key or default value if no value for the given key is found
     */
    @Synchronized
    override fun get(key: String, defaultValue: Int): Int = settings.getInt(key, defaultValue)

    /**
     * Returns the preference value corresponding to the given key or default value if no value for the given key is found.
     *
     * @param key          the key of the value to get
     * @param defaultValue default value to use if no value for the given key is found
     * @return Preference value corresponding to the given key or default value if no value for the given key is found
     */
    @Synchronized
    override fun get(key: String, defaultValue: Float): Float = settings.getFloat(key, defaultValue)

    /**
     * Returns the preference value corresponding to the given key or default value if no value for the given key is found.
     *
     * @param key          the key of the value to get
     * @param defaultValue default value to use if no value for the given key is found
     * @return Preference value corresponding to the given key or default value if no value for the given key is found
     */
    @Synchronized
    override fun get(key: String, defaultValue: Long): Long = settings.getLong(key, defaultValue)

    /**
     * Removes the preference object associated with the given key
     *
     * @param key the key of the object to be removed
     */
    @Synchronized
    override fun remove(key: String) {
        synchronized(sLock) {
            val editor = settings.edit()
            editor.remove(key)
            editor.apply()
        }
    }
}
