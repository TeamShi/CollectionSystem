package me.alfredis.collectionsystem;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
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
import android.widget.Toast;

import java.util.ArrayList;

import me.alfredis.collectionsystem.datastructure.Hole;
import me.alfredis.collectionsystem.datastructure.RigEvent;
import me.alfredis.collectionsystem.parser.HtmlParser;


public class RigIndexActivity extends ActionBarActivity implements View.OnClickListener{
    private TableLayout rigsTable;
    private Button buttonAddRig;
    private Button buttonExportNormalRigTable;
    private Button buttonExportDSTRigTable;
    private Button buttonExportSPTRigTable;
    private Spinner holeSpinner;

    private String holeId;

    private ArrayList<String> holeArray;
    private ArrayAdapter<String> holeAdapter;

    private static final String TAG = "ColletionSystem";

    private static final int ADD_RIG = 0;
    private static final int QUERY_RIG = 1;

    private static final int CONTEXT_MENU_QUERY = 0;
    private static final int CONTEXT_MENU_DELETE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rig_index);

        rigsTable = (TableLayout) findViewById(R.id.rig_table);

        holeSpinner = (Spinner) findViewById(R.id.spinner_hole);

        buttonAddRig = (Button) findViewById(R.id.button_add_rig);

        holeArray = new ArrayList<>();

        buttonAddRig.setOnClickListener(this);

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, CONTEXT_MENU_QUERY, 0, "查询");
        menu.add(0, CONTEXT_MENU_DELETE, 0, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String rigIndex = getIntent().getStringExtra("SelectedRigIndex");
        switch (item.getItemId()) {
            case CONTEXT_MENU_QUERY:
                Log.d(TAG, "Query rig.");
                Intent intent = new Intent(this, RigInfoActivity.class);
                intent.putExtra("requestCode", "QUERY_RIG");
                intent.putExtra("holeId", holeId);
                intent.putExtra("rigIndex", rigIndex);
                startActivityForResult(intent, QUERY_RIG);
                break;
            case CONTEXT_MENU_DELETE:
                DataManager.deleteRig(holeId, Integer.valueOf(rigIndex));
                Toast.makeText(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT).show();

                refreshTable();
                break;
        }
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

        int index = 0;
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
            row.addView(createRigContentTextView(Utility.calculateTimeSpan(rig.getStartTime(), rig.getEndTime())));

            row.addView(createRigContentTextView(rig.getProjectName()));

            row.addView(createRigContentTextView(String.valueOf(rig.getDrillPipeId())));
            row.addView(createRigContentTextView(String.valueOf(rig.getDrillPipeLength())));
            row.addView(createRigContentTextView(String.valueOf(rig.getCumulativeLength())));

            row.addView(createRigContentTextView(String.valueOf(rig.getCoreBarreliDiameter())));
            row.addView(createRigContentTextView(String.valueOf(rig.getCoreBarreliLength())));

            row.addView(createRigContentTextView(rig.getDrillType()));
            row.addView(createRigContentTextView(String.valueOf(rig.getDrillDiameter())));
            row.addView(createRigContentTextView(String.valueOf(rig.getDrillLength())));

            row.addView(createRigContentTextView(String.valueOf(rig.getDrillToolTotalLength())));

            row.addView(createRigContentTextView(String.valueOf(rig.getDrillToolRemainLength())));
            row.addView(createRigContentTextView(String.valueOf(rig.getRoundTripMeterage())));
            row.addView(createRigContentTextView(String.valueOf(rig.getCumulativeMeterage())));

            //护壁措施
            row.addView(createRigContentTextView("rug placeholder 1"));
            row.addView(createRigContentTextView("rug placeholder 2"));
            row.addView(createRigContentTextView("rug placeholder 3"));
            row.addView(createRigContentTextView("rug placeholder 4"));
            row.addView(createRigContentTextView("water placeholder 1"));
            //孔内情况
            row.addView(createRigContentTextView("water placeholder 2"));

            row.addView(createRigContentTextView(rig.getRockCoreId()));
            row.addView(createRigContentTextView(String.valueOf(rig.getRockCoreLength())));
            row.addView(createRigContentTextView(String.valueOf(rig.getRockCoreRecovery())));

            //土样
            row.addView(createRigContentTextView("ground placeholder 1"));
            row.addView(createRigContentTextView("ground placeholder 2"));
            row.addView(createRigContentTextView("ground placeholder 3"));
            row.addView(createRigContentTextView("ground placeholder 4"));

            //水样
            row.addView(createRigContentTextView("water placeholder 1"));
            row.addView(createRigContentTextView("water placeholder 2"));
            row.addView(createRigContentTextView("water placeholder 3"));

            //地层
            row.addView(createRigContentTextView("ground placeholder 5")); //编号
            row.addView(createRigContentTextView(String.valueOf(rig.getEndDepth())));
            row.addView(createRigContentTextView(String.valueOf(rig.getEndDepth() - rig.getStartDepth())));//层厚
            row.addView(createRigContentTextView(rig.getSpecialNote())); // 名称及岩性
            row.addView(createRigContentTextView("ground placeholder 6")); //岩层等级

            Hole hole = DataManager.getHole(holeId);

            //地下水
            row.addView(createRigContentTextView(Utility.formatTimeString(hole.getInitialLevelMeasuringDate()))); //观测时间
            row.addView(createRigContentTextView(String.valueOf(hole.getInitialLevel())));
            row.addView(createRigContentTextView(String.valueOf(hole.getStableLevel())));
            row.addView(createRigContentTextView(String.valueOf(hole.getStableLevel()-hole.getInitialLevel())));

            row.addView(createRigContentTextView(hole.getNote())); //特殊情况

            row.setTag(index);
            index++;
            row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    setIntent(getIntent().putExtra("SelectedRigIndex", v.getTag().toString()));
                    return false;
                }
            });

            registerForContextMenu(row);

            rigsTable.addView(row);
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

    @Override
    public void onClick(View v) {
        String baseDir = Environment.getExternalStorageDirectory().getPath()+"/";
        AssetManager assetManageer = getAssets();
        ArrayList<RigEvent> rigEvents = DataManager.getRigEventListByHoleId(holeId);
        switch (v.getId()) {
            case R.id.button_add_rig:
                Log.d(TAG, "Add new rig button clicked.");
                Intent intent = new Intent(this, RigInfoActivity.class);
                intent.putExtra("requestCode", "ADD_RIG");
                intent.putExtra("holeId", holeId);
                startActivityForResult(intent, ADD_RIG);
                break;
          default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_RIG) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "Get new hole.");
                refreshTable();
            }
        } else if (requestCode == QUERY_RIG) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "Query finished.");
                refreshTable();
            }
        }
    }
}
