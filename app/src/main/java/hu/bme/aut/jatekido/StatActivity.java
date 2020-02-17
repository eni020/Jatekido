package hu.bme.aut.jatekido;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerTitleStrip;
import androidx.viewpager.widget.ViewPager;

import java.util.Calendar;

public class StatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("pref", 0);
        if (!pref.contains("password")) {
            Intent intent = new Intent(StatActivity.this, PasswordActivity.class);
            startActivity(intent);
        }
        displayStatData();

        ImageButton settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatActivity.this, PasswordActivity.class);
                startActivity(intent);
            }
        });
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -5);
        if (pref.contains("phone")){
            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(pref.getString("phone", ""),null,"Ma a gyerek " + cal.getTimeInMillis() + " ezredmásodpercet játszott!",null,null);
        }
    }

    private void displayStatData() {
        ViewPager mainViewPager = findViewById(R.id.mainViewPager);
        StatsPagerAdapter statsPagerAdapter =
                new StatsPagerAdapter(getSupportFragmentManager(), this);
        mainViewPager.setAdapter(statsPagerAdapter);
        PagerTitleStrip pagerTitleStrip = findViewById(R.id.pagerTitleStrip);
        pagerTitleStrip.setNonPrimaryAlpha(0);
    }


}
