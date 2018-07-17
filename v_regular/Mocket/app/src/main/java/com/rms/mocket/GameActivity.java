package com.rms.mocket;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wajahatkarim3.easyflipview.EasyFlipView;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final EasyFlipView easyFlipView1 = (EasyFlipView) findViewById(R.id.GAME_easyFlipView_answer1);
        easyFlipView1.setFlipDuration(500);
        easyFlipView1.setFlipEnabled(true);
        TextView answer1_front = (TextView) findViewById(R.id.GAME_textView_answer1_front);
        final TextView answer1_back = (TextView) findViewById(R.id.GAME_textView_answer1_back);
        TextView answer2_front = (TextView) findViewById(R.id.GAME_textView_answer2_front);
        final TextView answer2_back = (TextView) findViewById(R.id.GAME_textView_answer2_back);
        TextView answer3_front = (TextView) findViewById(R.id.GAME_textView_answer3_front);
        final TextView answer3_back = (TextView) findViewById(R.id.GAME_textView_answer3_back);
        TextView answer4_front = (TextView) findViewById(R.id.GAME_textView_answer4_front);
        final TextView answer4_back = (TextView) findViewById(R.id.GAME_textView_answer4_back);

        answer1_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView1.flipTheView();
                markIncorrect(answer1_back);

            }
        });

        answer1_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView1.flipTheView();
            }
        });



        /* Answer 2 FlipView */

        final EasyFlipView easyFlipView2 = (EasyFlipView) findViewById(R.id.GAME_easyFlipView_answer2);
        easyFlipView2.setFlipDuration(500);
        easyFlipView2.setFlipEnabled(true);

        answer2_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView2.flipTheView();
                markCorrect(answer2_back);

            }
        });

        answer2_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView2.flipTheView();
            }
        });



        /* Answer 3 FlipView */

        final EasyFlipView easyFlipView3 = (EasyFlipView) findViewById(R.id.GAME_easyFlipView_answer3);
        easyFlipView3.setFlipDuration(500);
        easyFlipView3.setFlipEnabled(true);

        answer3_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView3.flipTheView();
                markIncorrect(answer3_back);

            }
        });

        answer3_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView3.flipTheView();
            }
        });



        /* Answer 4 FlipView */

        final EasyFlipView easyFlipView4 = (EasyFlipView) findViewById(R.id.GAME_easyFlipView_answer4);
        easyFlipView4.setFlipDuration(500);
        easyFlipView4.setFlipEnabled(true);

        answer4_front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView4.flipTheView();
                markIncorrect(answer4_back);


            }
        });

        answer4_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                easyFlipView4.flipTheView();
            }
        });

    }

    public void markCorrect(TextView v){
        v.setText("Correct :)");
        v.setTextSize(20);
        v.setTextColor(Color.GREEN);
        Button button_next = findViewById(R.id.GAME_button_next);
        button_next.setVisibility(View.VISIBLE);

    }

    public void markIncorrect(TextView v){
        v.setText("Incorrect :(");
        v.setTextSize(20);
        v.setTextColor(Color.RED);
    }

    public void goPreviousActivity(View v){
        this.finish();
    }
}