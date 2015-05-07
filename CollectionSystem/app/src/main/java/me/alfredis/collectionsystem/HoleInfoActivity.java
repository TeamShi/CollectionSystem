package me.alfredis.collectionsystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import me.alfredis.collectionsystem.datastructure.Hole;


public class HoleInfoActivity extends ActionBarActivity implements View.OnClickListener {


    private Hole hole;
    private String requestCode;

    private Button addButton;
    private Button backButton;
    private Button startDateButton;
    private Button endDateButton;
    private EditText projectNameEditText;
    //TODO: ask for holeId


    private static final String TAG = "CollectionSystem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole_info);

        hole = (Hole) getIntent().getSerializableExtra("hole");

        addButton = (Button) findViewById(R.id.button_confirm_add_hole);
        backButton = (Button) findViewById(R.id.button_cancel_add_hole);
        startDateButton = (Button) findViewById(R.id.button_hole_start_date);
        endDateButton = (Button) findViewById(R.id.button_hole_end_date);

        projectNameEditText = (EditText) findViewById(R.id.edittext_hole_project_name);

        addButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        startDateButton.setOnClickListener(this);
        endDateButton.setOnClickListener(this);

        requestCode = getIntent().getStringExtra("requestCode");

        switch (requestCode) {
            case "ADD_HOLE":
                initializeDatePicker();
                break;
            case "QUERY_HOLE":
                projectNameEditText.setText(hole.getProjectName());
                break;
            default:
                break;
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hole_info, menu);
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

    @Override
    public void onClick(View v) {
        Intent intent;

        Calendar calendar = Calendar.getInstance();

        switch (v.getId()) {
            case R.id.button_confirm_add_hole:
                Log.d(TAG, "Add button clicked.");

                //test code
                Random r = new Random();
                Hole hole = new Hole(String.valueOf(r.nextInt()), "pn", "a", "a", 123, 123.45, 123, 123, 123, "alfred", new Date(1212313), "alfred", new Date(123123123), "test note", 123123);

                intent = new Intent();
                intent.putExtra("hole", hole);
                this.setResult(RESULT_OK, intent);
                this.finish();
                break;
            case R.id.button_cancel_add_hole:
                Log.d(TAG, "Cancel button clicked.");
                intent = new Intent();
                this.setResult(RESULT_CANCELED, intent);
                this.finish();
                break;
            case R.id.button_hole_start_date:
                String [] startDateStringArray = startDateButton.getText().toString().split("/");

                int currentYearStart = Integer.valueOf(startDateStringArray[0]);
                int currentMonthStart = Integer.valueOf(startDateStringArray[1]);
                int currentDayOfMonthStart = Integer.valueOf(startDateStringArray[2]);

                DatePickerDialog dpdStart = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        startDateButton.setText(year +"/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    }
                }, currentYearStart, currentMonthStart, currentDayOfMonthStart);

                dpdStart.show();
                break;
            case R.id.button_hole_end_date:
                String [] endDateStringArray = endDateButton.getText().toString().split("/");

                int currentYearEnd = Integer.valueOf(endDateStringArray[0]);
                int currentMonthEnd = Integer.valueOf(endDateStringArray[1]);
                int currentDayOfMonthEnd = Integer.valueOf(endDateStringArray[2]);

                DatePickerDialog dpdEnd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        endDateButton.setText(year +"/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    }
                }, currentYearEnd, currentMonthEnd, currentDayOfMonthEnd);

                dpdEnd.show();
            default:
                break;
        }
    }

    private void initializeDatePicker() {
        Calendar calendar = Calendar.getInstance();

        startDateButton.setText(calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH));
        endDateButton.setText(calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/" + calendar.get(Calendar.DAY_OF_MONTH));
    }
}
