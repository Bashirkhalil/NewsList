package com.bk.learnkit.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bk.learnkit.R
import com.bk.learnkit.viewmodel.NewsViewModel
import com.bk.workPassenger.adapters.NewsAdaper
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity() {

    val mContext:Context = this
    lateinit var mRecyclerView: RecyclerView
    lateinit var mViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//  Depreciated
//  mViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)

        mRecyclerView = findViewById(R.id.mRecyclerView)

        mViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        mViewModel.getAllNews()!!.observe(this, Observer {
            Log.e("TAG", "home size => ${it.status}")

            mRecyclerView.layoutManager = LinearLayoutManager(this)
            mRecyclerView.itemAnimator = DefaultItemAnimator()
            mRecyclerView.adapter = NewsAdaper(this, it)

        })


    }


}
