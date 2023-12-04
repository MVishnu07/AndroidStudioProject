package ca.college.usa;
/**
 * Full Name: Vishnu Vardhan Malkapuram
 *
 * Student ID: 041078116
 *
 * Course: CST3104
 *
 * Term:  Fall 2023
 *
 * Assignment: Team Project
 *
 * Date : December 3rd, 2023.
 */
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SecondActivity extends AppCompatActivity {
    private ImageView flagImageView;
    private TextView capitalTextView;
    private ListView statesListView;
    private ArrayList<State> statesList = new ArrayList<>();
    private Random random = new Random();
    private TextView counterTextView;
    private TextView stateNameTextView;
    private int counter = 0;
    private boolean isCorrectAnswerFound = false;
    private String wikiUrl;

    private State correctState;
    CounterDAO mDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        CounterDatabase db = CounterDatabase.getDatabase(getApplicationContext());
        mDAO = db.cDAO();

        flagImageView = findViewById(R.id.imageViewFlag);
        capitalTextView = findViewById(R.id.textViewCapital);
        statesListView = findViewById(R.id.listViewStates);

        Toolbar toolbarGame = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarGame);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Guess the State?");
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        }

        counterTextView = findViewById(R.id.counter);
        stateNameTextView = findViewById(R.id.correctAnswer);
        counterTextView.setBackgroundColor(Color.TRANSPARENT);


        readStatesData();
        displayRandomState();
        setupListView();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.infoBtn:
                openWebPage(wikiUrl);
                return true;
            case R.id.nextBtn:
                resetGame();
                return true;
            case R.id.endGameBtn:
                endGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void resetGame() {
        counter = 0;
        counterTextView.setText("Counter: " + counter);
        counterTextView.setBackgroundColor(Color.TRANSPARENT);
        stateNameTextView.setVisibility(View.INVISIBLE);
        isCorrectAnswerFound = false;
        displayRandomState();
    }

    private void openWebPage(String url) {
        if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Error opening webpage: " + url, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Invalid URL: " + url, Toast.LENGTH_SHORT).show();
        }
    }

    private void endGame() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMMM-yy hh-mm a");
        String currentTime = sdf.format(new Date());
        CounterScore counterScore = new CounterScore(counter, currentTime);

        new AlertDialog.Builder(this)
                .setTitle("End Game")
                .setMessage("Your score is: " + counter + "\nDo you want to exit the game?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(() -> {
                        mDAO.InsertCounter(counterScore);
                        executor.shutdown();
                        runOnUiThread(this::finish);
                    });
                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void readStatesData() {
        try {
            InputStream is = getAssets().open("usa.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray statesArray = jsonObject.getJSONArray("states");

            for (int i = 0; i < statesArray.length(); i++) {
                JSONObject stateJson = statesArray.getJSONObject(i);
                State state = new State();
                state.setName(stateJson.getString("name"));
                state.setCapital(stateJson.getString("capital"));
                int flagId = getResources().getIdentifier(stateJson.getString("code").toLowerCase(), "drawable", getPackageName());
                state.setFlagResource(flagId);
                statesList.add(state);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayRandomState() {
        int index = random.nextInt(statesList.size());
        State randomState = statesList.get(index);
        wikiUrl = randomState.getWikiUrl();
        correctState = randomState;
        flagImageView.setImageResource(randomState.getFlagResource());
        capitalTextView.setText(randomState.getCapital());
    }

    private void setupListView() {
        ArrayList<String> stateNames = new ArrayList<>();
        for (State state : statesList) {
            stateNames.add(state.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stateNames);
        statesListView.setAdapter(adapter);

        statesListView.setOnItemClickListener((parent, view, position, id) -> {
            if (!isCorrectAnswerFound) {
                State selectedState = statesList.get(position);
                if (checkIfStateIsCorrect(selectedState)) {
                    isCorrectAnswerFound = true;
                    view.setBackgroundColor(Color.GREEN);
                    counterTextView.setBackgroundColor(Color.GREEN);
                    stateNameTextView.setText(selectedState.getName());
                    stateNameTextView.setVisibility(View.VISIBLE);
                    counter += 5;
                    counterTextView.setText("Counter: " + counter);

                    view.postDelayed(() -> {
                        displayRandomState();
                        isCorrectAnswerFound = false;
                        stateNameTextView.setVisibility(View.INVISIBLE);
                        view.setBackgroundColor(Color.TRANSPARENT);
                    },3000);
                } else {
                    view.setBackgroundColor(Color.RED);
                    counterTextView.setBackgroundColor(Color.RED);
                    counter = counter - 3;
                    counterTextView.setText("Counter: " + counter);
                    view.postDelayed(() -> {
                        view.setBackgroundColor(Color.TRANSPARENT);
                    },3000);
                }
            }
        });
    }
    private boolean checkIfStateIsCorrect(State selectedState) {

        return selectedState.getName().equals(correctState.getName());
    }
}
