package nl.project.newsreader2022

import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import nl.project.newsreader2022.databinding.ActivityMainBinding
import nl.project.newsreader2022.ui.fragments.LoginBottomSheet
import nl.project.newsreader2022.ui.fragments.LogoutBottomSheet
import nl.project.newsreader2022.viewModel.UserViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    private lateinit var loginBottomSheet: LoginBottomSheet
    private lateinit var logoutBottomSheet: LogoutBottomSheet
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()

        setSupportActionBar(binding.toolbar)
        binding.bottomNavMenu.setupWithNavController(navController)
        loginBottomSheet = LoginBottomSheet()
        logoutBottomSheet = LogoutBottomSheet()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.login) {
            // get auth token from data store
            userViewModel.authToken.asLiveData().observe(this) { token ->
                if (token == null) {
                    if (logoutBottomSheet.isVisible) {
                        logoutBottomSheet.dismiss()
                    }
                    loginBottomSheet.show(
                        supportFragmentManager,
                        "login"
                    )
                } else {
                    Log.d("token", token.AuthToken)
                    if (loginBottomSheet.isVisible)
                        loginBottomSheet.dismiss()
                    logoutBottomSheet.show(
                        supportFragmentManager,
                        "logout"
                    )
                }
            }
        }
        return item.onNavDestinationSelected(navController)
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }
}