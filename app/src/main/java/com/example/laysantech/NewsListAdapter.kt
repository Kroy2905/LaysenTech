package com.example.laysantech

/*class NewsListAdapter {
}*/


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter( private val listener:  NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {
    private val items: ArrayList<News> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemnews,parent,false)
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener(){
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }

        return viewHolder

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {   //takes 1 data at a time from the ViewHolder and binds it
        val currentItem = items[position]
        Log.i("Title = ",currentItem.title.toString())
        holder.titleview.text = currentItem.title
        if( !currentItem.author.equals("null"))
            holder.author.text = currentItem.author
        else
            holder.author.text = ""

        if(!currentItem.imageUrl.equals("null"))
            Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
        else
            Glide.with(holder.itemView.context).load("https://1m19tt3pztls474q6z46fnk9-wpengine.netdna-ssl.com/wp-content/themes/unbound/images/No-Image-Found-400x264.png").into(holder.image)


    }

    override fun getItemCount(): Int {
        return  items.size

    }
    fun updateNews(updatedNews:ArrayList<News>){
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()     // tells the adapter that datas have been changed
    }


}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){    // ----> ViewHolder   receives an itemview
    val  titleview: TextView = itemView.findViewById(R.id.titleTV)
    val image:ImageView = itemView.findViewById(R.id.image)
    val author:TextView = itemView.findViewById(R.id.author)

}


interface NewsItemClicked{
    fun onItemClicked(item: News)

}