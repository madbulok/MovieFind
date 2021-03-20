package com.uzlov.moviefind.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uzlov.moviefind.R
import com.uzlov.moviefind.model.Result

class PopularFilmsAdapter : RecyclerView.Adapter<PopularFilmsHolder>() {
    private val mListFilms:MutableList<Result> = mutableListOf()


    fun addFilm(film : Result ){
        mListFilms.add(film)
        notifyItemInserted(mListFilms.size)
    }

    fun addFilms(film :  List<Result>){
        mListFilms.addAll(film)
        notifyItemRangeInserted(mListFilms.size-film.size, mListFilms.size)
    }

    fun setFilms(films : List<Result>){
        mListFilms.apply {
            clear()
            addAll(films)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularFilmsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
        return PopularFilmsHolder(view)
    }

    override fun onBindViewHolder(holder: PopularFilmsHolder, position: Int) {
        holder.onBind(mListFilms[position])
    }

    override fun getItemCount(): Int  = mListFilms.size

}

class PopularFilmsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun onBind(film : Result){

    }

}
