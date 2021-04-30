package vs.mehrnaz.hooshi.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vs.mehrnaz.hooshi.models.LastDataResponseModel
import vs.mehrnaz.hooshi.network.BaseApi

class MainViewModel(application: Application) : AndroidViewModel(application) {

//    private val _status = MutableLiveData<ApiStatus>()
//    val status : LiveData<ApiStatus>
//        get() = _status

    private val _lastData = MutableLiveData<String>()
    val lastData : LiveData<String>
        get() = _lastData


    private val _dataHistory = MutableLiveData<MutableList<Float>>()
    val dataHistory : LiveData<MutableList<Float>>
        get() = _dataHistory

    private var tempList = mutableListOf<Float>()

    init {
        _dataHistory.value = tempList
    }

    fun getLastData(){
        viewModelScope.launch {

            val apiResponse: LastDataResponseModel = BaseApi.retrofitService.getData(61002)

           _lastData.value = apiResponse.inputB
            Log.d("LineChart" , "value to be added: "+ apiResponse.inputB)

            tempList.add(apiResponse.inputB!!.toFloat())
            if (tempList.size > 11)
                tempList.removeAt(0)

            _dataHistory.value = tempList

        }
    }
}