package kevinmaiani.lam2020.healthmonitor;

import androidx.appcompat.app.AppCompatActivity;
import kevinmaiani.lam2020.healthmonitor.Models.User;

import android.os.Bundle;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    private TextView tvUser;

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        user = (User) getIntent().getSerializableExtra("User");

        tvUser = findViewById(R.id.tvUser);

        if (user != null) {
            tvUser.setText("BENVENUTO "+user.getName() +" "+user.getLastName());
        }
    }
}