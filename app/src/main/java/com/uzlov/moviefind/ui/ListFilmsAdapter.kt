package com.uzlov.moviefind.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uzlov.moviefind.database.FilmEntityDB
import com.uzlov.moviefind.databinding.ItemFavoriteFilmLayoutBinding
import com.uzlov.moviefind.interfaces.IOnClickListenerAdapter
import com.uzlov.moviefind.model.Result

class ListFilmsAdapter(var listenerClick: IOnClickListenerAdapter) :
    RecyclerView.Adapter<ListFilmsAdapter.PopularFilmsHolder>() {
    private val mListFilms: MutableList<Result> = mutableListOf()

    override fun onViewRecycled(holder: PopularFilmsHolder) {
        super.onViewRecycled(holder)
        holder.recycle()
    }

    fun setFilms(films: List<Result>) {
        mListFilms.run {
            clear()
            addAll(films)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularFilmsHolder {
        val binding = ItemFavoriteFilmLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularFilmsHolder(binding)
    }

    override fun onBindViewHolder(holder: PopularFilmsHolder, position: Int) {
        holder.onBind(mListFilms[position])
    }

    override fun getItemCount(): Int = mListFilms.size

    inner class PopularFilmsHolder(private val binding: ItemFavoriteFilmLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(film: Result) {
            with(binding){
                Glide.with(binding.root.context)
                    .load(film.getImageOriginal())
                    .into(imageFilm)
                titleFilm.text = film.title
                ratingFilm.rating = film.vote_average.div(2).toFloat()
                root.setOnClickListener {
                    listenerClick.onClick(adapterPosition, film.id.toInt())
                }
            }
        }

        fun recycle(){
            Glide.with(binding.root.context)
                .clear(binding.imageFilm)
        }
    }
}


