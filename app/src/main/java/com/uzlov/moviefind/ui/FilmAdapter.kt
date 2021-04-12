package com.uzlov.moviefind.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uzlov.moviefind.databinding.ItemFilmBinding
import com.uzlov.moviefind.interfaces.IOnClickListenerAdapter
import com.uzlov.moviefind.model.Result

class FilmAdapter(private val onClick: IOnClickListenerAdapter) :
    RecyclerView.Adapter<FilmAdapter.FilmsHolder>() {
    private val mListFilms: MutableList<Result> = mutableListOf()

    fun setFilms(films: List<Result>) {
        mListFilms.clear()
        mListFilms.addAll(films)
    }

    override fun onViewRecycled(holder: FilmsHolder) {
        super.onViewRecycled(holder)
        holder.recycle()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsHolder {
        val binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilmsHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmsHolder, position: Int) {
        holder.onBind(mListFilms[position])
    }

    override fun getItemCount(): Int = mListFilms.size

    inner class FilmsHolder(private val binding: ItemFilmBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(film: Result) {
            with(binding) {
                Glide
                    .with(binding.imageFilm.context)
                    .load(film.getImage50())
                    .into(imageFilm)
                titleFilmTV.text = film.title
                ratingFilm.rating = film.vote_average.toFloat().div(2)
                root.setOnClickListener {
                    onClick.onClick(adapterPosition, film.id)
                }
            }
        }

        fun recycle() {
            Glide
                .get(binding.imageFilm.context)
                .requestManagerRetriever
                .get(binding.imageFilm.context)
                .clear(binding.imageFilm)
        }
    }
}