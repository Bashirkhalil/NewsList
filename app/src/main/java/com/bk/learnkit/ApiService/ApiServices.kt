package com.bk.workPassenger.apiServices

import com.bk.workPassenger.model.NewsPOJO
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

public interface ApiServices {

    //---------------------------------------------------------------------------------
    @GET("everything")
    fun getNewsLis(
            @Query("apiKey") apiKey:String,
            @Query("sortBy") sortBy: String,
            @Query("from") from: String,
            @Query("q") q: String

    ): Call<NewsPOJO>

//---------------------------------------------------------------------------------

}