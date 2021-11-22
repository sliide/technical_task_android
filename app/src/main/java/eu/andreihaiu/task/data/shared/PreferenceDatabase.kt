package eu.andreihaiu.task.data.shared


import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PreferenceDatabase(
    context: Context
) {

    companion object {
        private const val PREFERENCE_NAME = "preferences-andreihaiu-task"
    }

    private val preferences =
        context.applicationContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun register(sharedListener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.registerOnSharedPreferenceChangeListener(sharedListener)
    }

    fun unregister(sharedListener: SharedPreferences.OnSharedPreferenceChangeListener) {
        preferences.unregisterOnSharedPreferenceChangeListener(sharedListener)
    }

    private sealed class PreferenceFieldDelegate<T>(
        protected val key: kotlin.String,
        protected val defaultValue: T
    ) : ReadWriteProperty<PreferenceDatabase, T> {

        class Boolean(key: kotlin.String, defaultValue: kotlin.Boolean = false) :
            PreferenceFieldDelegate<kotlin.Boolean>(key, defaultValue) {

            override fun getValue(thisRef: PreferenceDatabase, property: KProperty<*>) =
                thisRef.preferences.getBoolean(key, defaultValue)

            override fun setValue(
                thisRef: PreferenceDatabase,
                property: KProperty<*>,
                value: kotlin.Boolean
            ) =
                thisRef.preferences.edit().putBoolean(key, value).apply()
        }

        class Int(key: kotlin.String, defaultValue: kotlin.Int = 0) :
            PreferenceFieldDelegate<kotlin.Int>(key, defaultValue) {

            override fun getValue(thisRef: PreferenceDatabase, property: KProperty<*>) =
                thisRef.preferences.getInt(key, defaultValue)

            override fun setValue(
                thisRef: PreferenceDatabase,
                property: KProperty<*>,
                value: kotlin.Int
            ) =
                thisRef.preferences.edit().putInt(key, value).apply()
        }

        class Float(key: kotlin.String, defaultValue: kotlin.Float = 0f) :
            PreferenceFieldDelegate<kotlin.Float>(key, defaultValue) {

            override fun getValue(thisRef: PreferenceDatabase, property: KProperty<*>) =
                thisRef.preferences.getFloat(key, defaultValue)

            override fun setValue(
                thisRef: PreferenceDatabase,
                property: KProperty<*>,
                value: kotlin.Float
            ) =
                thisRef.preferences.edit().putFloat(key, value).apply()
        }

        class Long(key: kotlin.String, defaultValue: kotlin.Long = 0) :
            PreferenceFieldDelegate<kotlin.Long>(key, defaultValue) {

            override fun getValue(thisRef: PreferenceDatabase, property: KProperty<*>) =
                thisRef.preferences.getLong(key, defaultValue)

            override fun setValue(
                thisRef: PreferenceDatabase,
                property: KProperty<*>,
                value: kotlin.Long
            ) =
                thisRef.preferences.edit().putLong(key, value).apply()
        }

        class String(key: kotlin.String, defaultValue: kotlin.String = "") :
            PreferenceFieldDelegate<kotlin.String>(key, defaultValue) {

            override fun getValue(thisRef: PreferenceDatabase, property: KProperty<*>) =
                thisRef.preferences.getString(key, defaultValue)
                    ?: defaultValue

            override fun setValue(
                thisRef: PreferenceDatabase,
                property: KProperty<*>,
                value: kotlin.String
            ) =
                thisRef.preferences.edit().putString(key, value).apply()
        }

        class StringSet(key: kotlin.String, defaultValue: Set<kotlin.String>) :
            PreferenceFieldDelegate<Set<kotlin.String>>(key, defaultValue) {

            override fun getValue(
                thisRef: PreferenceDatabase,
                property: KProperty<*>
            ): Set<kotlin.String> = thisRef.preferences.getStringSet(key, defaultValue)
                ?: setOf()

            override fun setValue(
                thisRef: PreferenceDatabase,
                property: KProperty<*>,
                value: Set<kotlin.String>
            ) =
                thisRef.preferences.edit().putStringSet(key, value).apply()
        }
    }
}
