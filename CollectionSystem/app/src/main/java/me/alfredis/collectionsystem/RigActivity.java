package me.alfredis.collectionsystem;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;

import me.alfredis.collectionsystem.datastructure.RigEvent;


public class RigActivity extends ActionBarActivity {
    private TableLayout rigsTable;
    private Button buttonAddRig;
    private Spinner holeSpinner;

    private int holeIndex;

    private static final String TAG = "ColletionSystem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rig);

        rigsTable = (TableLayout) findViewById(R.id.rig_table);
        holeSpinner = (Spinner) findViewById(R.id.spinner_hole);

        holeIndex = getIntent().getIntExtra("holeIndex", -1);

        refreshTable();

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
        while (rigsTable.getChildCount() != 1) {
            rigsTable.removeViewAt(1);
        }

        for (RigEvent rig : DataManager.getRigEventListByHoleIndex(holeIndex)) {
            //TODO: draw rig
        }
    }

}
