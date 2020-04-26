package kevinmaiani.lam2020.healthmonitor;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import kevinmaiani.lam2020.healthmonitor.Database.ReportViewModel;
import kevinmaiani.lam2020.healthmonitor.Models.User;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //    private TextView tvUser;
    private DrawerLayout drawer;
    private CalendarView mCalendarView;
    private User user;

    private ReportViewModel reportViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toogle);
        toogle.syncState();

        if (savedInstanceState == null ) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalendarFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_calendar);
        }

        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);

        user = (User) getIntent().getSerializableExtra("User");

//        mCalendarView.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                Date date = null;
//                date = new Date(year, month, dayOfMonth);
//                Toast.makeText(getApplicationContext(), date.toString(), Toast.LENGTH_SHORT);
//            }
//        });
        //tvUser = findViewById(R.id.tvUser);

        if (user != null) {
            //tvUser.setText("BENVENUTO "+user.getName() +" "+user.getLastName());
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer((GravityCompat.START));
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_calendar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CalendarFragment()).commit();
                break;
            case R.id.nav_report:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ReportFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}