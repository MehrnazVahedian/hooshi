package vs.mehrnaz.hooshi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import vs.mehrnaz.hooshi.R
import android.content.Intent
import android.os.Handler
import vs.mehrnaz.hooshi.activity.SettingActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }, 4000)
    }
}