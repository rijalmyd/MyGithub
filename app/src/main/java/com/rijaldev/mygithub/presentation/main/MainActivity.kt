package com.rijaldev.mygithub.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.switchmaterial.SwitchMaterial
import com.rijaldev.mygithub.R
import com.rijaldev.mygithub.databinding.ActivityMainBinding
import com.rijaldev.mygithub.vm.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpDrawer()
        setUpDarkMode()
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    private fun setUpDrawer() {
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

    private fun setUpDarkMode() {
        with(binding) {
            val darkModeItem = navView.menu.findItem(R.id.btnDarkMode)
            val switchDarkMode = darkModeItem.actionView as SwitchMaterial

            switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setTheme(isChecked)
            }

            viewModel.getTheme().observe(this@MainActivity) { isDarkModeActive ->
                switchDarkMode.isChecked = isDarkModeActive
                AppCompatDelegate.setDefaultNightMode(
                    if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES
                    else AppCompatDelegate.MODE_NIGHT_NO
                )
                darkModeItem.icon = ContextCompat.getDrawable(
                    this@MainActivity,
                    if (isDarkModeActive) R.drawable.ic_light_mode else R.drawable.ic_night_mode
                )
            }
        }
    }
}