package pl.polsl.workflow.manager.client.ui.coordinator

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_coordinator.*
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.ui.base.BaseActivity

class CoordinatorActivity : BaseActivity() {

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