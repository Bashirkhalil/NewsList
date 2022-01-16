package com.bk.learnkit.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.bk.learnkit.AppClient.AppClientRetrofit
import com.bk.workPassenger.model.NewsPOJO
import retrofit2.Call
import retrofit2.Response


class NewsRepo {

    private var mTag: String = NewsRepo::class.simpleName.toString()
    private var mList = MutableLiveData<NewsPOJO>()
    private val mMessage: MutableLiveData<String>? = null

    fun getNewFromRepoList(application: Application): MutableLiveData<NewsPOJO> {

//        a9990c827b6d405f9e26861fc5724722
        AppClientRetrofit.getRetrofitInstance().getNewsLis( "21f8ec4eb52d4bab9c88de8f9f380dd3",
                "2021-12-15",
                "publishedAt",
                "tesla").enqueue(object : retrofit2.Callback<NewsPOJO>{
            override fun onResponse(call: Call<NewsPOJO>, response: Response<NewsPOJO>) {
                Log.e(mTag, "Re 1 => $response")
                Log.e(mTag, "Re 2 => $response.isSuccessful")
                Log.e(mTag, "Re 3 => $response.code()")
                Log.e(mTag, "Re 4 => $response.body()")


                Log.e(mTag, "log: -----------------------------");
                Log.d(mTag, "onResponse: " + response.body());

                if(response.raw().networkResponse != null){
                    Log.d(mTag, "onResponse: response is from NETWORK...");
                }
                else if(response.raw().cacheResponse != null && response.raw().networkResponse== null){
                    Log.d(mTag, "onResponse: response is from CACHE...");
                }


                mList.value = response.body()
            }

            override fun onFailure(call: Call<NewsPOJO>, t: Throwable) {
                Log.e(mTag, "Re Error => " + t.message)
                Log.e(mTag, "Re Error => " + call.toString())
                Log.e(mTag, "Re Error => " + t.message)

                mMessage?.value = t.message.toString()
            }

        })

        return mList ;
    }//End

}







