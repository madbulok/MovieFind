package com.uzlov.moviefind.fragments

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.uzlov.moviefind.R
import com.uzlov.moviefind.databinding.FragmentMapsBinding
import com.uzlov.moviefind.viewmodels.FilmsViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.IOException

class ActorFragment : Fragment() {

    private lateinit var mMap: GoogleMap
    private var idActor = 0
    private var _viewBinding: FragmentMapsBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val viewModel: FilmsViewModel by lazy {
        ViewModelProvider(this).get(FilmsViewModel::class.java)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        mMap = googleMap
        startLoadingActor()
    }

    companion object {
        fun newInstance(id: Int) : ActorFragment{
            val fragment = ActorFragment()
            val bundle = Bundle()
            bundle.putInt("id_actor", id)
            fragment.arguments = bundle
            return fragment
        }
    }

    private fun startLoadingActor() {
        viewModel.getActorDescriptionsById(idActor).observe(viewLifecycleOwner, {
            with(viewBinding){
                tvNameActor.text = it.name
                description.text = it.biography
            }
            runBlocking {
                getPositionByAddress(it.place_of_birth)
            }
        })
    }

    private suspend fun getPositionByAddress(address : String){
        GlobalScope.launch {
            val gecoder = Geocoder(requireContext())
            try {
                val addresses = gecoder.getFromLocationName(address, 1)
                if (addresses.size > 0) {
                    goToAddress(addresses, address)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    private fun goToAddress(addresses: List<Address>, address: String) {
        val location = LatLng(
            addresses.first().latitude,
            addresses.first().longitude
        )
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            mMap.addMarker(MarkerOptions().position(location).title(address))
            mMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    location,
                    15f
                )
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments.let {
            idActor = it?.getInt("id_actor") ?: -1
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentMapsBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        viewBinding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}