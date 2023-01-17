package com.example.wuphf.ui


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.wuphf.ui.favoritesFragment.FavoritesFragment
import com.example.wuphf.R
import com.example.wuphf.ui.allDogsFragment.SwipingFragment
import com.example.wuphf.databinding.ActivityMainBinding
import com.example.wuphf.ui.faqFragment.FaqsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var drawer: DrawerLayout
    lateinit var binding : ActivityMainBinding
    lateinit var root : DrawerLayout
    var tutorialFlag : Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        root = binding.root
        setContentView(root)

        initUI()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun initUI() {
        openFragment(SwipingFragment(), "Opening the Swiping Fragment")
        initToolbar()
        initTutorial()
    }

    fun openFragment(fragment: Fragment, tag: String) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_container, fragment, tag)
        transaction.addToBackStack(tag)
        transaction.commit()
    }

    private fun initToolbar() {
        val toolbar = binding.toolbar
        drawer = binding.drawerLayout

        val navigationView = binding.navView

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    openFragment(SwipingFragment(), "Opening the Swiping Fragment")
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START)
                    }
                    true
                }
                R.id.nav_favorites-> {
                    openFragment(FavoritesFragment(), "Opening the favorites Fragment")
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START)
                    }
                    true
                }

                R.id.nav_faqs-> {
                    openFragment(FaqsFragment(), "Opening the Faqs Fragment")
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START)
                    }
                    true
                }

                R.id.nav_events-> {
                    openFragment(EventsFragment(), "Opening the Events Fragment")
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START)
                    }
                    true
                }

                else -> {
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START)
                    }
                    false
                }
            }
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun initTutorial() {
        if (tutorialFlag == null) {
            Toast.makeText(this, "Swipe left to browse through dogs.", Toast.LENGTH_LONG).show()
            Toast.makeText(this, "Swipe right to add a dog to favorites.", Toast.LENGTH_LONG).show()
            Toast.makeText(this, "Enjoy! :)", Toast.LENGTH_LONG).show()
            tutorialFlag = true
        }
    }
}
