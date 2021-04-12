package com.uzlov.moviefind.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar


import com.uzlov.moviefind.R
import com.uzlov.moviefind.databinding.FragmentHomeBinding
import com.uzlov.moviefind.interfaces.IOnClickListenerAdapter
import com.uzlov.moviefind.model.PopularFilms
import com.uzlov.moviefind.ui.FilmAdapter
import com.uzlov.moviefind.viewmodels.AppStateFilms
import com.uzlov.moviefind.viewmodels.FilmsViewModel

class HomeFragment : Fragment() , IOnClickListenerAdapter{

    private lateinit var adapterPopularFilms: FilmAdapter
    private lateinit var adapterTopFilms: FilmAdapter
    private var _viewBinding: FragmentHomeBinding ?= null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: FilmsViewModel by lazy {
        ViewModelProvider(this).get(FilmsViewModel::class.java)
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
        adapterTopFilms = FilmAdapter(this)
        adapterPopularFilms = FilmAdapter(this)
        loadData()
    }

    fun loadData(){
        viewModel.getTopRatedFilms().observe(viewLifecycleOwner, {
            renderDataTopFilms(it)
        })

        viewModel.getPopularFilms().observe(viewLifecycleOwner, {
            renderDataPopularFilms(it)
        })
    }



    private fun renderDataTopFilms(state: AppStateFilms){
        when(state){
            is AppStateFilms.Success -> showDataTopFilms(state.filmsData)
            is AppStateFilms.Loading -> showLoading()
            is AppStateFilms.Error -> showError(state.error)
        }
    }

    private fun renderDataPopularFilms(state: AppStateFilms){
        when(state){
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

    private fun showDataTopFilms(films: PopularFilms) {
        adapterTopFilms.setFilms(films.results)
        viewBinding.recommendRV.apply {
            adapter = adapterTopFilms
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun showLoading() {

    }

    private fun showError(error: Throwable) {
        Snackbar.make(viewBinding.root, error.message ?: "Empty error", Snackbar.LENGTH_SHORT).show()
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
    }
}

