package ca.college.usa;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

    private ImageView flagImageView;

    private TextView highestScore, lowestScore, recentScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flagImageView = findViewById(R.id.imageView2);

        flagImageView.setImageResource(R.drawable.usamap);

        highestScore = findViewById(R.id.HighestScoreNum);
        lowestScore = findViewById(R.id.LowestScoreNum);
        recentScore = findViewById(R.id.RecentScoreNum);

        Toolbar toolbarHome = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbarHome);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        }

        Button playButton = findViewById(R.id.playBtn);
        playButton.setOnClickListener(view -> {
            openSecondActivity(view);
        });
        loadScores();

    }

    public void loadScores() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            CounterDatabase db = CounterDatabase.getDatabase(getApplicationContext());
            CounterDAO dao = db.cDAO();

            CounterScore recentScoreObj = dao.getMostRecentScore();
            CounterScore highest = dao.getHighestScoreWithDate();
            CounterScore lowest = dao.getLowestScoreWithDate();

            runOnUiThread(() -> {
                if (recentScoreObj != null) {
                    recentScore.setText(recentScoreObj.getCounter() + " on " + recentScoreObj.getDate());
                }
                if (highest != null) {
                    highestScore.setText(highest.getCounter() + " on " + highest.getDate());
                }
                if (lowest != null) {
                    lowestScore.setText(lowest.getCounter() + " on " + lowest.getDate());
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
                return true;
            case R.id.versionId:
                showAuthorInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showGameInfoDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Game Information")
                .setMessage("Guess the U.S. State!\n\nTry to identify the U.S. state by its flag and capital city. Select the correct state from the list, your score will increase by 5 points for every correct guess and decrease by 3 points for incorrect ones.\n Good Luck!!")
                .setPositiveButton("Got it!", (dialog, which) -> dialog.dismiss())
                .show();
    }
    private void showAuthorInfo() {
        new AlertDialog.Builder(this)
                .setTitle("Author Information")
                .setMessage("Guess the State Game!!\n\nVersion 1 of the game created by Vishnu Malkapuram and Karim Al Malki")
                .setPositiveButton("Hooray!!", (dialog, which) -> dialog.dismiss())
                .show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadScores();
    }

}