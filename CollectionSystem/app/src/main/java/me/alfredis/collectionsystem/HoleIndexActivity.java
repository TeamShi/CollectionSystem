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
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;

import me.alfredis.collectionsystem.datastructure.Hole;
import me.alfredis.collectionsystem.parser.HtmlParser;
import me.alfredis.collectionsystem.parser.MdbParser;
import me.alfredis.collectionsystem.parser.XlsParser;


public class HoleIndexActivity extends ActionBarActivity implements View.OnClickListener{
    private TableLayout holesTable;
    private Button buttonAddHole;
    private Button buttonImportData;
    private Button buttonOutputData;

    private static final String TAG = "CollectionSystem";
    private static final int ADD_HOLE = 1;
    private static final int QUERY_HOLE = 2;
    private static final int EDIT_RIG = 3;

    private static final int CONTEXT_MENU_QUERY = 0;
    private static final int CONTEXT_MENU_INPUT = 1;
    private static final int CONTEXT_MENU_DELETE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole_index);

        holesTable = (TableLayout) findViewById(R.id.hole_table);
        buttonAddHole = (Button) findViewById(R.id.button_add_hole);
        buttonOutputData = (Button) findViewById(R.id.button_output_data);
        buttonImportData = (Button) findViewById(R.id.button_import_data);

        buttonAddHole.setOnClickListener(this);
        buttonImportData.setOnClickListener(this);
        buttonOutputData.setOnClickListener(this);

        refreshTable();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.add(0, CONTEXT_MENU_QUERY, 0, "查询");
        menu.add(0, CONTEXT_MENU_INPUT, 0, "输入");
        menu.add(0, CONTEXT_MENU_DELETE, 0, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String holeId = getIntent().getStringExtra("SelectedHoleId");
        switch (item.getItemId()) {
            case CONTEXT_MENU_QUERY:
                Log.d(TAG, "Query hole " + holeId);

                Intent intent = new Intent(this, HoleInfoActivity.class);
                intent.putExtra("requestCode", "QUERY_HOLE");
                intent.putExtra("holeIndex", DataManager.getHoleIndexByHoleId(holeId));
                startActivityForResult(intent, QUERY_HOLE);
                break;
            case CONTEXT_MENU_INPUT:
                Log.d(TAG, "Input rigs " + holeId);
                Intent intentRig = new Intent(this, RigIndexActivity.class);
                intentRig.putExtra("holeId", holeId);
                startActivityForResult(intentRig, EDIT_RIG);
                break;
            case CONTEXT_MENU_DELETE:
                Log.d(TAG, "Remove hole " + holeId);
                for (Hole hole : DataManager.holes) {
                    if (holeId.equals(hole.getHoleId())) {
                        DataManager.holes.remove(hole);
                        break;
                    }
                }
                refreshTable();
                Toast.makeText(getApplicationContext(), "Remove hole " + holeId, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hole_index, menu);
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
        while (holesTable.getChildCount() != 1) {
            holesTable.removeViewAt(1);
        }

        drawHoleContent();
    }

    private void drawHoleContent() {
        Log.d(TAG, "Draw hole information");
        for (Hole hole : DataManager.holes) {
            TableRow row = new TableRow(this);

            row.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));

            TableLayout.LayoutParams tableLayoutParam = new TableLayout.LayoutParams();
            tableLayoutParam.setMargins(2, 2, 2, 2);
            row.setLayoutParams(tableLayoutParam);

            row.addView(createHoleContentTextView(hole.getHoleId()));
            row.addView(createHoleContentTextView(hole.getProjectName()));
            row.addView(createHoleContentTextView(hole.getProjectStage().toString()));
            row.addView(createHoleContentTextView(hole.getArticle().toString()));
            row.addView(createHoleContentTextView(String.valueOf(hole.getMileage())));
            row.addView(createHoleContentTextView(String.valueOf(hole.getOffset())));
            row.addView(createHoleContentTextView(String.valueOf(hole.getHoleElevation())));
            row.addView(createHoleContentTextView(String.valueOf(hole.getLongitudeDistance())));
            row.addView(createHoleContentTextView(String.valueOf(hole.getLatitudeDistance())));
            //TODO: no position description right now. need to add?
            row.addView(createHoleContentTextView("position placeholder"));
            row.addView(createHoleContentTextView(hole.getRecorderName()));
            row.addView(createHoleContentTextView(Utility.formatCalendarDateString(hole.getRecordDate())));
            row.addView(createHoleContentTextView(hole.getReviewerName()));
            row.addView(createHoleContentTextView(Utility.formatCalendarDateString(hole.getReviewDate())));
            row.addView(createHoleContentTextView(hole.getNote()));
            row.addView(createHoleContentTextView(String.valueOf(hole.getActuralDepth())));

            row.setTag(hole.getHoleId());

            row.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    setIntent(getIntent().putExtra("SelectedHoleId", v.getTag().toString()));
                    return false;
                }
            });

            registerForContextMenu(row);

            holesTable.addView(row);
        }

    }
    private TextView createHoleContentTextView(String text) {
        TextView temp = new TextView(this);
        temp.setText(text);
        temp.setBackgroundColor(getResources().getColor(android.R.color.white));

        TableRow.LayoutParams tableRowParam = new TableRow.LayoutParams();
        tableRowParam.setMargins(2, 2, 2, 2);
        temp.setLayoutParams(tableRowParam);

        return temp;
    }

    @Override
    public void onClick(View v) {
        String baseDir = Environment.getExternalStorageDirectory().getPath()+"/";
        String xlsPath = baseDir + "test.xls";
        String mdbPath = baseDir+"DlcGeoInfo.mdb";
        switch (v.getId()) {
            case R.id.button_add_hole:
                Log.d(TAG, "Add new hole button clicked.");
                Intent intent = new Intent(this, HoleInfoActivity.class);
                intent.putExtra("requestCode", "ADD_HOLE");
                startActivityForResult(intent, ADD_HOLE);
                break;
            case R.id.button_import_data:
                Log.d(TAG, "Import data button clicked.");

                try {
                    DataManager.holes.clear();
                    if(DataManager.holes.addAll(XlsParser.parse(xlsPath))){
                        Toast.makeText(getApplicationContext(), "导入成功！", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "导入失败！", Toast.LENGTH_SHORT).show();
                    }
                    refreshTable();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "导入失败！", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case R.id.button_output_data:
                Log.d(TAG, "Output data button clicked.");
                try {
                    AssetManager assetManageer = getAssets();
                    File mdbFile = new File(mdbPath);
                    if(!mdbFile.exists()) {
                        InputStream is = assetManageer.open("DlcGeoInfo.mdb");
                        Utility.copyFile(is,mdbFile);
                    }
                    if( XlsParser.parse(xlsPath,DataManager.holes) && HtmlParser.parse(baseDir, DataManager.holes,assetManageer) && MdbParser.parse(mdbFile,DataManager.holes)){
                        Toast.makeText(getApplicationContext(), "导出成功！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "导出失败！" , Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "导出失败！" , Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                refreshTable();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_HOLE) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "Get new hole.");
                refreshTable();
            }
        } else if (requestCode == QUERY_HOLE) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "Update hole.");
                refreshTable();
            }
        }
    }
}
