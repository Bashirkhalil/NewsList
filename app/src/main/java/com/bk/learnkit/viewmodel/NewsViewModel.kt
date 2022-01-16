package com.bk.learnkit.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bk.learnkit.repository.NewsRepo
import com.bk.workPassenger.model.NewsPOJO


class NewsViewModel(application: Application) : AndroidViewModel(application) {

    private var instance : NewsRepo = NewsRepo()

    private val mList :MutableLiveData<NewsPOJO>? by lazy {
        loadFromRepo(application)
    }

    private fun loadFromRepo(application: Application): MutableLiveData<NewsPOJO>?{
        return instance.getNewFromRepoList(application)
    }

    fun getAllNews(): MutableLiveData<NewsPOJO>?{
        return mList
    }

}