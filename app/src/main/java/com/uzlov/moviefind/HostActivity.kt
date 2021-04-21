package com.uzlov.moviefind

import com.uzlov.moviefind.services.SampleService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.uzlov.moviefind.databinding.ActivityMainBinding
import com.uzlov.moviefind.fragments.*
import com.uzlov.moviefind.services.NetworkListener
import com.uzlov.moviefind.services.NetworkStateListener

class HostActivity : AppCompatActivity() {

    private lateinit var broadcastReceiver: NetworkStateListener
    private val popularFilmsFragment = FavoriteFilmsFragment()
    private val homeFragment = HomeFragment()
    private var activeFragment = Fragment()
    private var filmsFragmentL = FilmFragment()
    private var errorFragment = ErrorConnectionFragment()
    private var settingFragment = SettingsFragment()

    private var _viewBinding: ActivityMainBinding ?= null
    private val viewBinding get() = _viewBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        initFragments()

        viewBinding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    setActiveFragment(homeFragment)
                    true
                }
                R.id.menu_favorite-> {
                    setActiveFragment(popularFilmsFragment)
                    true
                }
                R.id.menu_settings -> {
                    setActiveFragment(settingFragment)
                    true
                }
                else -> false
            }
        }
        startNetworkListener()
    }

    private fun startNetworkListener(){
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        // слушатель состояния интернета
        broadcastReceiver = NetworkStateListener(object : NetworkListener {
            override fun networkStateChanged(isAvailable: Boolean) {
                updateStateNetwork(isAvailable)
            }
        })
        registerReceiver(broadcastReceiver, intentFilter)
    }

    private fun initFragments(){
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, homeFragment, "homeFragment").hide(homeFragment)
            add(R.id.fragment_container, popularFilmsFragment, "popularFilmsFragment").hide(popularFilmsFragment)
            add(R.id.fragment_container, filmsFragmentL, "filmsFragment").hide(filmsFragmentL)
            add(R.id.fragment_container, errorFragment, "errorFragment").hide(errorFragment)
            add(R.id.fragment_container, settingFragment, "settingFragment").hide(settingFragment)
            setReorderingAllowed(true)
        }.commit()

        setActiveFragment(homeFragment)
    }

    private fun setActiveFragment(fragment: Fragment) {
        if(supportFragmentManager.backStackEntryCount > 0) supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().hide(activeFragment).show(fragment).commit()
        activeFragment = fragment
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun updateStateNetwork(isAvailable: Boolean) {
        if (isAvailable) {
            setActiveFragment(homeFragment)
            homeFragment.loadData()
        } else {
            setActiveFragment(errorFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
        unregisterReceiver(broadcastReceiver)
    }

    /**
     *   Receiver sample data from com.uzlov.moviefind.services.SampleService.kt
     **/
    private inner class SampleReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent.let {
                Toast.makeText(context, intent?.getStringExtra(SampleService.VALUE_KEY), Toast.LENGTH_SHORT).show()
            }
        }
    }
}