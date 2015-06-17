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

import me.alfredis.collectionsystem.datastructure.CasedRig;
import me.alfredis.collectionsystem.datastructure.DSTRig;
import me.alfredis.collectionsystem.datastructure.Hole;
import me.alfredis.collectionsystem.datastructure.RigEvent;
import me.alfredis.collectionsystem.datastructure.RigView;
import me.alfredis.collectionsystem.datastructure.SPTRig;
import me.alfredis.collectionsystem.parser.HtmlParser;
import me.alfredis.collectionsystem.parser.XlsParser;


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
        int groundNo = 0;
        for (RigEvent rig : DataManager.getRigEventListByHoleId(holeId)) {
            Hole hole = DataManager.getHole(holeId);
            RigView rigView = new RigView(hole,rig);
            String rigType = rigView.getRigType();
            TableRow row = new TableRow(this);

            row.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));

            TableLayout.LayoutParams tableLayoutParam = new TableLayout.LayoutParams();
            tableLayoutParam.setMargins(2, 2, 2, 2);
            row.setLayoutParams(tableLayoutParam);

            row.addView(createRigContentTextView(rigView.getClassPeopleCount()));

            row.addView(createRigContentTextView((rigView.getDate())));
            row.addView(createRigContentTextView(rigView.getStartTime()));
            row.addView(createRigContentTextView(rigView.getEndTime()));
            row.addView(createRigContentTextView(rigView.getTimeInterval()));

            row.addView(createRigContentTextView(rigView.getProjectName()));

            row.addView(createRigContentTextView((rigView.getDrillPipeId())));
            row.addView(createRigContentTextView((rigView.getDrillPipeLength())));
            row.addView(createRigContentTextView((rigView.getCumulativeLength())));

            row.addView(createRigContentTextView((rigView.getCoreBarreliDiameter())));
            row.addView(createRigContentTextView((rigView.getCoreBarreliLength())));

            row.addView(createRigContentTextView(rigView.getDrillType()));
            row.addView(createRigContentTextView((rigView.getDrillDiameter())));
            row.addView(createRigContentTextView((rigView.getDrillLength())));

            row.addView(createRigContentTextView((rigView.getDrillToolTotalLength())));

            row.addView(createRigContentTextView((rigView.getDrillToolRemainLength())));
            row.addView(createRigContentTextView((rigView.getRoundTripMeterage())));
            row.addView(createRigContentTextView((rigView.getCumulativeMeterage())));

            row.addView(createRigContentTextView(rigView.getDadoType()));
            row.addView(createRigContentTextView((rigView.getCasedId())));
            row.addView(createRigContentTextView((rigView.getCasedDiameter())));
            row.addView(createRigContentTextView((rigView.getCasedLength())));
            row.addView(createRigContentTextView((rigView.getCasedTotalLength())));
            //孔内情况
            row.addView(createRigContentTextView(rigView.getCasedSituation()));

            row.addView(createRigContentTextView(rigView.getRockCoreId()));
            row.addView(createRigContentTextView((rigView.getRockCoreLength())));
            row.addView(createRigContentTextView((rigView.getRockCoreRecovery())));

            //土样
            row.addView(createRigContentTextView(rigView.getEarthNo()));
            row.addView(createRigContentTextView(rigView.getEarthDiameter()));
            row.addView(createRigContentTextView(rigView.getEarthDepth()));
            row.addView(createRigContentTextView(rigView.getEarthCount()));

            //水样
            row.addView(createRigContentTextView(rigView.getWaterNo()));
            row.addView(createRigContentTextView(rigView.getWaterDepth()));
            row.addView(createRigContentTextView(rigView.getWaterCount()));

            //地层
            String groundNumber ="";
            if(rigType.equals("Normal")){
                groundNo++;
                groundNumber = String.valueOf(groundNo);
            }
            row.addView(createRigContentTextView(groundNumber)); //编号
            row.addView(createRigContentTextView((rigView.getGroundDepth())));//层深
            row.addView(createRigContentTextView((rigView.getGroundDepthDiff())));//层厚
            row.addView(createRigContentTextView(rigView.getGroundNote())); // 名称及岩性
            row.addView(createRigContentTextView(rigView.getGroundClass())); //岩层等级

            //地下水
            row.addView(createRigContentTextView(rigView.getMeasureDatesInterval())); //观测时间
            row.addView(createRigContentTextView((rigView.getInitialLevel())));
            row.addView(createRigContentTextView((rigView.getStableLevel())));
            row.addView(createRigContentTextView((rigView.getLevelChange())));

            //特殊情况
            row.addView(createRigContentTextView(rigView.getHoleNote()));

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
        //backup
        String xlsPath = Environment.getExternalStorageDirectory().getPath()+"/ZuanTan/" + "zuantan.xls";
        XlsParser.parse(xlsPath, DataManager.holes);
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
