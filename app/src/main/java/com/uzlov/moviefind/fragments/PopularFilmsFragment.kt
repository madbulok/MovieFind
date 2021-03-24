package com.uzlov.moviefind.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uzlov.moviefind.databinding.PopularFilmsFragmentBinding
import com.uzlov.moviefind.model.TestFilm
import com.uzlov.moviefind.ui.PopularFilmsAdapter
import com.uzlov.moviefind.viewmodels.FilmsViewModel

class PopularFilmsFragment : Fragment() {

    private lateinit var viewModel: FilmsViewModel
    private var _viewBinding: PopularFilmsFragmentBinding?=null
    private val viewBinding get() = _viewBinding!!

    private lateinit var popularFilmsAdapter : PopularFilmsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinding = PopularFilmsFragmentBinding.inflate(layoutInflater, container, false)
        return  viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularFilmsAdapter = PopularFilmsAdapter()
        viewModel = ViewModelProvider(this).get(FilmsViewModel::class.java)
        viewModel.getPopularFilms().observe(viewLifecycleOwner, {
            showLoadedFilms(it)
        })
    }

    private fun showLoadedFilms(list: List<TestFilm>) {
        popularFilmsAdapter.setTestFilms(list)
        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = popularFilmsAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}