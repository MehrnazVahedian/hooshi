package vs.mehrnaz.hooshi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import vs.mehrnaz.hooshi.R
import android.content.Intent
import android.os.Handler
import android.os.Looper
import vs.mehrnaz.hooshi.activity.SettingActivity
import vs.mehrnaz.hooshi.utils.PreferenceManager
import vs.mehrnaz.hooshi.utils.setStatusBarGradient

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        setStatusBarGradient()

        Handler(Looper.getMainLooper()).postDelayed({
            if (PreferenceManager(applicationContext).checkLongPreference(R.string.id_key) == 0L)
            {
                val i = Intent(this@SplashScreenActivity, SettingActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
                finish()
            }
            else{
                val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(i)
                finish()
            }

        }, 4000)
    }
}