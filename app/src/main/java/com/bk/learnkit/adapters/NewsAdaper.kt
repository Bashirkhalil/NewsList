package com.bk.workPassenger.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bk.learnkit.R
import com.bk.workPassenger.model.Articles
import com.bk.workPassenger.model.NewsPOJO

import java.util.*

class NewsAdaper(var mConteext: Context, var mList: NewsPOJO) :
        RecyclerView.Adapter<NewsAdaper.NewsViewHolder>() , Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val v = LayoutInflater.from(mConteext).inflate(R.layout.row_my_orderes, parent, false)
        return NewsViewHolder(v)

    }


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        var data =mList.articles[position]
        holder.id.text = data.description
      Log.e("tah","Hello bro $data.description");
    }

    override fun getItemCount(): Int {
      return mList.articles.size
    }


    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: TextView = itemView.findViewById(R.id.status)
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var mList = mutableListOf<Articles>()

                if (constraint == null || constraint.isEmpty()) {
                    mList.addAll(mList)
                } else {

                    for (item in mList) {
                        if (item.author?.toLowerCase()?.startsWith(constraint.toString().toLowerCase()) == true) {
                            mList.add(item)
                        }
                    }
                }

                val results = FilterResults()
                results.values = mList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                results?.values as MutableList<Articles>
                notifyDataSetChanged()

            }
        }
    }

}