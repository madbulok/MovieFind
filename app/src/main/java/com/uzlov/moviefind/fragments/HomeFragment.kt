package com.uzlov.moviefind.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager


import com.uzlov.moviefind.R
import com.uzlov.moviefind.databinding.FragmentHomeBinding
import com.uzlov.moviefind.interfaces.IOnClickListenerAdapter
import com.uzlov.moviefind.ui.FilmAdapter
import com.uzlov.moviefind.viewmodels.FilmsViewModel

class HomeFragment : Fragment() , IOnClickListenerAdapter{
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
        loadData()
    }

    private fun loadData(){
        val adapterTopFilms = FilmAdapter(this)
        viewModel.getTopRatedFilms().observe(viewLifecycleOwner, {
            adapterTopFilms.setFilms(it.results)
            viewBinding.recommendRV.apply {
                adapter = adapterTopFilms
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        })

        val adapterPopularFilms = FilmAdapter(this)
        viewModel.getPopularFilms().observe(viewLifecycleOwner, {
            viewBinding.popularRV.apply {
                adapterPopularFilms.setFilms(it.results)
                adapter = adapterPopularFilms
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
        })
    }

    override fun onClick(position: Int, id: Int) {
        parentFragmentManager.beginTransaction().run {
            hide(this@HomeFragment)
            add(R.id.fragment_container, FilmFragment.newInstance(id))
            addToBackStack(null)
            commit()
        }
    }
}