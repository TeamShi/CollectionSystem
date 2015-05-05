package me.alfredis.collectionsystem;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import me.alfredis.collectionsystem.datastructure.Hole;


public class HoleIndexActivity extends ActionBarActivity implements View.OnClickListener{

    private ArrayList<Hole> holes = new ArrayList<>();
    private TableLayout holesTable;
    private Button buttonAddHole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole_index);

        holesTable = (TableLayout) findViewById(R.id.hole_table);
        buttonAddHole = (Button) findViewById(R.id.button_add_hole);


        refreshTable();
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
        holesTable.removeAllViews();

        drawTableHead();
    }

    private void drawTableHead() {
        TableRow row = new TableRow(this);

        row.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));

        TableLayout.LayoutParams tableLayoutParam = new TableLayout.LayoutParams();
        tableLayoutParam.setMargins(2, 2, 2, 2);
        row.setLayoutParams(tableLayoutParam);

        row.addView(createHeaderTextView("勘探点名称"));
        row.addView(createHeaderTextView("工程名称"));
        row.addView(createHeaderTextView("阶段"));
        row.addView(createHeaderTextView("冠词"));
        row.addView(createHeaderTextView("里程"));
        row.addView(createHeaderTextView("偏移量"));
        row.addView(createHeaderTextView("高程"));
        row.addView(createHeaderTextView("经距X"));
        row.addView(createHeaderTextView("纬距Y"));
        row.addView(createHeaderTextView("位置描述"));
        row.addView(createHeaderTextView("记录者"));
        row.addView(createHeaderTextView("记录日期"));
        row.addView(createHeaderTextView("复核者"));
        row.addView(createHeaderTextView("复核日期"));
        row.addView(createHeaderTextView("附注"));
        row.addView(createHeaderTextView("孔深"));

        registerForContextMenu(row);

        holesTable.addView(row);
    }

    private TextView createHeaderTextView(String text) {
        TextView temp = new TextView(this);
        temp.setText(text);
        temp.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));

        TableRow.LayoutParams tableRowParam = new TableRow.LayoutParams();
        tableRowParam.setMargins(2,2,2,2);
        temp.setLayoutParams(tableRowParam);

        return temp;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case v.get
        }
    }
}
