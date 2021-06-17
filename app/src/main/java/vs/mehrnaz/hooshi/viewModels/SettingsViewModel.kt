package vs.mehrnaz.hooshi.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import vs.mehrnaz.hooshi.R
import vs.mehrnaz.hooshi.utils.PreferenceManager

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    fun saveSetting(userName: String,id: Long,min: Long, max: Long, row: Long,input: Int){
        PreferenceManager(getApplication()).writePreference(R.string.user_name_key,userName)
        PreferenceManager(getApplication()).writeLongPreference(R.string.id_key,id)
        PreferenceManager(getApplication()).writeLongPreference(R.string.min_key,min)
        PreferenceManager(getApplication()).writeLongPreference(R.string.max_key,max)
        PreferenceManager(getApplication()).writeLongPreference(R.string.rows_key,row)
        PreferenceManager(getApplication()).writeIntPreference(R.string.input_key,input)
    }

}