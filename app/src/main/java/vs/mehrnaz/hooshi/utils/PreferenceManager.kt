package vs.mehrnaz.hooshi.utils

import android.content.Context
import android.content.SharedPreferences
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import vs.mehrnaz.hooshi.R

class PreferenceManager(private var context: Context) {
    lateinit var sharedPreferences: SharedPreferences

    init {
        getSharedPreferences()
    }

    private fun getSharedPreferences(){
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.my_preference), Context.MODE_PRIVATE)
    }

    fun writePreference(key: Int, value: String){
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(context.getString(key), value)
        editor.apply()
    }

    fun checkPreference(key: Int): String? {
        return sharedPreferences.getString(context.getString(key), null)
    }

    fun writeLongPreference(key: Int, value: Long){
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putLong(context.getString(key), value)
        editor.apply()
    }

    fun checkLongPreference(key: Int): Long {
        return sharedPreferences.getLong(context.getString(key), 0)
    }

    fun writeIntPreference(key: Int, value: Int){
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(context.getString(key), value)
        editor.apply()
    }

    fun checkIntPreference(key: Int): Int {
        return sharedPreferences.getInt(context.getString(key), 0)
    }

    fun clearPreferences(){
        sharedPreferences.edit().clear().apply()
    }

    fun removePreferences(key: String){
        sharedPreferences.edit().remove(key).apply()
    }

    inline fun <reified T> writeObject(key: String, obj: T){
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        val moshi: Moshi? = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<T> = moshi!!.adapter(T::class.java)
        val json = jsonAdapter.toJson(obj)
        editor.putString(key, json)
        editor.apply()
    }

    inline fun <reified T> checkObject(key: String) : T? {
        val string = sharedPreferences.getString(key, null)
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<T> = moshi.adapter(T::class.java)
        return if (string.isNullOrEmpty()) null
        else
            jsonAdapter.fromJson(string)
    }

}