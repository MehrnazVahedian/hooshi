package vs.mehrnaz.hooshi.network


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import vs.mehrnaz.hooshi.models.LastDataResponseModel


const val BASE_URL = "http://hivaind.ir/wil/"

val moshi : Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

interface BaseApiService {

    @GET("loglastjson81.php")
    suspend fun getData(@QueryMap id:Long): LastDataResponseModel

}

object BaseApi {
    val retrofitService : BaseApiService by lazy { retrofit.create(BaseApiService::class.java) }
}
