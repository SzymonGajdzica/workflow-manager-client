package pl.polsl.workflow.manager.client.ui.worker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_worker.*
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.hasLocationPermission
import pl.polsl.workflow.manager.client.utils.LocationReader
import pl.polsl.workflow.manager.client.utils.LocationReaderImpl

class WorkerActivity : AppCompatActivity() {

    private val locationReader: LocationReader = LocationReaderImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker)
        setSupportActionBar(workerActivityToolbar)
        val navView: BottomNavigationView = findViewById(R.id.workerActivityNavigationView)
        val navController = findNavController(R.id.workerActivityFragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_task_worker, R.id.navigation_account_worker
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.workerActivityFragment).navigateUp()

    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        if(hasLocationPermission())
            locationReader.startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        locationReader.stopLocationUpdates()
    }

}