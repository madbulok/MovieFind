package com.uzlov.moviefind.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.uzlov.moviefind.R
import com.uzlov.moviefind.activities.ShareActivity
import com.uzlov.moviefind.database.FilmEntityDB
import com.uzlov.moviefind.databinding.FragmentFilmBinding
import com.uzlov.moviefind.model.Film
import com.uzlov.moviefind.ui.ActorFilmsAdapter
import com.uzlov.moviefind.ui.MyItemDecorator
import com.uzlov.moviefind.viewmodels.AppStateFilm
import com.uzlov.moviefind.viewmodels.FilmsViewModel

class FilmFragment : Fragment() {
    private lateinit var adapterActor: ActorFilmsAdapter
    private var _viewBinding: FragmentFilmBinding?=null
    private val viewBinding get() = _viewBinding!!
    private var isAddedFavorite = false

    private val viewModel: FilmsViewModel by lazy {
        ViewModelProvider(this).get(FilmsViewModel::class.java)
    }

    private var idFilm  = 0
    private lateinit var film: Film

    companion object {
        fun newInstance(id: Int): FilmFragment {
            val data = Bundle().apply {
                putInt("film_key", id)
            }
            return FilmFragment().apply { arguments = data }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        idFilm = arguments?.getInt("film_key") ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentFilmBinding.inflate(layoutInflater, container, false)
        return  viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapterActor = ActorFilmsAdapter()
        viewBinding.recyclerViewActor.apply {
            adapter = adapterActor
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            addItemDecoration(MyItemDecorator(RecyclerView.HORIZONTAL), RecyclerView.HORIZONTAL)
        }

        viewModel.getFilmById(idFilm).observe(viewLifecycleOwner, {
            renderData(it)
        })

        viewModel.getCreditFilmByID(idFilm).observe(viewLifecycleOwner, {
            it.cast.map { actor->
                adapterActor.addActor(actor)
            }
        })

        viewModel.filmIsFavorite(idFilm).observe(viewLifecycleOwner, {count->
            when(count){
                1->{
                    viewBinding.favoriteBtn.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_favorite_fill, null)
                    isAddedFavorite = true
                }
                else ->{
                    viewBinding.favoriteBtn.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_favorite_border_24, null)
                }
            }
        })
        initListenerActionsFilm()
    }

    private fun initListenerActionsFilm() {
        viewBinding.favoriteBtn.setOnClickListener {
            when(isAddedFavorite){
                true->{
                    viewModel.removeFilmFromSavedFavorite(idFilm.toLong())
                }
                false->{
                    viewModel.addFilmToFavorite(
                        FilmEntityDB(
                            id = idFilm.toLong(),
                            title = film.title,
                            picture = film.getImageOriginal(),
                            rating = film.vote_average.toFloat(),
                            description = film.overview,
                            favorite = true
                        )
                    )
                }
            }
        }

        viewBinding.shareBtn.setOnClickListener {
            val intent = Intent(requireContext(), ShareActivity::class.java)
            intent.apply {
                putExtra("key_name", film.title)
                putExtra("key_url", film.homepage)
            }
            startActivity(intent)
        }
    }

    private fun renderData(state: AppStateFilm){
        when(state){
            is AppStateFilm.Error -> showError(state.error)
            AppStateFilm.Loading -> showLoading()
            is AppStateFilm.Success -> showData(state.filmsData)
        }
    }

    private fun showData(it: Film) {
        film = it
        with(viewBinding) {
            favoriteBtn.visibility = View.VISIBLE
            titleTv.text = it.title
            ratingFilm.rating = it.vote_average.div(2).toFloat()
            descriptionTV.text = it.overview

            if (it.production_companies.isNotEmpty()) studioFilm.text = it.production_companies[0].name
            yearFilmTv.text = it.release_date

            if (film.adult) titleTv.append(" 18+")

            it.genres.map {
                genreFilmTv.append("${it.name} \n")
            }

            countVoteTV.text = film.vote_count.toString()

            Glide.with(image.context)
                .load(it.getImageOriginal())
                .into(image)

            backButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun showLoading() {}

    private fun showError(error: Throwable) {
        Snackbar.make(viewBinding.root, error.message ?: "Empty error", Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}