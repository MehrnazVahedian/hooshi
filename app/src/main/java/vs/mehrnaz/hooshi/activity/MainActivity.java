package vs.mehrnaz.hooshi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.widget.Toast;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import it.ngallazzi.fancyswitch.FancyState;
import it.ngallazzi.fancyswitch.FancySwitch;
import vs.mehrnaz.hooshi.R;
import vs.mehrnaz.hooshi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        FancySwitch fancySwitch = findViewById(R.id.fancySwitchButton);
        fancySwitch.setSwitchStateChangedListener(new FancySwitch.StateChangedListener() {
              @Override
              public void onChanged(@NotNull FancyState fancyState) {
                  if( fancyState.getId().ordinal() == 0 ){
                      binding.textViewMainDisConnect.setText("Connected");
                      binding.imageViewMainDisconnect.setImageResource(R.drawable.ic_link);

                  } else {
                      binding.textViewMainDisConnect.setText("Disconnect");
                      binding.imageViewMainDisconnect.setImageResource(R.drawable.nolink);
                  }
              }
        });

        List < PieEntry > entryList = new ArrayList<>();
        entryList.add(new PieEntry(80f,"80"));
        entryList.add(new PieEntry(20f,"20"));

        PieDataSet dataSet = new PieDataSet(entryList,"test");

        List<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(0,85,85));
        colors.add(Color.rgb(85,0,0));

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);


        binding.activityMainChart.setData(data);


    }
}