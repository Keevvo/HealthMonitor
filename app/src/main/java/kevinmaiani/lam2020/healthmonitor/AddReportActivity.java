package kevinmaiani.lam2020.healthmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import kevinmaiani.lam2020.healthmonitor.Database.ReportViewModel;

public class AddReportActivity extends AppCompatActivity {
    public static final String EXTRA_BODYTEMPERATURE =
            "kevinmaiani.lam2020.healthmonitor.EXTRA_BODYTEMPERATURE";

    public static final String EXTRA_BODYTEMPERATUREPRIORITY =
            "kevinmaiani.lam2020.healthmonitor.EXTRA_DESCRIPTION";

    public static final String EXTRA_BLOODPRESSURE =
            "kevinmaiani.lam2020.healthmonitor.EXTRA_BLOODPRESSURE";

    public static final String EXTRA_BLOODPRESSUREPRIORITY =
            "kevinmaiani.lam2020.healthmonitor.EXTRA_BLOODPRESSUREPRIORITY";

    public static final String EXTRA_NOTE =
            "kevinmaiani.lam2020.healthmonitor.EXTRA_NOTE";

    private EditText editTextBodyTemperature;
    private EditText editTextBloodPressure;
    private EditText editTextNote;
    private NumberPicker numberPickerBloodPressure;
    private NumberPicker numberPickerBodyTemperature;
    private Button btnSaveReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        editTextBodyTemperature = findViewById(R.id.edit_text_bodyTemperature);
        editTextBloodPressure = findViewById(R.id.edit_text_bloodPressure);
        editTextNote = findViewById(R.id.editText_note);
        numberPickerBloodPressure = findViewById(R.id.number_picker_bloodPressurePriority);
        numberPickerBodyTemperature = findViewById(R.id.number_picker_bodyTemperaturePriority);
        btnSaveReport = findViewById(R.id.btn_saveReport);

        numberPickerBloodPressure.setMinValue(1);
        numberPickerBloodPressure.setMaxValue(5);

        numberPickerBodyTemperature.setMinValue(1);
        numberPickerBodyTemperature.setMaxValue(5);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Report");

        btnSaveReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveReport();
            }
        });
    }

    private void saveReport() {
        String tempBodyTemperature = editTextBodyTemperature.getText().toString();
        int bodyTemperatureValue = 0;
        if (!"".equals(tempBodyTemperature)) {
            bodyTemperatureValue = Integer.parseInt(tempBodyTemperature);
        }
        String tempBloodPressure = editTextBloodPressure.getText().toString();
        int bloodPressureValue = 0;
        if (!"".equals(tempBodyTemperature)) {
            bloodPressureValue = Integer.parseInt(tempBloodPressure);
        }

        String note = editTextNote.getText().toString();
        int bodyTemperaturePriority = numberPickerBodyTemperature.getValue();
        int bloodPressurePriority = numberPickerBodyTemperature.getValue();

        Intent data = new Intent();
        data.putExtra(EXTRA_BODYTEMPERATURE, bodyTemperatureValue);
        data.putExtra(EXTRA_BODYTEMPERATUREPRIORITY, bodyTemperaturePriority);
        data.putExtra(EXTRA_BLOODPRESSURE, bloodPressureValue);
        data.putExtra(EXTRA_BLOODPRESSUREPRIORITY, bloodPressurePriority);
        data.putExtra(EXTRA_NOTE, note);
        setResult(RESULT_OK, data);
        finish();
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.save_report:
//                saveReport();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }//per bottone salva nel action bar
}

