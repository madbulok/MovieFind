package com.uzlov.moviefind.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uzlov.moviefind.R
import com.uzlov.moviefind.databinding.ItemActorBinding

class ActorFilmsAdapter : RecyclerView.Adapter<ActorFilmsHolder>() {
    private val mListFilms:MutableList<String> = mutableListOf()

    init {
        mListFilms.add("Арнольд \n Цварцнеггер")
        mListFilms.add("Майкл \n Бин")
        mListFilms.add("Линда \n Хэмилтон")
        mListFilms.add("Поп \n Уинфилд")
        mListFilms.add("Лэн \n Хенриксен")
        mListFilms.add("Бесс \n Мотта")
        mListFilms.add("Эрл \n Боэн")
    }

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

}

class ActorFilmsHolder(private val binding: ItemActorBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(actor : String){
        with(binding){
            Glide
                .with(imageActor.context)
                .load(R.drawable.ter)
                .into(imageActor)
            actorName.text = actor
        }
    }

    fun recycle(){
        Glide.get(binding.imageActor.context)
            .requestManagerRetriever
            .get(binding.imageActor.context)
            .clear(binding.imageActor)
    }
}
