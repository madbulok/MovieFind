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
import com.uzlov.moviefind.databinding.PopularFilmsFragmentBinding
import com.uzlov.moviefind.interfaces.IOnClickListenerAdapter
import com.uzlov.moviefind.model.Result
import com.uzlov.moviefind.ui.MyItemDecorator
import com.uzlov.moviefind.ui.PopularFilmsAdapter
import com.uzlov.moviefind.viewmodels.FilmsViewModel

class PopularFilmsFragment : Fragment(), IOnClickListenerAdapter {

    private val viewModel: FilmsViewModel by lazy {
        ViewModelProvider(this).get(FilmsViewModel::class.java)
    }

    private var _viewBinding: PopularFilmsFragmentBinding?=null
    private val viewBinding get() = _viewBinding!!
    private lateinit var popularFilmsAdapter : PopularFilmsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _viewBinding = PopularFilmsFragmentBinding.inflate(layoutInflater, container, false)
        return  viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    private fun View.showSnackBar(
        text : String,
        actionText: String,
        action: (View) -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        Snackbar.make(this, text, length).setAction(actionText, action).show()
    }
    private fun showLoadedFilms(list: List<Result>) {
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

    override fun onClick(position: Int, id: Int) {
        parentFragmentManager.beginTransaction().run {
            hide(this@PopularFilmsFragment)
            add(R.id.fragment_container, FilmFragment.newInstance(id))
            addToBackStack(null)
            commit()
        }
    }
}