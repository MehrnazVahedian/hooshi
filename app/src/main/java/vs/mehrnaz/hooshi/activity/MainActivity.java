package vs.mehrnaz.hooshi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import it.ngallazzi.fancyswitch.FancyState;
import it.ngallazzi.fancyswitch.FancySwitch;
import vs.mehrnaz.hooshi.LoginActivity;
import vs.mehrnaz.hooshi.R;
import vs.mehrnaz.hooshi.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        //chane Status Toolbar Background to gradient
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            Drawable background = getDrawable(R.drawable.gray_gradient_no_corner); //bg_gradient is your gradient.
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(android.R.color.transparent));
            window.setBackgroundDrawable(background);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
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

        binding.mainToolbar.toolbarSimpleMenuImageButton.setVisibility(View.VISIBLE);
        binding.mainToolbar.toolbarSimpleMenuImageButton.setOnClickListener((View.OnClickListener) v -> {

            PopupMenu popup = new PopupMenu(MainActivity.this, v);
            popup.getMenuInflater().inflate(R.menu.pop_up, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId()==R.id.aboutItemMenu){
                    startActivity(new Intent(MainActivity.this , AboutActivity.class));
                }
                else if (item.getItemId() == R.id.settingItemMenu){
                    startActivity(new Intent(MainActivity.this , SettingActivity.class));
                }
                else if (item.getItemId() == R.id.logoutItemMenu){
                  //  FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(MainActivity.this , LoginActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    finish();
                }

                return true;
            });
            popup.show();//showing popup menu
        });

        binding.mainToolbar.toolbarSimpleBackImageButton.setVisibility(View.GONE);
        binding.mainToolbar.toolbarSimpleTitleTextView.setText(getString(R.string.app_name));

    }
}