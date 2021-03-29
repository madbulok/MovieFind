package com.uzlov.moviefind.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uzlov.moviefind.databinding.FragmentFilmBinding
import com.uzlov.moviefind.model.TestFilm
import com.uzlov.moviefind.ui.ActorFilmsAdapter
import com.uzlov.moviefind.ui.MyItemDecorator

class FilmFragment : Fragment() {
    private var _viewBinding: FragmentFilmBinding?=null
    private val viewBinding get() = _viewBinding!!
    private lateinit var film : TestFilm

    companion object {
        fun newInstance(testFilm: TestFilm): FilmFragment {
            val data = Bundle().apply {
                putParcelable("film_key", testFilm)
            }
            return FilmFragment().apply { arguments = data }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        film = arguments?.getParcelable("film_key") ?: TestFilm()
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
        with(viewBinding) {
            titleTv.text = film.name
            ratingFilm.rating = film.rating.toFloat()
            descriptionTV.text = film.description
            genreFilmTv.text = film.genre
            studioFilm.text = film.studio
            yearFilmTv.text = film.year

            recyclerViewActor.apply {
                adapter = ActorFilmsAdapter()
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
                addItemDecoration(MyItemDecorator(1), RecyclerView.HORIZONTAL)
            }

            backButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}