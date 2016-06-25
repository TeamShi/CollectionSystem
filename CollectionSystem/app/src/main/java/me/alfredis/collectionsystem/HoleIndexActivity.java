package me.alfredis.collectionsystem;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import me.alfredis.collectionsystem.datastructure.Hole;
import me.alfredis.collectionsystem.datastructure.RigEvent;
import me.alfredis.collectionsystem.parser.HtmlParser;
import me.alfredis.collectionsystem.parser.MdbParser;
import me.alfredis.collectionsystem.parser.XlsParser;


public class HoleIndexActivity extends ActionBarActivity implements View.OnClickListener{
    private TableLayout holesTable;
    private Button buttonAddHole;

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
        buttonAddHole.setOnClickListener(this);

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

            row.addView(createHoleContentTextView(Html.fromHtml(formatHoleId(hole.getHoleId()))));
            row.addView(createHoleContentTextView(hole.getProjectName()));
            row.addView(createHoleContentTextView(hole.getProjectStage().toString()));
            if (hole.getArticle() != Hole.ArticleType.NULL) {
                row.addView(createHoleContentTextView(hole.getArticle().toString()));
            } else {
                row.addView(createHoleContentTextView(hole.getArticleExtraString()));
            }
            row.addView(createHoleContentTextView(String.valueOf(hole.getMileage())));
            row.addView(createHoleContentTextView(String.valueOf(hole.getOffset())));
            row.addView(createHoleContentTextView(String.valueOf(hole.getHoleElevation())));
            row.addView(createHoleContentTextView(String.valueOf(hole.getLongitudeDistance())));
            row.addView(createHoleContentTextView(String.valueOf(hole.getLatitudeDistance())));
            row.addView(createHoleContentTextView(String.valueOf(hole.getPosition())));
            row.addView(createHoleContentTextView(hole.getRecorderName()));
            row.addView(createHoleContentTextView(Utility.formatCalendarDateString(hole.getRecordDate())));
            row.addView(createHoleContentTextView(hole.getReviewerName()));
            row.addView(createHoleContentTextView(Utility.formatCalendarDateString(hole.getReviewDate())));
            row.addView(createHoleContentTextView(hole.getNote()));
            row.addView(createHoleContentTextView(String.valueOf(hole.getActuralDepth())));

            row.setTag(hole.getHoleId());

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setIntent(getIntent().putExtra("SelectedHoleId", view.getTag().toString()));

                    String holeId = getIntent().getStringExtra("SelectedHoleId");
                    Log.d(TAG, "Input rigs " + holeId);
                    Intent intentRig = new Intent(HoleIndexActivity.this, RigIndexActivity.class);
                    intentRig.putExtra("holeId", holeId);
                    startActivityForResult(intentRig, EDIT_RIG);
                }
            });
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

    private TextView createHoleContentTextView(Spanned text) {
        TextView temp = new TextView(this);
        temp.setText(text);
        temp.setBackgroundColor(getResources().getColor(android.R.color.white));

        TableRow.LayoutParams tableRowParam = new TableRow.LayoutParams();
        tableRowParam.setMargins(6, 6, 6, 6);
        temp.setLayoutParams(tableRowParam);

        return temp;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_hole:
                Log.d(TAG, "Add new hole button clicked.");
                Intent intent = new Intent(this, HoleInfoActivity.class);
                intent.putExtra("requestCode", "ADD_HOLE");
                startActivityForResult(intent, ADD_HOLE);
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

    private String formatHoleId(String holeId) {
        StringBuilder sb = new StringBuilder();
        String[] temp = holeId.split("-");
        sb.append(temp[0] + "-");
        //To support the multiple part 1
        /*sb.append("J");
        if (temp[0].equals("JC")) {
            sb.append("<sub>c</sub>-");
        } else if (temp[0].equals("JZ")) {
            sb.append("<sub>z</sub)-");
        }*/

        if (temp[1].startsWith("I") && (!temp[1].startsWith("II"))) {
            sb.append("I");
            sb.append("<sub><small>" + temp[1].substring(1) + "</small></sub>");
        } else if (temp[1].startsWith("II")  && (!temp[1].startsWith("III"))) {
            sb.append("II");
            sb.append("<sub><small>" + temp[1].substring(2) + "</small></sub>");
        } else if (temp[1].startsWith("III")) {
            sb.append("III");
            sb.append("<sub><small>" + temp[1].substring(3) + "</small></sub>");
        } else if (temp[1].startsWith("IV")) {
            sb.append("IV");
            sb.append("<sub><small>" + temp[1].substring(2) + "</small></sub>");
        }

        sb.append("<sup><small>" + temp[2] +"</small></sup>-");
        sb.append(temp[3]);
        return sb.toString();
    }
}
