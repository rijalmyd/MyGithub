package com.rijaldev.mygithub.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.rijaldev.mygithub.R
import com.rijaldev.mygithub.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            setSupportActionBar(appBarMain.toolbar)

            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
            navController = navHostFragment.navController

            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.homeFragment, R.id.favoriteFragment,
                ),
                mainDrawer
            )

            setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}