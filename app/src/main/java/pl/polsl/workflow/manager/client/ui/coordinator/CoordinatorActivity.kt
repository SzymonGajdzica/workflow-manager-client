package pl.polsl.workflow.manager.client.ui.coordinator

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_coordinator.*
import pl.polsl.workflow.manager.client.R

class CoordinatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)
        setSupportActionBar(coordinatorActivityToolbar)
        val navView: BottomNavigationView = findViewById(R.id.coordinatorActivityNavigationView)
        val navController = findNavController(R.id.coordinatorActivityFragment)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_groups_coordinator,
                R.id.navigation_localizations_coordinator,
                R.id.navigation_account_coordinator
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.coordinatorActivityFragment).navigateUp()

}