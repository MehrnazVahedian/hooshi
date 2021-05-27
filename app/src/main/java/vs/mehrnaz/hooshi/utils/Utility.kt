package vs.mehrnaz.hooshi.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.os.Build
import android.provider.Settings.Secure
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import vs.mehrnaz.hooshi.R
import java.util.regex.Pattern


fun CharSequence.isPhoneNumber() : Boolean {
    val reg = "^09[0-9'@s]{9}\$"
    val pattern: Pattern = Pattern.compile(reg)
    return pattern.matcher(this).find()
}

fun CharSequence.isSolarYear() : Boolean {
    val reg = "^1[0-9'@s]{3}\$"
    val pattern: Pattern = Pattern.compile(reg)
    return pattern.matcher(this).find()
}


fun EditText.validatePhoneNumber() : Boolean {
    return if (! this.text.isPhoneNumber()) {
        this@validatePhoneNumber.error = "شماره موبایل را صحیح وارد کنید"
        false
    }
    else
        true
}

fun EditText.validateNationalCode() : Boolean {
    if (this.text.length == 10) {
        var sum = 0
        for (i in 0 .. 8)
            sum += Character.getNumericValue(this.text[i]) * (10-i)

        val lastDigit:Int
        val divideRemaining = sum % 11

        lastDigit = if (divideRemaining < 2) divideRemaining else 11 - (divideRemaining)

        return if (Character.getNumericValue(this.text[9]) == lastDigit)
            true
        else {
            this@validateNationalCode.error = "کد ملی را به درستی وارد کنید"
            false
        }
    }
    else {
        this@validateNationalCode.error = "کد ملی را به درستی وارد کنید"
        return false
    }
}

fun EditText.validateSolarYear() : Boolean {
    return if (! this.text.isSolarYear()) {
        this@validateSolarYear.error = "سال تولد را به درستی وارد کنید"
        false
    }
    else
        true
}

fun EditText.validatePassword(minChars: Int) : Boolean {
    return if (this.text.length < minChars) {
        this@validatePassword.error = " کلمه عبور باید حداقل $minChars کاراکتر باشد "
        false
    }
    else
        true
}

fun EditText.validateRetype(original: String) : Boolean {
    return if (this.text.toString() != original) {
        this@validateRetype.error = " کلمه عبور و تکرار آن یکسان نیستند "
        false
    }
    else
        true
}

fun EditText.requiredField(name: String) : Boolean {
    return if (this.text.toString() == "") {
        this@requiredField.error = "$name را وارد کنید "
        false
    }
    else
        true
}

fun Spinner.requiredField() : Boolean {
    return if (this.selectedItemPosition == 0) {
        Toast.makeText(context, this.selectedItem.toString(), Toast.LENGTH_LONG).show()
        false
    }
    else
        true
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit){
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.addToBackStack("a")
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int){
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.removeFragment(fragment: Fragment){
    supportFragmentManager.inTransaction { remove(fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int){
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

fun AppCompatActivity.showFragment(fragment: Fragment){
    supportFragmentManager.inTransaction { show(fragment) }
}

fun AppCompatActivity.hideFragment(fragment: Fragment){
    supportFragmentManager.inTransaction { hide(fragment) }
}

fun AppCompatActivity.setCustomAnimation(enterAnimation: Int, exitAnimation: Int){
    supportFragmentManager.inTransaction { setCustomAnimations(enterAnimation, exitAnimation) }
}

fun Fragment.showToastMessage(message: String?){
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
}

fun Activity.showToastMessage(message: String?){
    Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
}

fun Activity.hideSystemUI() {
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
            // Set the content to appear under the system bars so that the
            // content doesn't resize when the system bars hide and show.
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            // Hide the nav bar and status bar
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN)
}
fun Activity.showSystemUI() {
    window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_VISIBLE)
}
fun Activity.setStatusBarGradient(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = window
        val background =  ContextCompat.getDrawable(baseContext, R.drawable.gray_gradient_no_corner) //bg_gradient is your gradient.
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(android.R.color.transparent)
        window.setBackgroundDrawable(background)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

@BindingAdapter("avatar")
fun ImageView.avatar(img: Bitmap) {
    val round = RoundedBitmapDrawableFactory.create(resources, img)
    round.isCircular = true
    setImageDrawable(round)
}

@SuppressLint("HardwareIds")
fun Fragment.getAndroidId() : String{
    return Secure.getString(context!!.contentResolver, Secure.ANDROID_ID)
}

fun Fragment.timeFormat(hour: Int,min: Int): String{
    return "${hour.toString().padStart(2,'0')}:${min.toString().padStart(2,'0')}"
}
