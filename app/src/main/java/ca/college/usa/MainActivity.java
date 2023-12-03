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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.ArrayList;


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


    // Data Source for the Adapter
    private ArrayList<State> mStatesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flagImageView = findViewById(R.id.imageView2);

        flagImageView.setImageResource(R.drawable.usamap);

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