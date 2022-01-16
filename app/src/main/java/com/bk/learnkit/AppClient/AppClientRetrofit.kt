package com.bk.learnkit.AppClient

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.bk.learnkit.utilities.PrefManager
import com.bk.workPassenger.apiServices.ApiServices
import okhttp3.*
import okhttp3.CacheControl
import okhttp3.Interceptor.Chain
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


class AppClientRetrofit : MultiDexApplication() {


    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

    }

    //----------------------------------------------------------------------
    override fun onCreate() {
        super.onCreate()
        mContext = this
        PrefManager.iniInstance(this)
    }

    companion object {

        private val mBaseUrl = "https://newsapi.org/v2/"
        private var mRetrofit: Retrofit? = null
        private lateinit var mContext: Context  // AppClientRetrofit
        var mtag: String = AppClientRetrofit::class.java.simpleName


        @Synchronized
        fun getRetrofitInstance(): ApiServices {

            // setup cache
            var cache: Cache? = null
            try {
                cache = Cache(File(mContext.cacheDir, "http-cache"), (10 * 1024 * 1024).toLong())
            } catch (e: IOException) {
                Log.e(mtag, "Could not create http cache", e)
            }

            // OkHttp setup
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor()!!) // used if network off OR on
                .addNetworkInterceptor(networkInterceptor()!!) // only used when network is on
                .addInterceptor(offlineInterceptor()!!)
                .cache(cache)
                .build()

            // Retrofit Setup
            mRetrofit = Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return mRetrofit!!.create(ApiServices::class.java)

        }

        //This interceptor will be called ONLY if the network is available
        private fun networkInterceptor(): Interceptor? {
            return object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Chain): Response {
                    Log.e(mtag, "network interceptor: called.")
                    val response = chain.proceed(chain.request())
                    val cacheControl: CacheControl = CacheControl.Builder()
                        .maxAge(5, TimeUnit.SECONDS)
                        .build()
                    return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", cacheControl.toString())
                        .build()
                }
            }
        }

        // This interceptor will be called both if the network is available and if the network is not available
        private fun offlineInterceptor(): Interceptor? {
            return object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Chain): Response {
                    Log.e(mtag, "offline interceptor: called.")
                    var request = chain.request()

                    // prevent caching when network is on. For that we use the "networkInterceptor"
                    if (!isConnect()) {
                        val cacheControl: CacheControl = CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build()
                        request = request.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .cacheControl(cacheControl)
                            .build()
                    }
                    return chain.proceed(request)
                }
            }
        }

        private fun httpLoggingInterceptor(): HttpLoggingInterceptor? {
            val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.e(mtag, "log: http log: " + message);
                }
            })
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return httpLoggingInterceptor
        }

        fun isConnect(): Boolean {
            val connectivityManager =
                mContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val networkInfo = connectivityManager.activeNetworkInfo
                return networkInfo != null && networkInfo.isConnected
            }
            return false
        }

    }


}