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

public class AddEditReportActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "kevinmaiani.lam2020.healthmonitor.EXTRA_ID"; //serve per l'edit del report

    public static final String EXTRA_DATE =
            "kevinmaiani.lam2020.healthmonitor.EXTRA_DATE"; //serve per l'edit del report

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

    private int userId;
    private ReportViewModel reportViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);


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

        Intent intent = getIntent();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        final Date currentDate = (Date)intent.getSerializableExtra(EXTRA_DATE);
        if(intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Report");
            editTextBodyTemperature.setText(String.valueOf(intent.getIntExtra(EXTRA_BODYTEMPERATURE,1)));
            editTextBloodPressure.setText(String.valueOf(intent.getIntExtra(EXTRA_BLOODPRESSURE,1)));
            numberPickerBodyTemperature.setValue(intent.getIntExtra(EXTRA_BODYTEMPERATUREPRIORITY, 1));
            numberPickerBloodPressure.setValue(intent.getIntExtra(EXTRA_BLOODPRESSUREPRIORITY,1));
            editTextNote.setText(intent.getStringExtra(EXTRA_NOTE));
        }
        else {
            setTitle("Add Note");
        }

        btnSaveReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveReport(currentDate);
            }
        });
    }

    private void saveReport(Date currentDate) {
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
        int bloodPressurePriority = numberPickerBloodPressure.getValue();
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        this.userId = sharedpreferences.getInt("userId", 0);


        Intent data = new Intent();
        data.putExtra(EXTRA_BODYTEMPERATURE, bodyTemperatureValue);
        data.putExtra(EXTRA_BODYTEMPERATUREPRIORITY, bodyTemperaturePriority);
        data.putExtra(EXTRA_BLOODPRESSURE, bloodPressureValue);
        data.putExtra(EXTRA_BLOODPRESSUREPRIORITY, bloodPressurePriority);
        data.putExtra(EXTRA_NOTE, note);
        data.putExtra(EXTRA_DATE, currentDate);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if(id != 1) {
            data.putExtra(EXTRA_ID, id);
        }

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

