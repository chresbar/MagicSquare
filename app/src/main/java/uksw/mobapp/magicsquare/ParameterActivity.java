package uksw.mobapp.magicsquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ParameterActivity extends AppCompatActivity {

    EditText mUserNameInput;

    SeekBar mGameLevelInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_parameter);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mUserNameInput = findViewById(R.id.userNameInput);

        mGameLevelInput = findViewById(R.id.gameLevel);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            int gameLevel = intent.getIntExtra("gameLevel", 0);
            mGameLevelInput.setProgress(gameLevel);

            String userName = intent.getStringExtra("userName");
            if (userName.isEmpty())
                mUserNameInput.setHint("User Name");
            else
                mUserNameInput.setHint(userName);
        }
    }

    public void save(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("gameLevel", mGameLevelInput.getProgress());
        returnIntent.putExtra("userName", mUserNameInput.getText().toString());
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    public void back(View v) {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}