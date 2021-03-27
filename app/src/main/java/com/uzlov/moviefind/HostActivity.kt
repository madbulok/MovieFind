package com.uzlov.moviefind

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.uzlov.moviefind.databinding.ActivityMainBinding
import com.uzlov.moviefind.fragments.FilmFragment
import com.uzlov.moviefind.fragments.HomeFragment
import com.uzlov.moviefind.fragments.PopularFilmsFragment

class HostActivity : AppCompatActivity() {

    private val popularFilmsFragment = PopularFilmsFragment()
    private val homeFragment = HomeFragment()
    private var activeFragment = Fragment()
    private var filmsFragmentL = FilmFragment()

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
                    Toast.makeText(this, getString(R.string.in_developing), Toast.LENGTH_SHORT).show()
                    false
                }
                else -> false
            }
        }
    }

    private fun initFragments(){
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, homeFragment, "homeFragment").hide(homeFragment)
            add(R.id.fragment_container, popularFilmsFragment, "popularFilmsFragment").hide(popularFilmsFragment)
            add(R.id.fragment_container, filmsFragmentL, "filmsFragmentL").hide(filmsFragmentL)
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

    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}