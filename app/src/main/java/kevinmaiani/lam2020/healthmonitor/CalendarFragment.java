package kevinmaiani.lam2020.healthmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import kevinmaiani.lam2020.healthmonitor.Database.ReportViewModel;
import kevinmaiani.lam2020.healthmonitor.Database.UserReportDetails;
import kevinmaiani.lam2020.healthmonitor.Models.Report;
import kevinmaiani.lam2020.healthmonitor.Models.User;

import static android.app.Activity.RESULT_OK;

public class CalendarFragment extends Fragment {
    public static final int ADD_REPORT_REQUEST = 1;
    private CalendarView mCalendarView;

    private ReportViewModel reportViewModel;
    private User user;

    public static CalendarFragment newInstance(User user) {
        CalendarFragment calendarFragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putSerializable("User", user);
        calendarFragment.setArguments(args);
        return calendarFragment;
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("User", user);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("User");
            reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);
        }

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        //Bundle bundle = getArguments(); //è null perchè onCreateView viene chiamato da UserActivity quando crea la navbar quindi forse bisogna mettere la roba del bundle tirasi su l'user in un metodo in cui il fragmnet è gia stato creato tipo onCreated
        mCalendarView = v.findViewById(R.id.reportCalendar);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getContext(), user.getName() , Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(getActivity(), AddReportActivity.class);
//                startActivityForResult(intent, ADD_REPORT_REQUEST);
//                // display the selected date by using a toast
//                Date currentDate = new Date(year, month, dayOfMonth);
//                Report report = new Report(currentDate, 2,3,4,5, "", user.getId());
//
//                reportViewModel.insert(report);
//                List<Report> reportsUser = reportViewModel.findReporyByUserId(user.getId());
//                List<Report> reports = reportViewModel.findReportForUser(user.getId(), currentDate);
//                //List<UserReportDetails> userReportsList = (List<UserReportDetails>) reportViewModel.getUsersReport();
//                Toast.makeText(getContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });
//        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                Toast.makeText(getContext(), user.getName() , Toast.LENGTH_LONG).show();
////                Intent intent = new Intent(getActivity(), AddReportActivity.class);
////                startActivityForResult(intent, ADD_REPORT_REQUEST);
////                // display the selected date by using a toast
////                Date currentDate = new Date(year, month, dayOfMonth);
////                Report report = new Report(currentDate, 2,3,4,5, "", user.getId());
////
////                reportViewModel.insert(report);
////                List<Report> reportsUser = reportViewModel.findReporyByUserId(user.getId());
////                List<Report> reports = reportViewModel.findReportForUser(user.getId(), currentDate);
////                //List<UserReportDetails> userReportsList = (List<UserReportDetails>) reportViewModel.getUsersReport();
////                Toast.makeText(getContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
//            }
//        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) { //method invoked when fragment return from backstack
        super.onActivityCreated(savedInstanceState);

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        getActivity();
//        if(requestCode == ADD_REPORT_REQUEST && resultCode == RESULT_OK) {
////            String title = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
////            String description = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
////            int priority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY, 1);
////
////            Note note = new Note(title,description,priority);
////            noteViewModel.insert(note);
//
//            Toast.makeText(getContext(), "Note Saved", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(getContext(), "Note not saved", Toast.LENGTH_SHORT).show();
//        }
//
//    }
}
