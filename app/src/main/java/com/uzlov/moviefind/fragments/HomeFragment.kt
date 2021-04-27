package com.uzlov.moviefind.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.uzlov.moviefind.R
import com.uzlov.moviefind.databinding.FragmentHomeBinding
import com.uzlov.moviefind.interfaces.IOnClickListenerAdapter
import com.uzlov.moviefind.model.PopularFilms
import com.uzlov.moviefind.ui.FilmAdapter
import com.uzlov.moviefind.ui.MyItemDecorator
import com.uzlov.moviefind.viewmodels.AppStateFilms
import com.uzlov.moviefind.viewmodels.FilmsViewModel
import com.uzlov.moviefind.viewmodels.TypeFilms

class HomeFragment : Fragment(), IOnClickListenerAdapter , SharedPreferences.OnSharedPreferenceChangeListener{

    private var enableAdult: Boolean = false
    private val adapterPopularFilms by lazy { FilmAdapter(this) }
    private val adapterTopFilms by lazy { FilmAdapter(this) }
    private val adapterUpcomingFilms by lazy { FilmAdapter(this) }
    private val adapterSearchedFilms by lazy { FilmAdapter(this) }
    private var _viewBinding: FragmentHomeBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: FilmsViewModel by lazy {
        ViewModelProvider(this).get(FilmsViewModel::class.java)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (count == 0) {
                viewBinding.scrollContainer.visibility = View.VISIBLE
                viewBinding.searchRV.visibility = View.GONE
            }
            viewModel.getFilmsByName(s.toString(), enableAdult).observe(viewLifecycleOwner, { state ->
                renderSearchResult(state)
            })
        }
        override fun afterTextChanged(s: Editable?) {}
    }

    private val onClickCallback = View.OnClickListener {
        when(it){
            viewBinding.morePopularTV -> {
                val fragment = ListFilmsFragment.getInstance(TypeFilms.PopularFilm)
                parentFragmentManager.beginTransaction().run {
                    hide(this@HomeFragment)
                    add(R.id.fragment_container, fragment)
                    addToBackStack(null)
                    commit()
                }
            }
            viewBinding.moreRecommendTV -> {
                val fragment = ListFilmsFragment.getInstance(TypeFilms.RecommendFilm)
                parentFragmentManager.beginTransaction().run {
                    hide(this@HomeFragment)
                    add(R.id.fragment_container, fragment)
                    addToBackStack(null)
                    commit()
                }
            }
            viewBinding.moreUpComingTV -> {
                val fragment = ListFilmsFragment.getInstance(TypeFilms.UpcomingFilm)
                parentFragmentManager.beginTransaction().run {
                    hide(this@HomeFragment)
                    add(R.id.fragment_container, fragment)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

    private fun renderSearchResult(state: AppStateFilms){
        when (state) {
            is AppStateFilms.Error -> {
                // stub
            }
            AppStateFilms.Loading -> {
                showLoading()
            }
            is AppStateFilms.Success -> {
                viewBinding.scrollContainer.visibility = View.GONE
                viewBinding.searchRV.visibility = View.VISIBLE
                showSearchResultFilm(state.filmsData)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceManager.getDefaultSharedPreferences(requireContext()).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        loadData()
        PreferenceManager.getDefaultSharedPreferences(requireContext()).registerOnSharedPreferenceChangeListener(this)
        viewBinding.nameFilmSearchTv.addTextChangedListener(textWatcher)
    }

    private fun initListeners() {
        with(viewBinding){
            morePopularTV.setOnClickListener(onClickCallback)
            moreUpComingTV.setOnClickListener(onClickCallback)
            moreRecommendTV.setOnClickListener(onClickCallback)
        }
    }

    fun loadData() {
        viewModel.getTopRatedFilms().observe(viewLifecycleOwner, {
            renderDataTopFilms(it)
        })

        viewModel.getPopularFilms().observe(viewLifecycleOwner, {
            renderDataPopularFilms(it)
        })

        viewModel.getUpcomingFilms().observe(viewLifecycleOwner, {
            renderUpcomingFilms(it)
        })
    }

    private fun renderUpcomingFilms(state: AppStateFilms) {
        when (state) {
            is AppStateFilms.Success -> showUpcomingFilms(state.filmsData)
            is AppStateFilms.Loading -> showLoading()
            is AppStateFilms.Error -> showError(state.error)
        }
    }

    private fun showUpcomingFilms(films: PopularFilms) {
        viewBinding.upcomingRV.apply {
            adapterUpcomingFilms.setFilms(films.results)
            adapter = adapterUpcomingFilms
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun renderDataTopFilms(state: AppStateFilms) {
        when (state) {
            is AppStateFilms.Success -> showRecommendedFilms(state.filmsData)
            is AppStateFilms.Loading -> showLoading()
            is AppStateFilms.Error -> showError(state.error)
        }
    }

    private fun renderDataPopularFilms(state: AppStateFilms) {
        when (state) {
            is AppStateFilms.Success -> showDataPopularFilms(state.filmsData)
            is AppStateFilms.Loading -> showLoading()
            is AppStateFilms.Error -> showError(state.error)
        }
    }

    private fun showDataPopularFilms(films: PopularFilms) {
        viewBinding.popularRV.apply {
            adapterPopularFilms.setFilms(films.results)
            adapter = adapterPopularFilms
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun showSearchResultFilm(films : PopularFilms) {
        adapterSearchedFilms.setFilms(films.results)
        viewBinding.searchRV.apply {
            adapter = adapterSearchedFilms
            layoutManager = GridLayoutManager(requireContext(), 3, RecyclerView.VERTICAL, false)
            addItemDecoration(MyItemDecorator(RecyclerView.VERTICAL))
        }
    }

    private fun showRecommendedFilms(films: PopularFilms) {
        adapterTopFilms.setFilms(films.results)
        viewBinding.recommendRV.apply {
            adapter = adapterTopFilms
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun showLoading() {}

    private fun showError(error: Throwable) {
        Snackbar.make(viewBinding.root, error.message ?: "Empty error", Snackbar.LENGTH_SHORT)
            .show()
    }

    override fun onClick(position: Int, id: Int) {
        parentFragmentManager.beginTransaction().run {
            hide(this@HomeFragment)
            add(R.id.fragment_container, FilmFragment.newInstance(id))
            addToBackStack(null)
            commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
        PreferenceManager.getDefaultSharedPreferences(requireContext()).unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sPref: SharedPreferences?, key: String) {
        when(key){
            "adult" -> {
                enableAdult = sPref?.getBoolean(key, false) ?: false
            }
        }
    }
}

