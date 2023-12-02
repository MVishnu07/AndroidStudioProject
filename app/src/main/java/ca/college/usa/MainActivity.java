package ca.college.usa;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;


/**
 * Full Name:
 *
 * Student ID:
 *
 * Course: CST3104
 *
 * Term:  Fall 2022
 *
 * Assignment: Team Project
 *
 * Date :
 */

public class MainActivity extends AppCompatActivity {

    private static final String sFileName = "usa.json";

    // ListView <---> adapter <---> data
    private ArrayAdapter<State> mAdapter;
    private ListView mlistView;

    // Data Source for the Adapter
    private ArrayList<State> mStatesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the data needed for the adapter
        mStatesList = State.readData(this, sFileName );

        mlistView = findViewById(R.id.listView);

        mAdapter = new ArrayAdapter<>(this,
                                       android.R.layout.simple_list_item_1,
                                        mStatesList);

        mlistView.setAdapter(mAdapter);

        Button playButton = findViewById(R.id.playBtn);
        playButton.setOnClickListener(view -> {
            openSecondActivity(view);
        });

    }
    public void openSecondActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }



}