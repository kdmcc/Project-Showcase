package com.example.cse331_19wi_campus_paths;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import hw8.CampusBuilding;
import hw8.PathMap;

public class MainActivity extends AppCompatActivity {

    // Displays the campus map and the shortest path.
    DrawView view;
    // The user will choose the starting point of the path from this list
    ListView originSelect;
    // The user will choose the ending point of the path from this list
    ListView destinationSelect;

    Button findPathButton;
    Button clearButton;

    // Displays the user's selections
    TextView originText;
    TextView destText;

    // Represents the campus map and is able to make shortest path calculations
    // between two buildings on campus
    private PathMap campus;

    // Used to map the options in the ListView selectors to campusBuilding objects
    // Maps a building's long name to its campusBuilding object
    private Map<String, CampusBuilding>  builds;

    // Stores the user's selections
    private CampusBuilding orig;
    private CampusBuilding dest;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Try-Catch block to catch IOException from PathMap constructor
        try {
            // Load campus data into campus
            InputStream pathIS = this.getResources().openRawResource(R.raw.campus_paths);
            InputStream buildingIS = this.getResources().openRawResource(R.raw.campus_buildings_new);
            campus = new PathMap(pathIS, buildingIS);

            // Fill builds with all CampusBuilding objects mapped to from their long names
            builds = new HashMap<>();
            for (CampusBuilding build: campus.getBuildings()) {
                builds.put(build.getLongName(), build);
            }

            // Initialize UI elements and their listeners
            view = (DrawView) findViewById(R.id.imageView);

            originText = (TextView) findViewById(R.id.textView);
            destText = (TextView) findViewById(R.id.textView2);

            clearButton = (Button) findViewById(R.id.clearButton);
            clearButton.setOnClickListener(clearButtonClick);

            findPathButton = (Button) findViewById(R.id.findPathButton);
            findPathButton.setOnClickListener(findPathButtonClick);

            originSelect = (ListView) findViewById(R.id.originSelect);
            destinationSelect = (ListView) findViewById(R.id.destinationSelect);


            // Create adapter to fill ListViews with the all building long names.
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                    android.R.layout.simple_list_item_1,
                    new ArrayList<String>());
            adapter.addAll(builds.keySet());
            adapter.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });

            originSelect.setAdapter(adapter);
            destinationSelect.setAdapter(adapter);

            originSelect.setOnItemClickListener(originItemClick);
            destinationSelect.setOnItemClickListener(destinationItemClick);

        } catch (IOException e) { // Try-Catch block to catch IOException from PathMap constructor
            Toast.makeText(getApplicationContext(), "Error Loading Files", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    OnClickListener for the find path button.

    If the origin or destination are not selected, the user is prompted to make a selection.
    Otherwise, the shortest path between the two selected buildings is presented to the user.
    */
    private View.OnClickListener findPathButtonClick = new View.OnClickListener() {
        public void onClick(View v) {
            if (orig == null) { // no origin selected
                Toast.makeText(getApplicationContext(), "Please Select Origin", Toast.LENGTH_LONG).show();
            } else if (dest == null) { // no destination selected
                Toast.makeText(getApplicationContext(), "Please Select Destination", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Lets Go!!", Toast.LENGTH_LONG).show();
                view.setPath(campus.shorestPath(orig.getShortName(), dest.getShortName()));

                // TODO: implement a new separate activity to present the shortest path more clearly
                // The following line is commented out because it the ShowPathActivity is incomplete.
                //startActivity(new Intent(MainActivity.this, ShowPathActivity.class));
            }
        }
    };

    /*
    OnClickListener for the clear button.

    Provides a way for the user to clear their selections.
    Resets the user's previous selections and the map display.
     */
    private View.OnClickListener clearButtonClick = new View.OnClickListener() {
        public void onClick(View v) {
            // reset selections
            orig = null;
            dest = null;
            originText.setText("Select Origin");
            destText.setText("Select Destination");

            // reset map display
            view.setOrig(null);
            view.setDest(null);
            view.setPath(null);

            // notify user
            Toast.makeText(getApplicationContext(), "Selections Cleared", Toast.LENGTH_LONG).show();
        }
    };

    /*
    OnClickListener for the destination ListView

    When a building is selected, the building is displayed on the map, and its name is presented
    to the user in destText
     */
    private AdapterView.OnItemClickListener destinationItemClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v,
                                int position, long id) {
            dest = builds.get(destinationSelect.getItemAtPosition(position).toString());
            view.setDest(dest.getLocation());
            destText.setText(dest.getLongName());
        }
    };

    /*
    OnClickListener for the origin ListView

    When a building is selected, the building is displayed on the map, and its name is presented
    to the user in destText
     */
    private AdapterView.OnItemClickListener originItemClick = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v,
                                int position, long id) {
            orig = builds.get(originSelect.getItemAtPosition(position).toString());
            view.setOrig(orig.getLocation());
            originText.setText(orig.getLongName());
        }
    };
}
