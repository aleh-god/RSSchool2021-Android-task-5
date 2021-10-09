package by.godevelopment.rsschool2021_android_task_5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.godevelopment.rsschool2021_android_task_5.ui.main.MainFragment
import by.godevelopment.rsschool2021_android_task_5.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        setSupportActionBar(binding.toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
