package kevinmaiani.lam2020.healthmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddReportActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(5);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Report");

    }
    private void saveReport() {
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        int priority = numberPickerPriority.getValue();

        if(title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT);
            return;
        }

        Intent data = new Intent();
//        data.putExtra(EXTRA_TITLE, title);
//        data.putExtra(EXTRA_DESCRIPTION, description);
//        data.putExtra(EXTRA_PRIORITY, priority); //da questa activity facciamo partire un intent verso un altra activity che quando verrà chiusa (onCLose()) passerà il risultato a questa activtiy

        setResult(RESULT_OK, data);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_report:
                saveReport();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
