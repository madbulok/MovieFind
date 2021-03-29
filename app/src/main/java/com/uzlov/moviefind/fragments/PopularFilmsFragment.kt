package com.uzlov.moviefind.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uzlov.moviefind.R
import com.uzlov.moviefind.databinding.PopularFilmsFragmentBinding
import com.uzlov.moviefind.model.TestFilm
import com.uzlov.moviefind.ui.MyItemDecorator
import com.uzlov.moviefind.ui.PopularFilmsAdapter
import com.uzlov.moviefind.viewmodels.FilmsViewModel

class PopularFilmsFragment : Fragment(), OnClickListenerAdapter {

    private lateinit var viewModel: FilmsViewModel
    private var _viewBinding: PopularFilmsFragmentBinding?=null
    private val viewBinding get() = _viewBinding!!
    private val films:ArrayList<TestFilm> = ArrayList()
    private lateinit var popularFilmsAdapter : PopularFilmsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinding = PopularFilmsFragmentBinding.inflate(layoutInflater, container, false)
        return  viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularFilmsAdapter = PopularFilmsAdapter(this)
        viewModel = ViewModelProvider(this).get(FilmsViewModel::class.java)
        viewModel.getPopularFilms().observe(viewLifecycleOwner, {
            showLoadedFilms(it)
            films.apply {
                clear()
                addAll(it)
            }
        })
    }

    private fun showLoadedFilms(list: List<TestFilm>) {
        popularFilmsAdapter.setTestFilms(list)
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

    override fun onClick(position: Int) {
        parentFragmentManager.beginTransaction().run {
            hide(this@PopularFilmsFragment)
            replace(R.id.fragment_container, FilmFragment.newInstance(films[position]))
            addToBackStack(null)
            commit()
        }
    }


}
interface OnClickListenerAdapter{
    fun onClick(position: Int)
}