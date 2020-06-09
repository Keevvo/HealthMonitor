package kevinmaiani.lam2020.healthmonitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kevinmaiani.lam2020.healthmonitor.Database.ReportViewModel;
import kevinmaiani.lam2020.healthmonitor.Models.Report;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Date;
import java.util.List;

public class ReportFragment extends Fragment {
    private static final int EDIT_REPORT_REQUEST = 2;

    private ReportViewModel reportViewModel;
    private RecyclerView recyclerView;
    private ReportAdapter adapter;
    private int userId;

    public static ReportFragment newInstance() {
        return (new ReportFragment());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        SharedPreferences sharedpreferences = this.getActivity().getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        this.userId = sharedpreferences.getInt("userId",0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reports, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        adapter = new ReportAdapter();
        recyclerView.setAdapter(adapter);
        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);

        reportViewModel.getAllReports().observe(getViewLifecycleOwner(), new Observer<List<Report>>() {
            @Override
            public void onChanged(@Nullable List<Report> reports) {
                //update RecyclerView
                adapter.setReports(reports);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                reportViewModel.delete(adapter.getReportAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Report eliminato", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(report -> {
            Intent intent = new Intent(getActivity(), AddEditReportActivity.class);
            intent.putExtra(AddEditReportActivity.EXTRA_ID, report.getId());
            intent.putExtra(AddEditReportActivity.EXTRA_DATE, report.getCreationDate());
            intent.putExtra(AddEditReportActivity.EXTRA_BLOODPRESSURE, report.getBloodPressure());
            intent.putExtra(AddEditReportActivity.EXTRA_BODYTEMPERATURE, report.getBodyTemperature());
            intent.putExtra(AddEditReportActivity.EXTRA_BLOODPRESSUREPRIORITY, report.getBloodPressureLevel());
            intent.putExtra(AddEditReportActivity.EXTRA_BODYTEMPERATUREPRIORITY, report.getBodyTemperatureLevel());
            intent.putExtra(AddEditReportActivity.EXTRA_NOTE, report.getNote());
            startActivityForResult(intent, EDIT_REPORT_REQUEST);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_REPORT_REQUEST && resultCode == getActivity().RESULT_OK) {
            int id = data.getIntExtra(AddEditReportActivity.EXTRA_ID, -1);

            if(id == -1){
                Toast.makeText(getContext(), "Il report non può essere modificato", Toast.LENGTH_SHORT).show();
            }
            else {
                int bodyTemperature = data.getIntExtra(AddEditReportActivity.EXTRA_BODYTEMPERATURE,0);
                int bloodPressure = data.getIntExtra(AddEditReportActivity.EXTRA_BLOODPRESSURE,0);
                int bodyTemperaturePriority = data.getIntExtra(AddEditReportActivity.EXTRA_BODYTEMPERATUREPRIORITY, 0);
                int bloodPressurePriority = data.getIntExtra(AddEditReportActivity.EXTRA_BLOODPRESSUREPRIORITY, 0);
                String note = data.getStringExtra(AddEditReportActivity.EXTRA_NOTE);
                Date calendarDate = (Date) data.getSerializableExtra(AddEditReportActivity.EXTRA_DATE);

                Report report = new Report(calendarDate, bloodPressurePriority, bloodPressure, bodyTemperature, bodyTemperaturePriority, note, userId);
                report.setId(id);
                reportViewModel.update(report);
                Toast.makeText(getContext(), "Report modificato correttamente", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.report_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_reports:
                reportViewModel.deleteAllReports();
                Toast.makeText(getContext(), "Report cancellati", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.max_priority:
                reportViewModel.findMaxPriorityReports(userId).observe(getViewLifecycleOwner(), reports -> {
                    adapter.setReports(reports);
                });
                return true;
            case R.id.all_reports:
                reportViewModel.getAllReports().observe(getViewLifecycleOwner(), reports -> adapter.setReports(reports));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}