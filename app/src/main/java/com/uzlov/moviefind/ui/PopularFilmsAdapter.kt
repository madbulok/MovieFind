package com.uzlov.moviefind.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uzlov.moviefind.databinding.ItemFilmBinding
import com.uzlov.moviefind.interfaces.IOnClickListenerAdapter
import com.uzlov.moviefind.model.Result

class PopularFilmsAdapter(var listenerClick: IOnClickListenerAdapter) :
    RecyclerView.Adapter<PopularFilmsAdapter.PopularFilmsHolder>() {
    private val mListFilms: MutableList<Result> = mutableListOf()


    fun addFilm(film: Result) {
        mListFilms.add(film)
        notifyItemInserted(mListFilms.size)
    }

    override fun onViewRecycled(holder: PopularFilmsHolder) {
        super.onViewRecycled(holder)

    }

    fun setTestFilms(films: List<Result>) {
        mListFilms.run {
            clear()
            addAll(films)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularFilmsHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularFilmsHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularFilmsHolder, position: Int) {
        holder.onBind(mListFilms[position])
    }

    override fun getItemCount(): Int = mListFilms.size

    inner class PopularFilmsHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(film: Result) {
        }
    }
}


