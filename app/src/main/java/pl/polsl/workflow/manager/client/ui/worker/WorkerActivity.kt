package pl.polsl.workflow.manager.client.ui.worker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.hasLocationPermission
import pl.polsl.workflow.manager.client.utils.LocationReader
import pl.polsl.workflow.manager.client.utils.LocationReaderImpl

class WorkerActivity : AppCompatActivity() {

    private val locationReader: LocationReader = LocationReaderImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(mainActivityToolbar)
        val navView: BottomNavigationView = findViewById(R.id.mainActivityNavigationView)
        val navController = findNavController(R.id.mainActivityFragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_task, R.id.navigation_account, R.id.navigation_notifications
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.mainActivityFragment).navigateUp()

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