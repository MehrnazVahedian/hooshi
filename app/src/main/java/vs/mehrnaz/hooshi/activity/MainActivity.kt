package vs.mehrnaz.hooshi.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import vs.mehrnaz.hooshi.R
import vs.mehrnaz.hooshi.databinding.ActivityMainBinding
import vs.mehrnaz.hooshi.viewModels.MainViewModel
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //chane Status Toolbar Background to gradient
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            val background =  ContextCompat.getDrawable(baseContext,R.drawable.gray_gradient_no_corner) //bg_gradient is your gradient.
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(android.R.color.transparent)
            window.setBackgroundDrawable(background)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        binding.mainToolbar.toolbarSimpleMenuImageButton.visibility = View.VISIBLE
        binding.mainToolbar.toolbarSimpleMenuImageButton.setOnClickListener(View.OnClickListener { v: View? ->
            val popup = PopupMenu(this@MainActivity, v)
            popup.menuInflater.inflate(R.menu.pop_up, popup.menu)
            popup.setOnMenuItemClickListener { item: MenuItem ->
                if (item.itemId == R.id.aboutItemMenu) {
                    startActivity(Intent(this@MainActivity, AboutActivity::class.java))
                } else if (item.itemId == R.id.settingItemMenu) {
                    startActivity(Intent(this@MainActivity, SettingActivity::class.java))
                }
                true
            }
            popup.show() //showing popup menu
        })
        binding.mainToolbar.toolbarSimpleBackImageButton.visibility = View.GONE
        binding.mainToolbar.toolbarSimpleTitleTextView.text = getString(R.string.app_name)

        val timerHandler = Handler()
        val timerRunnable: Runnable = object : Runnable {
            override fun run() {
                viewModel.getLastData()

                timerHandler.postDelayed(this, 5000)
            }
        }


        timerHandler.postDelayed(timerRunnable, 0)


        viewModel.lastData.observe(this, {
//            Toast.makeText(baseContext, it, Toast.LENGTH_LONG).show()

            updateDonut(it.toFloat())
        })

        viewModel.dataHistory.observe(this, {
//            Toast.makeText(baseContext, it, Toast.LENGTH_LONG).show()

            updateLineChart(it)
        })

        viewModel.loadDefaultData()

    }

    private fun updateDonut(value: Float){
        val entryList: MutableList<PieEntry> = ArrayList()
        entryList.add(PieEntry(value / 100))
        entryList.add(PieEntry(100-value / 100))
        val dataSet = PieDataSet(entryList, "test")
        val colors: MutableList<Int> = ArrayList()
        colors.add(Color.rgb(255, 0, 0))
        colors.add(Color.rgb(85, 85, 85))
        dataSet.colors = colors
        dataSet.setDrawValues(false)
        dataSet.valueTextSize = 850f
        val pieData = PieData(dataSet)
        //        data.setDrawValues(false);
        binding.activityMainDounatChart.data = pieData
        binding.activityMainDounatChart.isRotationEnabled = false
        binding.activityMainDounatChart.description.isEnabled = false
        binding.activityMainDounatChart.rotationAngle = 90f
        binding.activityMainDounatChart.isHighlightPerTapEnabled = false
        binding.activityMainDounatChart.animateY(2400, Easing.EaseInOutQuad)
        binding.activityMainDounatChart.holeRadius = 85f
        binding.activityMainDounatChart.transparentCircleRadius = 86f
        binding.activityMainDounatChart.setHoleColor(ContextCompat.getColor(this, R.color.gray_light))

        binding.activityMainDonutValueTextView.text = value.toString().subSequence(0,4)
    }

    private fun updateLineChart(data: List<Float>){
        val values = ArrayList<Entry>()
        Log.d("LineChart" , "values added: "+ data.size)

        for ( i in data.indices){
            values.add(Entry(i.toFloat() +1 , data[i]))
            Log.d("LineChart" , "value added: "+ data[i])
        }

        val set1: LineDataSet
        if (binding.activityMainLineChart.data != null &&
                binding.activityMainLineChart.data.dataSetCount > 0) {
            set1 = binding.activityMainLineChart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            binding.activityMainLineChart.data.notifyDataChanged()
            binding.activityMainLineChart.notifyDataSetChanged()
        } else {
            set1 = LineDataSet(values, "Sample Data")
            set1.setDrawIcons(false)
            set1.enableDashedLine(10f, 5f, 0f)
            set1.enableDashedHighlightLine(10f, 5f, 0f)
            set1.color = Color.DKGRAY
            set1.setCircleColor(Color.DKGRAY)
            set1.lineWidth = 1f
            set1.circleRadius = 3f
            set1.setDrawCircleHole(false)
            set1.valueTextSize = 9f
            set1.setDrawFilled(true)
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f
            //            if (Utils.getSDKInt() >= 18) {
//                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.ic_cancel);
//                set1.setFillDrawable(drawable);
//            } else {
            set1.fillColor = Color.DKGRAY
            //            }
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1)
            val data = LineData(dataSets)
            binding.activityMainLineChart.data = data
        }
    }
}