package ca.college.usa;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Full Name: Karim Al Malki
 *
 * Student ID: 041-073-798
 *
 * Course: CST3104
 *
 * Term:  Fall 2023
 *
 * Assignment: Team Project
 *
 * Date : December 3rd, 2023.
 */

public class MainActivity extends AppCompatActivity {

    private static final String sFileName = "usa.json";
    // ListView <---> adapter <---> data
    private ArrayAdapter<State> mAdapter;
    private ListView mlistView;
    private ImageView flagImageView;

    private TextView highestScore, lowestScore, recentScore;

    private ArrayList<State> mStatesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flagImageView = findViewById(R.id.imageView2);

        flagImageView.setImageResource(R.drawable.usamap);

        highestScore = findViewById(R.id.HighestScoreNum);
        lowestScore = findViewById(R.id.LowestScoreNum);
        recentScore = findViewById(R.id.RecentScoreNum);

        loadScores();

        // Load the data needed for the adapter
//        mStatesList = State.readData(this, sFileName );

//        mAdapter = new ArrayAdapter<>(this,
//                                       android.R.layout.simple_list_item_1,
//                                        mStatesList);
//
//        mlistView.setAdapter(mAdapter);

        Toolbar toolbarHome = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbarHome);
        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("Guess the State?");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        }

        Button playButton = findViewById(R.id.playBtn);
        playButton.setOnClickListener(view -> {
            openSecondActivity(view);
        });

    }

    private void loadScores() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            CounterDatabase db = CounterDatabase.getDatabase(getApplicationContext());
            CounterDAO dao = db.cDAO();

            CounterScore highest = dao.getHighestScoreWithDate();
            CounterScore lowest = dao.getLowestScoreWithDate();
            CounterScore recentScoreObj = dao.getMostRecentScore();

            runOnUiThread(() -> {
                if (highest != null) {
                    highestScore.setText(highest.getCounter() + " on " + highest.getDate());
                }
                if (lowest != null) {
                    lowestScore.setText(lowest.getCounter() + " on " + lowest.getDate());
                }
                if (recentScoreObj != null) {
                    recentScore.setText(recentScoreObj.getCounter() + " on " + recentScoreObj.getDate());
                }
            });
        });
        executor.shutdown();
    }

    public void openSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.infoId:
                showGameInfoDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showGameInfoDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Game Information")
                .setMessage("Guess the U.S. State!\n\nTry to identify the U.S. state by its flag and capital city. Select the correct state from the list. Your score will increase for every correct guess and decrease for incorrect ones.")
                .setPositiveButton("Got it!", (dialog, which) -> dialog.dismiss())
                .show();
    }

}