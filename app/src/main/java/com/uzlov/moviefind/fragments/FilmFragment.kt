package com.uzlov.moviefind.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uzlov.moviefind.databinding.FragmentFilmBinding
import com.uzlov.moviefind.ui.ActorFilmsAdapter
import com.uzlov.moviefind.ui.MyItemDecorator
import com.uzlov.moviefind.viewmodels.FilmsViewModel

class FilmFragment : Fragment() {
    private var _viewBinding: FragmentFilmBinding?=null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: FilmsViewModel by lazy {
        ViewModelProvider(this).get(FilmsViewModel::class.java)
    }
    private var id_film  = 0

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
        id_film = arguments?.getInt("film_key") ?: 0
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

        viewModel.getFilmById(id_film).observe(viewLifecycleOwner, {
            with(viewBinding) {
                titleTv.text = it.title
                ratingFilm.rating = it.vote_average.div(2).toFloat()
                descriptionTV.text = it.overview
                genreFilmTv.text = it.genres.joinToString(", ")
                studioFilm.text = it.production_companies[0].name
                yearFilmTv.text = it.release_date

                recyclerViewActor.apply {
                    adapter = ActorFilmsAdapter()
                    layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                    addItemDecoration(MyItemDecorator(1), RecyclerView.HORIZONTAL)
                }

                backButton.setOnClickListener {
                    parentFragmentManager.popBackStack()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}