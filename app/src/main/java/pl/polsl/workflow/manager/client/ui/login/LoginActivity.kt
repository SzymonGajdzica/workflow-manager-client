package pl.polsl.workflow.manager.client.ui.login

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import pl.polsl.workflow.manager.client.R
import pl.polsl.workflow.manager.client.ui.base.BaseActivity

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setSupportActionBar(findViewById(R.id.loginActivityToolbar))
        NavigationUI.setupActionBarWithNavController(this, findNavController(R.id.loginActivityFragment))
    }

    override fun onSupportNavigateUp(): Boolean = findNavController(R.id.loginActivityFragment).navigateUp()

}