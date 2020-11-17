package pl.polsl.workflow.manager.client.ui.manager

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_manager.*
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.ui.base.BaseActivity

class ManagerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager)
        setSupportActionBar(managerActivityToolbar)
        val navView: BottomNavigationView = findViewById(R.id.managerActivityNavigationView)
        val navController = findNavController(R.id.managerActivityFragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_task_manager, R.id.navigation_account_manager))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.managerActivityFragment).navigateUp()

}