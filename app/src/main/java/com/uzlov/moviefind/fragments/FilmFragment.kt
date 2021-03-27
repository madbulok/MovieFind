package com.uzlov.moviefind.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uzlov.moviefind.databinding.FragmentFilmBinding
import com.uzlov.moviefind.model.TestFilm
import com.uzlov.moviefind.ui.ActorFilmsAdapter
import com.uzlov.moviefind.ui.ActorHorizontalItemDecorator

class FilmFragment : Fragment() {
    private var _viewBinding: FragmentFilmBinding?=null
    private val viewBinding get() = _viewBinding!!

    companion object {
        fun newInstance(testFilm : TestFilm){
            val fragment = FilmFragment()
            val bundle = Bundle().apply {
                putParcelable("film_key", testFilm)
            }
        }
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
        val actorAdapter = ActorFilmsAdapter()
        viewBinding.recyclerViewActor.apply {
            adapter = actorAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            addItemDecoration(ActorHorizontalItemDecorator())
        }
    }
}