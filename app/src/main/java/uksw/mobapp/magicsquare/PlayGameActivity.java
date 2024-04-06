package uksw.mobapp.magicsquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayGameActivity extends AppCompatActivity {

    private TextView mMsgView, mTitleView;
    private Button mSubmitButton, mNewGameButton, mHelpButton;
    private String mMsg = ".. .. ..";
    private int mGameLevel = 0;
    private ArrayList<Integer> mValueList = new ArrayList<>();
    private ArrayList<Integer> mRowSumValueList = new ArrayList<>();
    private ArrayList<Integer> mColSumValueList = new ArrayList<>();
    private ArrayList<TextView> mRowSumList = new ArrayList<>();
    private ArrayList<TextView> mColSumList = new ArrayList<>();
    private ArrayList<EditText> mInputSquares = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mMsgView = findViewById(R.id.msg);
        mMsgView.setText(mMsg);

        mTitleView = findViewById(R.id.title);

        mSubmitButton = findViewById(R.id.buttonSubmit);
        mSubmitButton.setEnabled(false);

        mNewGameButton = findViewById(R.id.buttonNewGame);

        mHelpButton = findViewById(R.id.buttonHelp);
        mHelpButton.setEnabled(false);

        mRowSumList.add(findViewById(R.id.sumRow0));
        mRowSumList.add(findViewById(R.id.sumRow1));
        mRowSumList.add(findViewById(R.id.sumRow2));

        mColSumList.add(findViewById(R.id.sumCol0));
        mColSumList.add(findViewById(R.id.sumCol1));
        mColSumList.add(findViewById(R.id.sumCol2));

        mInputSquares.add(findViewById(R.id.r0c0));
        mInputSquares.add(findViewById(R.id.r0c1));
        mInputSquares.add(findViewById(R.id.r0c2));

        mInputSquares.add(findViewById(R.id.r1c0));
        mInputSquares.add(findViewById(R.id.r1c1));
        mInputSquares.add(findViewById(R.id.r1c2));

        mInputSquares.add(findViewById(R.id.r2c0));
        mInputSquares.add(findViewById(R.id.r2c1));
        mInputSquares.add(findViewById(R.id.r2c2));

        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null) {
            mGameLevel = intent.getIntExtra("gameLevel", 0);

            String userName = intent.getStringExtra("userName");
            mTitleView.setText("Hello " + userName);
        }

    }

    public void submit(View v) {
        ArrayList<Integer> answers = new ArrayList<>();

        for (EditText editText: mInputSquares) {
            String input = editText.getText().toString();
            if (!input.isEmpty())
                answers.add(Integer.parseInt(input));
            else {
                mHelpButton.setEnabled(true);
                mMsgView.setText("Not all squares are filled");
                return;
            }
        }

        for (int i = 1; i <= 9; i++) {
            if (!answers.contains(i)) {
                mHelpButton.setEnabled(true);
                mMsg = "Digit " + i + " is missing";
                mMsgView.setText("There are missing digits");
                return;
            }
        }

        for (int i = 0; i < 3; ++i)
        {
            int rowValue = answers.get(i * 3) + answers.get((i * 3) + 1) + answers.get((i * 3) + 2);
            if (rowValue != mRowSumValueList.get(i))
            {
                mHelpButton.setEnabled(true);
                mMsg = "Sum of row " + (i + 1) + " is wrong";
                mMsgView.setText("Wrong row sums");
                return;
            }

            int colValue = answers.get(i) + answers.get(i + 3) + answers.get(i + 6);
            if (colValue != mColSumValueList.get(i))
            {
                mHelpButton.setEnabled(true);
                mMsg = "Sum of col " + (i + 1) + " is wrong";
                mMsgView.setText("Wrong col sums");
                return;
            }
        }

        mMsg = ".. .. ..";
        mMsgView.setText("Congratulations, you managed to solve it!");

        mSubmitButton.setEnabled(false);
        mHelpButton.setEnabled(false);
        mNewGameButton.setEnabled(true);
    }

    public void newGame(View v) {
        mSubmitButton.setEnabled(true);
        mNewGameButton.setEnabled(false);

        mMsg = ".. .. ..";
        mMsgView.setText(mMsg);

        for (EditText editText: mInputSquares) {
            editText.setText("");
        }

        mValueList.clear();
        for (int i = 1; i <= 9; ++i)
        {
            mValueList.add(i);
        }
        Collections.shuffle(mValueList);

        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < 9; ++i)
        {
            indexes.add(i);
        }
        Collections.shuffle(indexes);
        List<Integer> hints = indexes.subList(0, mGameLevel);

        for (int index: hints) {
            mInputSquares.get(index).setText(String.valueOf(mValueList.get(index)));
        }

        mRowSumValueList.clear();
        mColSumValueList.clear();
        for (int i = 0; i < 3; ++i)
        {
            int value = mValueList.get(i * 3) + mValueList.get((i * 3) + 1) + mValueList.get((i * 3) + 2);
            mRowSumValueList.add(value);
            mRowSumList.get(i).setText(String.valueOf(value));

            value = mValueList.get(i) + mValueList.get(i + 3) + mValueList.get(i + 6);
            mColSumValueList.add(value);
            mColSumList.get(i).setText(String.valueOf(value));
        }
    }
    public void help(View v) {
        mMsgView.setText(mMsg);
    }
    public void exit(View v) {
        finish();
    }
}