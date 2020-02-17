package hu.bme.aut.jatekido;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Calendar;
import java.util.List;

public class StatsPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    List<UsageStats> stats;

    public StatsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

    }

    // This method is called whenever the adapter needs a Fragment for a certain position
    @Override
    public Fragment getItem(int position) {
        UsageStatsManager usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

        Fragment ret = null;
        switch (position) {
            case 0:
                Calendar today = Calendar.getInstance();

                ret = new StatsDailyFragment();
                break;
            case 1:
                ret = new StatsWeeklyFragment();
                break;
            case 2:
                stats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST, 0, System.currentTimeMillis());
                ret = new StatsOvertimeFragment();
                break;
        }
        return ret;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        switch (position) {
            case 0:
                title = context.getString(R.string.napi);
                break;
            case 1:
                title = context.getString(R.string.heti);
                break;
            case 2:
                title = context.getString(R.string.osszesitett);
                break;
            default:
                title = "";
        }
        return title;
    }

    @Override
    public int getCount() {
        return 3;
    }



}
