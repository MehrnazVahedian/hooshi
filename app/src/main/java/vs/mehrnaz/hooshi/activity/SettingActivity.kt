package vs.mehrnaz.hooshi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import vs.mehrnaz.hooshi.R
import android.os.Build
import android.graphics.drawable.Drawable
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import vs.mehrnaz.hooshi.databinding.ActivitySettingBinding
import vs.mehrnaz.hooshi.utils.Inputs
import vs.mehrnaz.hooshi.utils.PreferenceManager
import vs.mehrnaz.hooshi.utils.requiredField
import vs.mehrnaz.hooshi.utils.setStatusBarGradient
import vs.mehrnaz.hooshi.viewModels.SettingsViewModel

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var viewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)

        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        setStatusBarGradient()

        binding.settingToolbar.toolbarSimpleBackImageButton.setOnClickListener {
            onBackPressed()
        }
        binding.settingToolbar.toolbarSimpleMenuImageButton.visibility = View.INVISIBLE
        binding.settingToolbar.toolbarSimpleTitleTextView.text = getString(R.string.Setting)

        val previousInput = PreferenceManager(applicationContext).checkIntPreference(R.string.input_key)

        binding.settingInputSpinner.adapter = ArrayAdapter(baseContext, R.layout.spinner_item,Inputs.values())
        binding.settingInputSpinner.setSelection(previousInput)

        binding.settingIdTextInputEditText.setText(PreferenceManager(applicationContext).checkLongPreference(R.string.id_key).toString())
        binding.settingUserNameTextInputEditText.setText(PreferenceManager(applicationContext).checkPreference(R.string.user_name_key))
        binding.settingMinTextInputEditText.setText(PreferenceManager(applicationContext).checkLongPreference(R.string.min_key).toString())
        binding.settingMaxTextInputEditText.setText(PreferenceManager(applicationContext).checkLongPreference(R.string.max_key).toString())
        binding.settingRowTextInputEditText.setText(PreferenceManager(applicationContext).checkLongPreference(R.string.rows_key).toString())



        binding.buttonSetting.setOnClickListener {
            if (
                binding.settingIdTextInputEditText.requiredField(getString(R.string.id)) &&
                binding.settingMinTextInputEditText.requiredField(getString(R.string.min)) &&
                binding.settingMaxTextInputEditText.requiredField(getString(R.string.max)) &&
                binding.settingRowTextInputEditText.requiredField(getString(R.string.rows))
            ){
                viewModel.saveSetting(
                    binding.settingUserNameTextInputEditText.text.toString(),
                    binding.settingIdTextInputEditText.text.toString().toLong(),
                    binding.settingMinTextInputEditText.text.toString().toLong(),
                    binding.settingMaxTextInputEditText.text.toString().toLong(),
                    binding.settingRowTextInputEditText.text.toString().toLong(),
                    binding.settingInputSpinner.selectedItemPosition)

                if(previousInput != binding.settingInputSpinner.selectedItemPosition)
                    PreferenceManager(applicationContext).removePreferences("data_history")

                startActivity(Intent(baseContext,MainActivity::class.java))
                finish()
            }
        }

    }
}