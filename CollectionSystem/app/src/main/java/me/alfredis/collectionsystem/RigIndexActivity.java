package me.alfredis.collectionsystem;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
        while (rigsTable.getChildCount() > 3) {
            rigsTable.removeViewAt(3);
        }

        Log.d(TAG, "Draw hole rigs");

        for (RigEvent rig : DataManager.getRigEventListByHoleId(holeId)) {
            TableRow row = new TableRow(this);

            row.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));

            TableLayout.LayoutParams tableLayoutParam = new TableLayout.LayoutParams();
            tableLayoutParam.setMargins(2, 2, 2, 2);
            row.setLayoutParams(tableLayoutParam);

            row.addView(createRigContentTextView(rig.getClassPeopleCount()));

            row.addView(createRigContentTextView(Utility.formatCalendarDateString(rig.getDate())));
            row.addView(createRigContentTextView(Utility.formatTimeString(rig.getStartTime())));
            row.addView(createRigContentTextView(Utility.formatTimeString(rig.getEndTime())));
            row.addView(createRigContentTextView("TimeSpan placeHolders"));

            row.addView(createRigContentTextView(rig.getProjectName()));

            row.addView(createRigContentTextView(String.valueOf(rig.getDrillPipeId())));
            row.addView(createRigContentTextView(String.valueOf(rig.getDrillPipeLength())));
            row.addView(createRigContentTextView(String.valueOf(rig.getCumulativeLength())));

            row.addView(createRigContentTextView(String.valueOf(rig.getCoreBarreliDiameter())));
            row.addView(createRigContentTextView(String.valueOf(rig.getCoreBarreliLength())));

            row.addView(createRigContentTextView(rig.getDrillType()));
            row.addView(createRigContentTextView(String.valueOf(rig.getDrillDiameter())));
            row.addView(createRigContentTextView(String.valueOf(rig.getDrillLength())));

            row.addView(createRigContentTextView("hole state placeHolder"));

            row.addView(createRigContentTextView(rig.getRockCoreId()));
            row.addView(createRigContentTextView(String.valueOf(rig.getRockCoreLength())));
            row.addView(createRigContentTextView(String.valueOf(rig.getRockCoreRecovery())));

            row.addView(createRigContentTextView("rug placeholder 1"));
            row.addView(createRigContentTextView("rug placeholder 2"));
            row.addView(createRigContentTextView("rug placeholder 3"));
            row.addView(createRigContentTextView("rug placeholder 4"));

            row.addView(createRigContentTextView("water placeholder 1"));
            row.addView(createRigContentTextView("water placeholder 2"));
            row.addView(createRigContentTextView("water placeholder 3"));

            row.addView(createRigContentTextView("ground placeholder 1"));
            row.addView(createRigContentTextView("ground placeholder 2"));
            row.addView(createRigContentTextView("ground placeholder 3"));
            row.addView(createRigContentTextView("ground placeholder 4"));
            row.addView(createRigContentTextView("ground placeholder 5"));
            row.addView(createRigContentTextView("water placeholder 1"));
            row.addView(createRigContentTextView("water placeholder 2"));
            row.addView(createRigContentTextView("water placeholder 3"));
            row.addView(createRigContentTextView("water placeholder 4"));

            row.addView(createRigContentTextView(rig.getNote()));

        }
    }

    private TextView createRigContentTextView(String text) {
        TextView temp = new TextView(this);
        temp.setText(text);
        temp.setBackgroundColor(getResources().getColor(android.R.color.white));

        TableRow.LayoutParams tableRowParam = new TableRow.LayoutParams();
        tableRowParam.setMargins(2, 2, 2, 2);
        temp.setLayoutParams(tableRowParam);

        return temp;
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
