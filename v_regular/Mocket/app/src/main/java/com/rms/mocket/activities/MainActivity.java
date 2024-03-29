package com.rms.mocket.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rms.mocket.R;
import com.rms.mocket.common.AlarmReceiver;
import com.rms.mocket.fragments.GraphFragment;
import com.rms.mocket.fragments.MemoryFragment;
import com.rms.mocket.fragments.MoreFragment;
import com.rms.mocket.fragments.QuizFragment;
import com.rms.mocket.object.User;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private static final String ID_KEY = "android_id";

    final String MEMORY_FRAGMENT_TAG = "memory";
    final String QUIZ_FRAGMENT_TAG = "quiz";
    final String GRAPH_FRAGMENT_TAG = "graph";
    final String MORE_FRAGMENT_TAG = "more";

    String previous_fragment = MEMORY_FRAGMENT_TAG;
    Fragment memory_fragment;
    Fragment quiz_fragment;
    Fragment graph_fragment;
    Fragment more_fragment;

    FragmentTransaction fragment_transaction;
    FragmentManager fragment_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        memory_fragment = new MemoryFragment();
        quiz_fragment = new QuizFragment();
        graph_fragment = new GraphFragment();
        more_fragment = new MoreFragment();

//        memory_fragment.onCreate(savedInstanceState);
//        quiz_fragment.onCreate(savedInstanceState);
//        graph_fragment.onCreate(savedInstanceState);
//        more_fragment.onCreate(savedInstanceState);

        fragment_transaction = getSupportFragmentManager().beginTransaction();
        fragment_manager = getSupportFragmentManager();

        this.setFragments();
        this.syncLocalDatabaseWithServerDatabase();

        startAlarm(this);

    }

    public void setFragments(){
        fragment_manager.beginTransaction().add(R.id.MAIN_frameLayout_content,
                memory_fragment,MEMORY_FRAGMENT_TAG).commit();
        fragment_manager.beginTransaction().addToBackStack(MEMORY_FRAGMENT_TAG).commit();
    }

    /* OnClick: when a category is clicked. */
    public void categoryClicked(View v) {
        ImageView imageView_memory = (ImageView) findViewById(R.id.MAIN_imageView_memory);
        ImageView imageView_quiz = (ImageView) findViewById(R.id.MAIN_imageView_quiz);
        ImageView imageView_graph = (ImageView) findViewById(R.id.MAIN_imageView_graph);
        ImageView imageView_more = (ImageView) findViewById(R.id.MAIN_imageView_more);

        /* Image optimizer */
        Glide.with(this)  // Activity or Fragment
                .load(R.drawable.memory_icon)
                .into(imageView_memory);
        Glide.with(this)  // Activity or Fragment
                .load(R.drawable.quiz_icon)
                .into(imageView_quiz);
        Glide.with(this)  // Activity or Fragment
                .load(R.drawable.graph_icon)
                .into(imageView_graph);
        Glide.with(this)  // Activity or Fragment
                .load(R.drawable.more_icon)
                .into(imageView_more);


        RelativeLayout relativeLayout_memory = (RelativeLayout) findViewById(R.id.MAIN_relativeLayout_memory);
        RelativeLayout relativeLayout_quiz = (RelativeLayout) findViewById(R.id.MAIN_relativeLayout_quiz);
        RelativeLayout relativeLayout_graph = (RelativeLayout) findViewById(R.id.MAIN_relativeLayout_graph);
        RelativeLayout relativeLayout_more = (RelativeLayout) findViewById(R.id.MAIN_relativeLayout_more);

        relativeLayout_memory.setBackgroundResource(R.drawable.no_bottom_border);
        relativeLayout_quiz.setBackgroundResource(R.drawable.no_bottom_border);
        relativeLayout_graph.setBackgroundResource(R.drawable.no_bottom_border);
        relativeLayout_more.setBackgroundResource(R.drawable.no_bottom_border);

        v.setBackgroundResource(R.drawable.bottom_border);

        switch (v.getId()) {


            case R.id.MAIN_relativeLayout_memory:
                Glide.with(this)  // Activity or Fragment
                        .load(R.drawable.clicked_memory_icon)
                        .into(imageView_memory);

                if(previous_fragment.equals(MEMORY_FRAGMENT_TAG)) return;

                switch (previous_fragment) {
                    case MEMORY_FRAGMENT_TAG:
                        fragment_manager.beginTransaction().hide(fragment_manager.findFragmentByTag(MEMORY_FRAGMENT_TAG)).commit();
                        break;
                    case QUIZ_FRAGMENT_TAG:
                        fragment_manager.beginTransaction().hide(fragment_manager.findFragmentByTag(QUIZ_FRAGMENT_TAG)).commit();
                        fragment_manager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case GRAPH_FRAGMENT_TAG:
                        fragment_manager.beginTransaction().hide(fragment_manager.findFragmentByTag(GRAPH_FRAGMENT_TAG)).commit();
                        fragment_manager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case MORE_FRAGMENT_TAG:
                        fragment_manager.beginTransaction().hide(fragment_manager.findFragmentByTag(MORE_FRAGMENT_TAG)).commit();
                        fragment_manager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;


                }
                previous_fragment = MEMORY_FRAGMENT_TAG;


                break;

            case R.id.MAIN_relativeLayout_quiz:
                Glide.with(this)  // Activity or Fragment
                        .load(R.drawable.clicked_quiz_icon)
                        .into(imageView_quiz);

                if(previous_fragment.equals(QUIZ_FRAGMENT_TAG)) return;

                switch (previous_fragment) {
                    case MEMORY_FRAGMENT_TAG:
                        fragment_manager.beginTransaction().hide(fragment_manager.findFragmentByTag(MEMORY_FRAGMENT_TAG)).commit();
                        fragment_manager.beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case QUIZ_FRAGMENT_TAG:
                        fragment_manager.beginTransaction().hide(fragment_manager.findFragmentByTag(QUIZ_FRAGMENT_TAG)).commit();
                        break;
                    case GRAPH_FRAGMENT_TAG:
                        fragment_manager.beginTransaction().hide(fragment_manager.findFragmentByTag(GRAPH_FRAGMENT_TAG)).commit();
                        fragment_manager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case MORE_FRAGMENT_TAG:
                        fragment_manager.beginTransaction().hide(fragment_manager.findFragmentByTag(MORE_FRAGMENT_TAG)).commit();
                        fragment_manager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;

                }
                previous_fragment = QUIZ_FRAGMENT_TAG;

                break;

            case R.id.MAIN_relativeLayout_graph:
                Glide.with(this)  // Activity or Fragment
                        .load(R.drawable.clicked_graph_icon)
                        .into(imageView_graph);
                if(previous_fragment.equals(GRAPH_FRAGMENT_TAG)) return;

                switch (previous_fragment) {
                    case MEMORY_FRAGMENT_TAG:
                        fragment_manager.beginTransaction().hide(fragment_manager.findFragmentByTag(MEMORY_FRAGMENT_TAG)).commit();
                        fragment_manager.beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case QUIZ_FRAGMENT_TAG:
                        fragment_manager.beginTransaction().hide(fragment_manager.findFragmentByTag(QUIZ_FRAGMENT_TAG)).commit();
                        fragment_manager.beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case GRAPH_FRAGMENT_TAG:
                        fragment_manager.beginTransaction().hide(fragment_manager.findFragmentByTag(GRAPH_FRAGMENT_TAG)).commit();
                        break;
                    case MORE_FRAGMENT_TAG:
                        fragment_manager.beginTransaction().hide(fragment_manager.findFragmentByTag(MORE_FRAGMENT_TAG)).commit();
                        fragment_manager.beginTransaction().setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;

                }
                previous_fragment = GRAPH_FRAGMENT_TAG;

                break;

            case R.id.MAIN_relativeLayout_more:

                Glide.with(this)  // Activity or Fragment
                        .load(R.drawable.clicked_more_icon)
                        .into(imageView_more);
                if(previous_fragment.equals(MORE_FRAGMENT_TAG)) return;

                switch (previous_fragment) {

                    case MEMORY_FRAGMENT_TAG:
                        fragment_manager.beginTransaction().hide(fragment_manager.findFragmentByTag(MEMORY_FRAGMENT_TAG)).commit();
                        fragment_manager.beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case QUIZ_FRAGMENT_TAG:
                        fragment_manager.beginTransaction().hide(fragment_manager.findFragmentByTag(QUIZ_FRAGMENT_TAG)).commit();
                        fragment_manager.beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case GRAPH_FRAGMENT_TAG:
                        fragment_manager.beginTransaction().hide(fragment_manager.findFragmentByTag(GRAPH_FRAGMENT_TAG)).commit();
                        fragment_manager.beginTransaction().setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case MORE_FRAGMENT_TAG:
                        fragment_manager.beginTransaction().hide(fragment_manager.findFragmentByTag(MORE_FRAGMENT_TAG)).commit();
                        break;

                }
                previous_fragment = MORE_FRAGMENT_TAG;

                break;


        }

        Fragment fragment = fragment_manager.findFragmentByTag(previous_fragment);
        if(fragment == null){
            switch(previous_fragment){
                case MEMORY_FRAGMENT_TAG:
                    fragment_manager.beginTransaction().add(R.id.MAIN_frameLayout_content,
                            memory_fragment,previous_fragment).commit();
                    fragment_manager.beginTransaction().addToBackStack(MEMORY_FRAGMENT_TAG);
                    break;
                case QUIZ_FRAGMENT_TAG:
                    fragment_manager.beginTransaction().add(R.id.MAIN_frameLayout_content,
                            quiz_fragment,previous_fragment).commit();
                    fragment_manager.beginTransaction().addToBackStack(QUIZ_FRAGMENT_TAG);
                    break;
                case GRAPH_FRAGMENT_TAG:
                    fragment_manager.beginTransaction().add(R.id.MAIN_frameLayout_content,
                            graph_fragment,previous_fragment).commit();
                    fragment_manager.beginTransaction().addToBackStack(GRAPH_FRAGMENT_TAG);
                    break;
                case MORE_FRAGMENT_TAG:
                    fragment_manager.beginTransaction().add(R.id.MAIN_frameLayout_content,
                            more_fragment,previous_fragment).commit();
                    fragment_manager.beginTransaction().addToBackStack(MORE_FRAGMENT_TAG);
                    break;
            }

        }else{
            fragment_manager.beginTransaction().show(fragment).commit();
        }

    }

    private void syncLocalDatabaseWithServerDatabase(){
        //TODO: Sync all tables with Server database.
    }


    public static void startAlarm(Context context) {
        /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child(User.REFERENCE_USERS).child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(mAuth.getCurrentUser() == null) return;

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                String setting_game = "";
                String setting_notification = "";
                String setting_vibration = "";
                String setting_gesture = "";


                for(DataSnapshot child: children) {
                    String key = child.getKey();
                    String value = child.getValue(String.class);
                    switch (key) {
                        case "setting_game":
                            setting_game = value;
                            break;

                        case "setting_gesture":
                            setting_gesture = value;
                            break;

                        case "setting_notification":
                            setting_notification = value;
                            break;

                        case "setting_vibration":
                            setting_vibration = value;
                            break;

                    }
                }


                // TESTING

                AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                int hour = 1000 * 60 * 60;
                Calendar calendar = Calendar.getInstance();

                /* Set the alarm to start at 10:30 AM */
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, 9);
                calendar.set(Calendar.MINUTE, 00);

                switch(setting_notification){
                    case "None":
                        manager.cancel(pendingIntent);

                        break;
                    case "3 hours":
                        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                hour * 3, pendingIntent);
                        break;
                    case "6 hours":
                        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                hour * 6, pendingIntent);
                        break;
                    case "12 hours":
                        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                                hour * 12, pendingIntent);
                        break;
                }

                //TODO: when Setting_vibration is changed.
                //TODO: when Setting_gesture is changed.
                //TODO: when Setting_game is changed.

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
