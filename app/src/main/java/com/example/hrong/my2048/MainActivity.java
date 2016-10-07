package com.example.hrong.my2048;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private TextView tvScore;
    private int score = 0;
    private Button btRestart,btAnotherActivity;
    private GameView gameView;
    public static MainActivity mainActivity = null;
    public MainActivity() {
        mainActivity =this ;
    }
    public static MainActivity getMainActivity() {
        return mainActivity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameView= (GameView) findViewById(R.id.mygameView);
        tvScore = (TextView) findViewById(R.id.tvScore);
        btRestart = (Button) findViewById(R.id.btRestart);
        btAnotherActivity= (Button) findViewById(R.id.btanotherActivity);
        btAnotherActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getMainActivity(),AnotherActivity.class));
            }
        });
        btRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getMainActivity()).setTitle("Restart").setMessage("Are you sure you wish to reset the game").setNegativeButton("CANCEL",null).setPositiveButton("ReStart", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameView.startGame();
                        clearScore();
                    }
                }).show();

            }
        });
    }


    public void addScore(int addscore) {
        score += addscore;
        showScore();
    }

    private void showScore() {
        tvScore.setText(score + "");
    }

    public void clearScore() {
        score = 0;
        showScore();
    }

    public int getScore() {
        return score;
    }
}
