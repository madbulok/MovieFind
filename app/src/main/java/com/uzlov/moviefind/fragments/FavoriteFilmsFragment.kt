package com.uzlov.moviefind.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.uzlov.moviefind.R
import com.uzlov.moviefind.database.FilmEntityDB
import com.uzlov.moviefind.databinding.PopularFilmsFragmentBinding
import com.uzlov.moviefind.interfaces.IOnClickListenerAdapter
import com.uzlov.moviefind.ui.MyItemDecorator
import com.uzlov.moviefind.ui.PopularFilmsAdapter
import com.uzlov.moviefind.viewmodels.FilmsViewModel

class FavoriteFilmsFragment : Fragment(), IOnClickListenerAdapter {

    private val viewModel: FilmsViewModel by lazy {
        ViewModelProvider(this).get(FilmsViewModel::class.java)
    }

    private var _viewBinding: PopularFilmsFragmentBinding?=null
    private val viewBinding get() = _viewBinding!!
    private var popularFilmsAdapter : PopularFilmsAdapter = PopularFilmsAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewBinding = PopularFilmsFragmentBinding.inflate(layoutInflater, container, false)
        return  viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMyFavoritesFilms().observe(viewLifecycleOwner, {
            showLoadedFilms(it)
        })
    }

    private fun showLoadedFilms(list: List<FilmEntityDB>) {
        popularFilmsAdapter.setFilms(list)
        viewBinding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = popularFilmsAdapter
            addItemDecoration(MyItemDecorator(RecyclerView.VERTICAL))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    override fun onClick(position: Int, id: Int) {
        parentFragmentManager.beginTransaction().run {
            hide(this@FavoriteFilmsFragment)
            add(R.id.fragment_container, FilmFragment.newInstance(id))
            addToBackStack(null)
            commit()
        }
    }
}