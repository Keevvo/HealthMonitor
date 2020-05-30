package kevinmaiani.lam2020.healthmonitor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import kevinmaiani.lam2020.healthmonitor.Adapter.StatPageAdapter;


public class HomeStatFragment extends Fragment {

    View myFragment;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    public HomeStatFragment() {

    }

    public static HomeStatFragment newInstance() {
        return new HomeStatFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_home_stat, container, false);

        viewPager = myFragment.findViewById(R.id.viewPager);
        tabLayout = myFragment.findViewById(R.id.tabLayout);

        return myFragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_bar_chart);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_pie_chart);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        StatPageAdapter adapter = new StatPageAdapter(getChildFragmentManager());

        adapter.addFragment(new BarChartFragment(), "");
        adapter.addFragment(new PieChartFragment(), "");
        viewPager.setAdapter(adapter);

    }

}
