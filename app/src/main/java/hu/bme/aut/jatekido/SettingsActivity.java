package hu.bme.aut.jatekido;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {

    static  SharedPreferences pref;
    static EditTextPreference pw;
    static EditTextPreference phone;
    static MultiSelectListPreference apps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        pref = getApplicationContext().getSharedPreferences("pref", 0);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment(this))
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        Activity activity;

        public SettingsFragment(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            pw = findPreference("password");
            pw.setText(pref.getString("password", ""));

            phone = findPreference("phone");
            if(pref.contains("phone")) {
                phone.setText(pref.getString("phone", ""));
            }

            apps = findPreference("apps");
            final PackageManager packageManager = activity.getPackageManager();

            List<ApplicationInfo> installedApplications =
                    packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
            CharSequence[] appnames = new CharSequence[installedApplications.size()];
            int i = 0;
            for (ApplicationInfo appInfo : installedApplications) {
                appnames[i] = appInfo.loadLabel(packageManager);
                i++;

            }
            apps.setEntries(appnames);
            apps.setValues(new HashSet<String>());
            apps.setEntryValues(appnames);

            if(pref.contains("apps")) {
                Set<String> applikaciooook = pref.getStringSet("apps", new HashSet<String>());
                apps.setValues(applikaciooook);
            }
        }

        @Override
        public void onPause() {
            super.onPause();
            Set<String> vals = apps.getValues();
            final Editor editor = pref.edit();
            editor.putString("password", pw.getText());
            String pnumber =phone.getText();
            if (!pnumber.equals("nincs megadva")) {
                editor.putString("phone", pnumber);
            }
            editor.putStringSet("apps", vals);
            editor.commit();

        }

    }
}