package com.uzlov.moviefind.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uzlov.moviefind.R
import com.uzlov.moviefind.databinding.ItemActorBinding
import com.uzlov.moviefind.model.Cast

class ActorFilmsAdapter : RecyclerView.Adapter<ActorFilmsHolder>() {
    private val mListFilms:MutableList<Cast> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorFilmsHolder {
        val binding = ItemActorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ActorFilmsHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorFilmsHolder, position: Int) {
        holder.onBind(mListFilms[position])
    }

    override fun onViewRecycled(holder: ActorFilmsHolder) {
        super.onViewRecycled(holder)
        holder.recycle()
    }

    override fun getItemCount(): Int  = mListFilms.size

    fun addActor(actor: Cast) {
        mListFilms.add(actor)
        notifyItemInserted(mListFilms.size-1)
    }
}

class ActorFilmsHolder(private val binding: ItemActorBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(actor : Cast){
        with(binding){
            Glide
                .with(imageActor.context)
                .load(actor.getFullImagePath())
                .into(imageActor)
            actorName.text = actor.original_name.replace(" ", System.getProperty("line.separator") ?: "\n")
        }
    }

    fun recycle(){
        Glide.get(binding.imageActor.context)
            .requestManagerRetriever
            .get(binding.imageActor.context)
            .clear(binding.imageActor)
    }
}
