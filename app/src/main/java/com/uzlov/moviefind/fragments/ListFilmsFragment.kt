package com.uzlov.moviefind.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uzlov.moviefind.databinding.ListFilmFragmentBinding
import com.uzlov.moviefind.interfaces.IOnClickListenerAdapter
import com.uzlov.moviefind.model.PopularFilms
import com.uzlov.moviefind.ui.ListFilmsAdapter
import com.uzlov.moviefind.ui.MyItemDecorator
import com.uzlov.moviefind.viewmodels.AppStateFilms
import com.uzlov.moviefind.viewmodels.FilmsViewModel
import com.uzlov.moviefind.viewmodels.TypeFilms

class ListFilmsFragment : Fragment() {

    private lateinit var typeFilm: TypeFilms
    private var _viewBinding: ListFilmFragmentBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val viewModel by lazy { ViewModelProvider(this).get(FilmsViewModel::class.java) }
    private val filmAdapter by lazy { ListFilmsAdapter(callback) }

    private val callback = object : IOnClickListenerAdapter {
        override fun onClick(position: Int, id: Int) {

        }
    }

    companion object {
        const val TYPE_FRAGMENT = "type_fragment"
        fun getInstance(type: TypeFilms): ListFilmsFragment {
            val bundle = Bundle()
            val fragment = ListFilmsFragment()
            when (type) {
                TypeFilms.PopularFilm -> {
                    bundle.putSerializable(TYPE_FRAGMENT, TypeFilms.PopularFilm)
                }
                TypeFilms.RecommendFilm -> {
                    bundle.putSerializable(TYPE_FRAGMENT, TypeFilms.RecommendFilm)
                }
                TypeFilms.UpcomingFilm -> {
                    bundle.putSerializable(TYPE_FRAGMENT, TypeFilms.UpcomingFilm)
                }
                else -> bundle.putSerializable(TYPE_FRAGMENT, TypeFilms.ErrorFilm)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            typeFilm = it?.getSerializable(TYPE_FRAGMENT) as TypeFilms
        }
    }

    private fun loadData(typeFilm: TypeFilms) {
        when (typeFilm) {
            TypeFilms.ErrorFilm -> {

            }
            TypeFilms.PopularFilm -> {
                viewModel.getPopularFilms().observe(viewLifecycleOwner, {
                    renderData(it)
                })
            }
            TypeFilms.RecommendFilm -> {
                viewModel.getTopRatedFilms().observe(viewLifecycleOwner, {
                    renderData(it)
                })
            }
            TypeFilms.UpcomingFilm -> {
                viewModel.getUpcomingFilms().observe(viewLifecycleOwner, {
                    renderData(it)
                })
            }
        }
    }

    private fun renderData(it: AppStateFilms) {
        when (it) {
            is AppStateFilms.Error -> {

            }
            AppStateFilms.Loading -> {

            }
            is AppStateFilms.Success -> {
                showData(it.filmsData)
            }
        }
    }

    private fun showData(filmsData: PopularFilms) {
        filmAdapter.setFilms(films = filmsData.results)
        viewBinding.recyclerFilms.apply {
            adapter = filmAdapter
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(MyItemDecorator(RecyclerView.VERTICAL))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = ListFilmFragmentBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData(typeFilm)
        with(viewBinding){
            backButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
    }
}