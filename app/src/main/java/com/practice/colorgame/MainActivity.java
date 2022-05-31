package com.practice.colorgame;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.practice.colorgame.databinding.ActivityMainBinding;
import com.practice.colorgame.databinding.GameOverBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityMainBinding b;
    int greyIndex;
    int score;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        b.start.setOnClickListener(view -> {
            b.start.setVisibility(View.GONE);

            b.box1.setOnClickListener(this);
            b.box2.setOnClickListener(this);
            b.box3.setOnClickListener(this);
            b.box4.setOnClickListener(this);

            b.box1.setEnabled(true);
            b.box2.setEnabled(true);
            b.box3.setEnabled(true);
            b.box4.setEnabled(true);

            setTimer();
            greyIndex = generateRandomInt();
            setGrey(greyIndex);


        });


        b.help.setOnClickListener(view -> {
            View v = getLayoutInflater().inflate(R.layout.alert_bg, null);
            AlertDialog d = new AlertDialog.Builder(MainActivity.this)
                    .setView(v)
                    .show();

            Button b = v.findViewById(R.id.close);
            b.setOnClickListener(view1 -> {
                d.dismiss();
            });
        });


    }

    private void setGrey(int value) {
        switch (value) {
            case 1:
                b.box1.setBackgroundColor(getResources().getColor(R.color.grey));
                break;
            case 2:
                b.box2.setBackgroundColor(getResources().getColor(R.color.grey));
                break;
            case 3:
                b.box3.setBackgroundColor(getResources().getColor(R.color.grey));
                break;
            case 4:
                b.box4.setBackgroundColor(getResources().getColor(R.color.grey));
                break;
        }
        timer.start();
    }

    @Override
    public void onClick(View view) {
            timer.cancel();
//        else
//            Toast.makeText(this, "null mp", Toast.LENGTH_SHORT).show();

        int value = 0;
        switch (view.getId()) {
            case R.id.box1:
                value = 1;
                break;
            case R.id.box2:
                value = 2;
                break;
            case R.id.box3:
                value = 3;
                break;
            case R.id.box4:
                value = 4;
                break;
        }

//        Toast.makeText(this, "value = "+value+"\ngreyIndex = "+greyIndex, Toast.LENGTH_SHORT).show();
        if (value == greyIndex) {
            score++;
            b.score.setText(score + "");
            resetViews();
            greyIndex = generateRandomInt();
            setGrey(greyIndex);
        } else {
            gameOver("CLICKED WRONG BOX");
        }
    }

    @SuppressLint("SetTextI18n")
    private void gameOver(String text) {
        Toast.makeText(this, "gamer over "+text, Toast.LENGTH_SHORT).show();
        View v = getLayoutInflater().inflate(R.layout.game_over, null);
        AlertDialog d = new AlertDialog.Builder(this)
                .setView(v)
                .setCancelable(false)
                .show();

        GameOverBinding bi = GameOverBinding.bind(v);
        bi.gs.setText("YOUR SCORE : " + score);
        bi.reason.setText(text);
        bi.gt.setText("Congratulations! you clicked the " + score + " boxes successfully");
        bi.ge.setOnClickListener(view -> finish());
        bi.gr.setOnClickListener(view -> {
            d.cancel();
            score = 0;
            b.score.setText("0");
            b.start.setVisibility(View.VISIBLE);
            resetViews();

//           finish();
//           startActivity(getIntent());
        });
    }

    private void resetViews() {
        b.box1.setBackgroundColor(getResources().getColor(R.color.orange));
        b.box2.setBackgroundColor(getResources().getColor(R.color.skyBlue));
        b.box3.setBackgroundColor(getResources().getColor(R.color.yellow));
        b.box4.setBackgroundColor(getResources().getColor(R.color.green));

    }

    private int generateRandomInt() {
        Random rand = new Random(); //instance of random class
        int upperbound = 4;
        //generate random values from 0-3
        return (rand.nextInt(upperbound) + 1);
    }

    private void setTimer() {
        timer = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                gameOver("TIME FINISHED");
                b.box1.setEnabled(false);
                b.box2.setEnabled(false);
                b.box3.setEnabled(false);
                b.box4.setEnabled(false);
            }
        };
    }
}