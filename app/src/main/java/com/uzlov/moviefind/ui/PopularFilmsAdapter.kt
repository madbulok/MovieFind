package com.uzlov.moviefind.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uzlov.moviefind.databinding.ItemFilmBinding
import com.uzlov.moviefind.fragments.OnClickListenerAdapter
import com.uzlov.moviefind.model.TestFilm

class PopularFilmsAdapter(var listenerClick: OnClickListenerAdapter) :
    RecyclerView.Adapter<PopularFilmsAdapter.PopularFilmsHolder>() {
    private val mListFilms: MutableList<TestFilm> = mutableListOf()


    fun addFilm(film: TestFilm) {
        mListFilms.add(film)
        notifyItemInserted(mListFilms.size)
    }

    fun setTestFilms(films: List<TestFilm>) {
        mListFilms.apply {
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
        fun onBind(film: TestFilm) {
            binding.apply {
                nameFilmTV.text = film.name
                genreFilmTVLabel.text = film.genre
                root.setOnClickListener {
                    listenerClick.onClick(adapterPosition)
                }
            }
        }
    }
}


