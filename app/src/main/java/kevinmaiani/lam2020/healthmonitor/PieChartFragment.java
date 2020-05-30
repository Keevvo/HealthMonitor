package kevinmaiani.lam2020.healthmonitor;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import kevinmaiani.lam2020.healthmonitor.Database.ReportViewModel;
import kevinmaiani.lam2020.healthmonitor.Models.Report;
import kevinmaiani.lam2020.healthmonitor.R;

public class PieChartFragment extends Fragment {

    private PieChart pieChart;
    private ReportViewModel reportViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        this.pieChart = view.findViewById(R.id.pieChart);
        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);

        initializeChart();
        return (view);
    }

    private void initializeChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(false);
        pieChart.setHighlightPerTapEnabled(false);

        pieChart.animateY(1400, Easing.EaseInOutQuad);
        pieChart.getLegend().setEnabled(false);

        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(12f);

        data();
    }

    private void data() {
        reportViewModel.getAllReports().observe(getViewLifecycleOwner(), (reports) -> onReportDataChange(reports) );

    }

    private void onReportDataChange(List<Report> reports) {
        List<PieEntry> entries = new ArrayList<>();

        List<Integer> highTempList = new ArrayList<>();

        List<Report> filteredReports = reports.stream().filter(p -> p.getBodyTemperature() > 37).collect(Collectors. toList());

        filteredReports.forEach( (report) -> highTempList.add(report.getBodyTemperature()));

        PieEntry highTemperatureEntry = new PieEntry(highTempList.size(), "Febbre");
        PieEntry lowTemperatuatreEntry = new PieEntry(reports.size()- highTempList.size(), "Regolare");
        entries.add(highTemperatureEntry);
        entries.add(lowTemperatuatreEntry);
        PieDataSet dataSet = new PieDataSet(entries, "Presenza di febbre(temperature corporea > 37)");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(1f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        List<Integer> colors = new ArrayList<>();

        colors.add(Color.argb((float) 0.6, 0,255,0));
        colors.add(Color.argb((float) 0.6, 255, 23,51));

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);

        pieChart.highlightValues(null);

        pieChart.invalidate();
    }
}
