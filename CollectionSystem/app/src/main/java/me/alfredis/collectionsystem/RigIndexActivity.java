package me.alfredis.collectionsystem;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;

import java.util.ArrayList;

import me.alfredis.collectionsystem.datastructure.RigEvent;


public class RigIndexActivity extends ActionBarActivity {
    private TableLayout rigsTable;
    private Button buttonAddRig;
    private Spinner holeSpinner;

    private String holeId;

    private ArrayList<String> holeArray;
    private ArrayAdapter<String> holeAdapter;

    private static final String TAG = "ColletionSystem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rig_index);

        rigsTable = (TableLayout) findViewById(R.id.rig_table);

        holeSpinner = (Spinner) findViewById(R.id.spinner_hole);

        holeArray = new ArrayList<>();

        holeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                holeId = String.valueOf(parent.getItemAtPosition(position).toString());
                refreshTable();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holeId = getIntent().getStringExtra("holeId");

        refreshHoleSpinner();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rig, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshTable() {
        while (rigsTable.getChildCount() > 2) {
            rigsTable.removeViewAt(2);
        }

        for (RigEvent rig : DataManager.getRigEventListByHoleId(holeId)) {
            //TODO: draw rig
        }
    }

    private void refreshHoleSpinner() {
        holeArray.clear();

        int selectedIndex = -1;

        for (int i = 0; i < DataManager.holes.size(); i++) {
            holeArray.add(DataManager.holes.get(i).getHoleId());
            if (DataManager.holes.get(i).getHoleId().equals(holeId)) {
                selectedIndex = i;
            }
        }

        holeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, holeArray);

        holeSpinner.setAdapter(holeAdapter);

        holeSpinner.setSelection(selectedIndex);
    }
}
