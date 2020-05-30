package kevinmaiani.lam2020.healthmonitor;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import kevinmaiani.lam2020.healthmonitor.Database.ReportViewModel;
import kevinmaiani.lam2020.healthmonitor.Models.Report;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class BarChartFragment extends Fragment {

    public BarChartFragment() {
    }

    private ReportViewModel reportViewModel;
    private BarChart barChart;

    public static BarChartFragment newInstance() {
        return (new BarChartFragment());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stat_bar_chart, container, false);
        this.barChart = view.findViewById((R.id.barChart));
        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);

        initializeChart();
        return (view);
    }

    private void initializeChart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDragEnabled(true);
        barChart.setVisibleXRangeMaximum(3);
        barChart.setNoDataText(""); //delete in case of errors in data
        barChart.animateY(2000, Easing.EaseInOutQuad);

        data();
    }

    private void data() {
        reportViewModel.getAllReports().observe(getViewLifecycleOwner(), (reports) -> onReportDataChange(reports));
    }

    private void onReportDataChange(List<Report> reports) {
        ArrayList<BarEntry> bloodPressureEntries = new ArrayList<>();
        ArrayList<BarEntry> bodyTemperatureEntries = new ArrayList<>();
        ArrayList<String> reportDate = new ArrayList<>();

        for (int i = 0; i < reports.size(); i++) {
            BarEntry bloodPressureEntry = new BarEntry(i, reports.get(i).getBloodPressure());
            BarEntry bodyTemperatureEntry = new BarEntry(i, reports.get(i).getBodyTemperature());

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            reportDate.add(formatter.format(reports.get(i).getCreationDate()));
            bodyTemperatureEntries.add(bodyTemperatureEntry);
            bloodPressureEntries.add(bloodPressureEntry);
        }

        //#region DataSet
        BarDataSet bloodPressureSet = new BarDataSet(bloodPressureEntries, "Pressione Sanguigna");
        bloodPressureSet.setColors(ColorTemplate.MATERIAL_COLORS);
        bloodPressureSet.setColor(Color.argb((float) 0.6, 0, 255, 0));

        BarDataSet bodyTemperatureSet = new BarDataSet(bodyTemperatureEntries, "Temperatura Corporea");
        bodyTemperatureSet.setColors(ColorTemplate.MATERIAL_COLORS);
        bodyTemperatureSet.setColor(Color.argb((float) 0.6, 255, 23, 51));

        BarData data = new BarData(bloodPressureSet, bodyTemperatureSet);
        barChart.setData(data);
        //#endregion

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(reportDate));
        xAxis.setTextSize(12f);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        float barSpace = 0.13f;
        float groupSpace = 0.14f;
        data.setBarWidth(0.30f);
        data.setValueTextSize(12f);

        barChart.setFitBars(true);
        barChart.getXAxis().setAxisMinimum(0);
        barChart.getXAxis().setAxisMaximum(0 + barChart.getBarData().getGroupWidth(groupSpace, barSpace) * reports.size());
        barChart.getAxisLeft().setAxisMinimum(0);
        barChart.getAxisRight().setAxisMinimum(0);

        barChart.groupBars(0, groupSpace, barSpace);

        barChart.invalidate();
    }
}