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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import me.alfredis.collectionsystem.datastructure.Hole;


public class HoleInfoActivity extends ActionBarActivity implements View.OnClickListener {


    private Hole hole;
    private String requestCode;

    private Button addButton;
    private Button backButton;
    private Button startDateButton;
    private Button endDateButton;
    private Button initialLevelButton;
    private Button stableLevelButton;
    private Button recordDateButton;
    private Button reviewDateButton;

    private EditText projectNameEditText;
    //TODO: ask for holeId


    private static final String TAG = "CollectionSystem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole_info);

        addButton = (Button) findViewById(R.id.button_confirm_add_hole);
        backButton = (Button) findViewById(R.id.button_cancel_add_hole);
        startDateButton = (Button) findViewById(R.id.button_hole_start_date);
        endDateButton = (Button) findViewById(R.id.button_hole_end_date);
        initialLevelButton = (Button) findViewById(R.id.button_initial_level_date);
        stableLevelButton = (Button) findViewById(R.id.button_stable_level_date);
        recordDateButton = (Button) findViewById(R.id.button_record_date);
        reviewDateButton = (Button) findViewById(R.id.button_review_date);

        projectNameEditText = (EditText) findViewById(R.id.edittext_hole_project_name);

        addButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        startDateButton.setOnClickListener(this);
        endDateButton.setOnClickListener(this);
        initialLevelButton.setOnClickListener(this);
        stableLevelButton.setOnClickListener(this);
        recordDateButton.setOnClickListener(this);
        reviewDateButton.setOnClickListener(this);

        requestCode = getIntent().getStringExtra("requestCode");

        switch (requestCode) {
            case "ADD_HOLE":
                hole = new Hole("1", "pn", "a", "a", 123, 123.45, 123, 123, 123, "alfred", "alfred", "test note", 123123);

                refreshHoleInfoTable();
                break;
            case "QUERY_HOLE":
                hole = (Hole) getIntent().getSerializableExtra("hole");

                refreshHoleInfoTable();
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
                GregorianCalendar gc = new GregorianCalendar();
                gc.set(2013, 2, 4);
                Hole hole2 = new Hole(String.valueOf(r.nextInt()), "pn", "a", "a", 123, 123.45, 123, 123, 123, "alfred", "alfred", "test note", 123123);

                intent = new Intent();
                intent.putExtra("hole", hole2);
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
                Calendar startDate = hole.getStartDate();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(year, monthOfYear, dayOfMonth);
                        hole.setStartDate(temp);
                        refreshHoleInfoTable();
                    }
                }, startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.button_hole_end_date:
                Calendar endDate = hole.getEndDate();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(year, monthOfYear, dayOfMonth);
                        hole.setEndDate(temp);
                        refreshHoleInfoTable();
                    }
                }, endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.button_initial_level_date:
                Calendar initialLevelDate = hole.getInitialLevelMeasuringDate();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(year, monthOfYear, dayOfMonth);
                        hole.setInitialLevelMeasuringDate(temp);
                        refreshHoleInfoTable();
                    }
                }, initialLevelDate.get(Calendar.YEAR), initialLevelDate.get(Calendar.MONTH), initialLevelDate.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.button_stable_level_date:
                Calendar stableLevelDate = hole.getStableLevelMeasuringDate();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(year, monthOfYear, dayOfMonth);
                        hole.setStableLevelMeasuringDate(temp);
                        refreshHoleInfoTable();
                    }
                }, stableLevelDate.get(Calendar.YEAR), stableLevelDate.get(Calendar.MONTH), stableLevelDate.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.button_record_date:
                Calendar recordDate = hole.getRecordDate();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(year, monthOfYear, dayOfMonth);
                        hole.setRecordDate(temp);
                        refreshHoleInfoTable();
                    }
                }, recordDate.get(Calendar.YEAR), recordDate.get(Calendar.MONTH), recordDate.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.button_review_date:
                Calendar reviewDate = hole.getReviewDate();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(year, monthOfYear, dayOfMonth);
                        hole.setReviewDate(temp);
                        refreshHoleInfoTable();
                    }
                }, reviewDate.get(Calendar.YEAR), reviewDate.get(Calendar.MONTH), reviewDate.get(Calendar.DAY_OF_MONTH)).show();
                break;
            default:
                break;
        }
    }

    private void refreshHoleInfoTable() {
        projectNameEditText.setText(hole.getProjectName());

        startDateButton.setText(Utility.formatCalendarDateString(hole.getStartDate()));
        endDateButton.setText(Utility.formatCalendarDateString(hole.getEndDate()));
        initialLevelButton.setText(Utility.formatCalendarDateString(hole.getInitialLevelMeasuringDate()));
        stableLevelButton.setText(Utility.formatCalendarDateString(hole.getStableLevelMeasuringDate()));
        recordDateButton.setText(Utility.formatCalendarDateString(hole.getRecordDate()));
        reviewDateButton.setText(Utility.formatCalendarDateString(hole.getReviewDate()));
    }
}
