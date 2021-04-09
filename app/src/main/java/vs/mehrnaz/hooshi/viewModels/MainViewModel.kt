package vs.mehrnaz.hooshi.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vs.mehrnaz.hooshi.network.BaseApi

class MainViewModel(application: Application) : AndroidViewModel(application) {

//    private val _status = MutableLiveData<ApiStatus>()
//    val status : LiveData<ApiStatus>
//        get() = _status

    private val _lastData = MutableLiveData<String>()
    val lastData : LiveData<String>
        get() = _lastData

    init {
        getLastData()
    }

    fun getLastData(){
        viewModelScope.launch {
           _lastData.value = BaseApi.retrofitService.getData(800300).id
        }
    }
}