package kevinmaiani.lam2020.healthmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import kevinmaiani.lam2020.healthmonitor.Database.UserDao;
import kevinmaiani.lam2020.healthmonitor.Database.UserDatabase;
import kevinmaiani.lam2020.healthmonitor.Models.User;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtLastName;
    private EditText edtEmail;
    private EditText edtPassword;

    private Button btCancel;
    private Button btRegister;

    private UserDao userDao;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registering...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);

        edtName = findViewById(R.id.nameinput);
        edtLastName = findViewById(R.id.lastnameinput);
        edtEmail = findViewById(R.id.emailinput);
        edtPassword = findViewById(R.id.passwordinput);

        btCancel = findViewById(R.id.btCancel);
        btRegister = findViewById(R.id.btRegister);

        userDao = Room.databaseBuilder(this, UserDatabase.class, "mi-database.db")
                .allowMainThreadQueries()
                .build()
                .getUserDao();

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEmpty()) {

                    progressDialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            User user = new User(edtName.getText().toString(), edtLastName.getText().toString(),
                                    edtEmail.getText().toString(), edtPassword.getText().toString());
                            userDao.insert(user);
                            progressDialog.dismiss();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    }, 1000);

                } else {
                    Toast.makeText(getApplicationContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isEmpty() {
        if (TextUtils.isEmpty(edtEmail.getText().toString()) ||
                TextUtils.isEmpty(edtPassword.getText().toString()) ||
                TextUtils.isEmpty(edtName.getText().toString()) ||
                TextUtils.isEmpty(edtLastName.getText().toString())
        ) {
            return true;
        } else {
            return false;
        }
    }
}
