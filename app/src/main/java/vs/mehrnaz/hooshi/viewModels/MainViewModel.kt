package vs.mehrnaz.hooshi.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import vs.mehrnaz.hooshi.R
import vs.mehrnaz.hooshi.utils.PreferenceManager
import vs.mehrnaz.hooshi.models.LastDataResponseModel
import vs.mehrnaz.hooshi.network.BaseApi

class MainViewModel(application: Application) : AndroidViewModel(application) {

//    private val _status = MutableLiveData<ApiStatus>()
//    val status : LiveData<ApiStatus>
//        get() = _status

    private val _lastData = MutableLiveData<String>()
    val lastData : LiveData<String>
        get() = _lastData

    private var row = PreferenceManager(getApplication()).checkLongPreference(R.string.rows_key)

    private val _dataHistory = MutableLiveData<MutableList<Float>>()
    val dataHistory : LiveData<MutableList<Float>>
        get() = _dataHistory

    private var tempList = mutableListOf<Float>()
    private val slicedList = mutableListOf<Float>()

    val id = PreferenceManager(getApplication()).checkLongPreference(R.string.id_key)
    val input = PreferenceManager(getApplication()).checkIntPreference(R.string.input_key)


    fun loadDefaultData(){
        if(row == 0L) row = 10

        PreferenceManager(getApplication()).checkObject<MutableList<Float>>("data_history").let {
            if (it!= null){

                tempList = it

                var step = (tempList.size / row).toInt()
                if (step == 0) step = 1

                for(i in 0 until tempList.size step step)
                    slicedList.add(tempList[i])

                Log.d("slice", "tempList: "+tempList.size+" slicedList "+ slicedList.size)

                _dataHistory.value = slicedList
            }

        }
    }

    fun getLastData(){
        viewModelScope.launch {

            try {
                val apiResponse: LastDataResponseModel = BaseApi.retrofitService.getData(id)

                _lastData.value = when(input){
                    0 -> apiResponse.inputA
                    1 -> apiResponse.inputB
                    2 -> apiResponse.inputC
                    3 -> apiResponse.inputD
                    4 -> apiResponse.inputE
                    5 -> apiResponse.inputF
                    6 -> apiResponse.inputG
                    else -> apiResponse.inputH
                }

                tempList.add(_lastData.value!!.toFloat())
                if (tempList.size > 100)
                    tempList.removeAt(0)

                PreferenceManager(getApplication()).writeObject("data_history",tempList)

                slicedList.clear()

                var step = (tempList.size / row).toInt()
                if (step == 0) step = 1

                for(i in 0 until tempList.size step step)
                    slicedList.add(tempList[i])

                Log.d("slice", "tempList: "+tempList.size+" slicedList "+ slicedList.size)

                _dataHistory.value = slicedList
            }
            catch (e: Exception){

            }


        }
    }
}