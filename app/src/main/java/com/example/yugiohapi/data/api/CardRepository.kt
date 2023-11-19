package com.example.yugiohapi.data.api

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.net.URLEncoder

interface  CardApi {
    @GET("api/v7/cardinfo.php")
    suspend fun fetchCard(@Query("name") name:String):CardDetailResponse

    @GET("api/v7/cardinfo.php")
    suspend fun fetchAllCard():CardListResponse
}

class CardRepository private constructor(private val api:CardApi){

    private val _card = MutableLiveData<CardListApiModel>()
    val card: LiveData<CardListApiModel>
        get() = _card

    companion object {
        private var _INSTANCE: CardRepository? = null
        fun getInstance(): CardRepository {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://db.ygoprodeck.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val cardApi = retrofit.create(CardApi::class.java)
            _INSTANCE = _INSTANCE ?: CardRepository(cardApi)
            return _INSTANCE!!

        }
    }

    @SuppressLint("SuspiciousIndentation")
    suspend fun fetch() {
        try {

            val listResponse = api.fetchAllCard()
            Log.d("API_RESPONSE", listResponse.toString())

            val list: MutableList<CardApiModel> = mutableListOf()
            listResponse.data.forEach() {
                Log.d("NAME", "${it.name}")
                try {
                    val encodedName = it.name.replace("\"", "\\\"")
                    Log.e("ENCODE", "${encodedName}")
                    val fetchCardUrl = "https://db.ygoprodeck.com/api/v7/cardinfo.php?name=${encodedName}"
                    Log.d("FETCH_URL", "URL for ${it.name}: $fetchCardUrl")
                    val detailResponse: CardDetailResponse = api.fetchCard(encodedName)
                    Log.d("CARD_API_URL", "URL for ${it.name}: ${api.fetchCard(name = encodedName)}")

                    val cardApiModel = CardApiModel(
                        detailResponse.id,
                        detailResponse.name,
                        detailResponse.type,
                        detailResponse.desc,
                        detailResponse.archetype,
                        detailResponse.card_images
                    )
                    list.add(cardApiModel)
                } catch (e: Exception) {
                    Log.e("API_ERROR", "Error fetching card ${it.name}: $e")
                }
            }

            val cardListApiModel = CardListApiModel(list)
            _card.value = cardListApiModel
        } catch (e: Exception) {
            Log.e("FETCH", "Error fetching data", e)
        }

    }

}