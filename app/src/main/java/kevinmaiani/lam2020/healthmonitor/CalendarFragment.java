package kevinmaiani.lam2020.healthmonitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import kevinmaiani.lam2020.healthmonitor.Database.ReportViewModel;
import kevinmaiani.lam2020.healthmonitor.Models.Report;
import kevinmaiani.lam2020.healthmonitor.Models.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarFragment extends Fragment {

    public static final int ADD_REPORT_REQUEST = 1;
    private CalendarView mCalendarView;

    private Date calendarDate;
    private User user;
    private int userId;
    private ReportViewModel reportViewModel;


    public static CalendarFragment newInstance(User user) {
        CalendarFragment calendarFragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putSerializable("User", user);
        calendarFragment.setArguments(args);
        return calendarFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("User");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        this.mCalendarView = view.findViewById((R.id.reportCalendar));
        return (view);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            user = (User) savedInstanceState.getSerializable("KEY");
        }
        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        this.userId = sharedpreferences.getInt("userId", 0);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                calendarDate = new GregorianCalendar(year, month, dayOfMonth).getTime();
                List<Report> reports = reportViewModel.findReportForUser(userId, calendarDate);
                Intent intent = new Intent(getActivity(), AddEditReportActivity.class);
                startActivityForResult(intent, ADD_REPORT_REQUEST);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REPORT_REQUEST && resultCode == getActivity().RESULT_OK) {
            int bodyTemperature = data.getIntExtra(AddEditReportActivity.EXTRA_BODYTEMPERATURE, 0);
            int bloodPressure = data.getIntExtra(AddEditReportActivity.EXTRA_BLOODPRESSURE, 0);
            int bodyTemperaturePriority = data.getIntExtra(AddEditReportActivity.EXTRA_BODYTEMPERATUREPRIORITY, 0);
            int bloodPressurePriority = data.getIntExtra(AddEditReportActivity.EXTRA_BLOODPRESSUREPRIORITY, 0);
            String note = data.getStringExtra(AddEditReportActivity.EXTRA_NOTE);

            Report summaryReport = new Report();
            List<Report> reports = reportViewModel.findReportForUser(userId, calendarDate);
            Report report = new Report(calendarDate, bloodPressurePriority, bloodPressure, bodyTemperature, bodyTemperaturePriority, note, userId);
            if (reports.size() == 0) {
                reportViewModel.insert(report);
            } else {
                Report old = reports.get(0);
                Report average = new Report();
                average.setCreationDate(report.getCreationDate());
                average.setUserId(userId);
                average.setBodyTemperature((old.getBodyTemperature() + report.getBodyTemperature()) / 2);
                average.setBloodPressure((old.getBloodPressure() + report.getBloodPressure()) / 2);
                average.setBloodPressureLevel((old.getBloodPressureLevel() + report.getBloodPressureLevel() / 2));
                average.setBodyTemperatureLevel((old.getBodyTemperatureLevel() + report.getBodyTemperatureLevel() / 2));
                reportViewModel.delete(old);
                reportViewModel.insert(average);
            }
            Toast.makeText(getActivity(), "Report salvato", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Report non salvato", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Save buttonTag in Bundle when fragment is destroyed
        outState.putSerializable("KEY", user);
    }

}