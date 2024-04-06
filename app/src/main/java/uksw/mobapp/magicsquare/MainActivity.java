package uksw.mobapp.magicsquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private int mGameLevel = 0;
    private String mUserName = "";
    public final static int RES_PARAMETER_CODE = 1;

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null && intent.getExtras() != null) {
                        String userName = intent.getStringExtra("userName");
                        mUserName = userName;
                        int gameLevel = intent.getIntExtra("gameLevel", 0);
                        mGameLevel = gameLevel;
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void parameter(View v) {
        Intent intent = new Intent(this, ParameterActivity.class);
        intent.putExtra("gameLevel", mGameLevel);
        intent.putExtra("userName", mUserName);
        mStartForResult.launch(intent);
    }

    public void playGame(View v) {
        Intent intent = new Intent(this, PlayGameActivity.class);
        intent.putExtra("gameLevel", mGameLevel);
        intent.putExtra("userName", mUserName);
        startActivity(intent);
    }

    public void about(View v) {
//        Intent intent = new Intent(this, PlayGameActivity.class);
//        startActivity(intent);
    }

    public void quit(View v) {
        this.finish();
    }
}