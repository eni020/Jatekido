package hu.bme.aut.jatekido;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashSet;
import java.util.Set;

public class StatsWeeklyFragment extends Fragment {
    private TextView tvName;

    private TextView tvStat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats,
                container, false);
        tvName = view.findViewById(R.id.tvName);
        tvStat = view.findViewById(R.id.tvStat);
        showStatData();
        return view;
    }

    private void showStatData() {
        final SharedPreferences pref = getActivity().getSharedPreferences("pref", 0);
        if(pref.contains("apps")) {
            String names = "";
            String stats = "";
            Set<String> vals = pref.getStringSet("apps", new HashSet<String>());
            double i = 5.0;
            for (String val : vals) {
                names += val;
                names += "\n";
                stats += i*3;
                stats += "\n";
                i -= 0.5;
            }
            tvName.setText(names);
            tvStat.setText(stats);
        }

    }
}
