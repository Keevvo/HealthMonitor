package kevinmaiani.lam2020.healthmonitor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import kevinmaiani.lam2020.healthmonitor.Models.Report;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportHolder> {

    private List<Report> reports = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public ReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_item, parent, false);
        return new ReportHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportHolder holder, int position) {
        Report currentReport = reports.get(position);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String reportCalendarDate = formatter.format(currentReport.getCreationDate());
        holder.creationDate.setText(reportCalendarDate);
        holder.bodyTemperaturePriority.setText(String.valueOf(currentReport.getBodyTemperatureLevel()));
        holder.bloodPressurePriority.setText(String.valueOf(currentReport.getBloodPressureLevel()));
        holder.bloodPressureText.setText("Pressione Sanguigna: " + currentReport.getBloodPressure());
        holder.bodyTemperatureText.setText("Temperatura corporea:" + currentReport.getBodyTemperature());
        holder.note.setText((currentReport.getNote() == null || currentReport.getNote().isEmpty()) ? "" : currentReport.getNote()); //TOGLIERE QUESTO CONTROLLO SUL NULLO DI NOTE ESSENDO CHE NEL XML NON C'è IMPOSTATO VALORE?
    }

    @Override
    public int getItemCount() {
        return reports.size();
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
        notifyDataSetChanged();
    }

    public Report getReportAt(int position) {
        return reports.get(position);
    }

    class ReportHolder extends RecyclerView.ViewHolder {
        private TextView creationDate;
        private TextView bodyTemperaturePriority;
        private TextView bloodPressurePriority;
        private TextView bodyTemperatureText;
        private TextView bloodPressureText;
        private TextView note;

        public ReportHolder(View itemView) {
            super(itemView);
            creationDate = itemView.findViewById(R.id.text_view_creationDate);
            bodyTemperatureText = itemView.findViewById(R.id.text_view_bodyTemperature);
            bloodPressureText = itemView.findViewById(R.id.text_view_bloodPressure);
            bodyTemperaturePriority = itemView.findViewById(R.id.text_view_bodyTemperature_priority);
            bloodPressurePriority = itemView.findViewById(R.id.text_view_bloodPressure);
            note = itemView.findViewById(R.id.text_view_note);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(reports.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Report report);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
