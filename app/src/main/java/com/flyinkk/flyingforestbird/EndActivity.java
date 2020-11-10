package com.flyinkk.flyingforestbird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private Button restart_btn;
    private Button exit_btn;
    private Intent intent;
    private int score;
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        textView = findViewById(R.id.end_score);
        restart_btn = findViewById(R.id.restart_btn);
        restart_btn.setOnClickListener(this);
        exit_btn = findViewById(R.id.exit_btn);
        exit_btn.setOnClickListener(this);
        mainActivity = new MainActivity();
        intent = getIntent();
        score = intent.getIntExtra(mainActivity.SCORE_KEY, 0);
        textView.setText(String.format(getString(R.string.score), score));
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == restart_btn.getId()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if (v.getId() == exit_btn.getId()) {
            System.exit(0);
        }
    }
}