package me.alfredis.collectionsystem;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

import me.alfredis.collectionsystem.datastructure.DSTRig;
import me.alfredis.collectionsystem.datastructure.RigEvent;


public class DSTRigDetailedActivity extends ActionBarActivity implements View.OnClickListener {

    private String holeId;

    private DSTRig rig;

    private Button applyButton;
    private Button rigDateButton;
    private Button startTimeButton;
    private Button endTimeButton;
    private Button previewDSTButton;
    private Button argumentReferenceDSTButton;

    private EditText classPeopleCountEditText;

    private TextView rigTimeSpanTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dstrig_detailed);

        holeId = getIntent().getStringExtra("holeId");

        rig = (DSTRig) getIntent().getSerializableExtra("rig");

        applyButton = (Button) findViewById(R.id.button_confirm_dst_detailed);
        rigDateButton = (Button) findViewById(R.id.button_rig_date_dst_detail);
        startTimeButton = (Button) findViewById(R.id.button_start_time_dst_detail);
        endTimeButton = (Button) findViewById(R.id.button_end_time_dst_detail);
        classPeopleCountEditText = (EditText) findViewById(R.id.edit_text_class_people_count_dst_detail);
        rigTimeSpanTextView = (TextView) findViewById(R.id.textview_rig_time_duration_dst_detail);
        previewDSTButton = (Button) findViewById(R.id.button_preview_dst);
        argumentReferenceDSTButton = (Button) findViewById(R.id.button_argument_reference_dst);

        applyButton.setOnClickListener(this);
        rigDateButton.setOnClickListener(this);
        startTimeButton.setOnClickListener(this);
        endTimeButton.setOnClickListener(this);
        previewDSTButton.setOnClickListener(this);
        argumentReferenceDSTButton.setOnClickListener(this);

        refreshRigInfoTable();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dstrig_detailed, menu);
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
        Calendar calendar = Calendar.getInstance();

        switch (v.getId()) {
            case R.id.button_rig_date_dst_detail:
                Calendar rigDate = rig.getDate();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(year, monthOfYear, dayOfMonth);
                        rig.setDate(temp);
                        rigDateButton.setText(Utility.formatCalendarDateString(rig.getDate()));
                    }
                }, rigDate.get(Calendar.YEAR), rigDate.get(Calendar.MONTH), rigDate.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.button_start_time_dst_detail:
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(1, 1, 1, hourOfDay, minute);
                        rig.setStartTime(temp);
                        if (rig.getStartTime().get(Calendar.HOUR_OF_DAY) > rig.getEndTime().get(Calendar.HOUR_OF_DAY)
                                || (rig.getStartTime().get(Calendar.HOUR_OF_DAY) == rig.getEndTime().get(Calendar.HOUR_OF_DAY)
                                && rig.getStartTime().get(Calendar.MINUTE) > rig.getEndTime().get(Calendar.MINUTE))) {
                            rig.setEndTime(rig.getStartTime());
                            endTimeButton.setText(Utility.formatTimeString(rig.getEndTime()));
                        }
                        startTimeButton.setText(Utility.formatTimeString(rig.getStartTime()));
                        rigTimeSpanTextView.setText(Utility.calculateTimeSpan(rig.getStartTime(), rig.getEndTime()));
                    }
                }, rig.getStartTime().get(Calendar.HOUR), rig.getStartTime().get(Calendar.MINUTE), true).show();
                break;
            case R.id.button_end_time_dst_detail:
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(1, 1, 1, hourOfDay, minute);
                        rig.setEndTime(temp);
                        if (rig.getStartTime().get(Calendar.HOUR_OF_DAY) > rig.getEndTime().get(Calendar.HOUR_OF_DAY)
                                || (rig.getStartTime().get(Calendar.HOUR_OF_DAY) == rig.getEndTime().get(Calendar.HOUR_OF_DAY)
                                && (rig.getStartTime().get(Calendar.MINUTE) > rig.getEndTime().get(Calendar.MINUTE)))) {
                            rig.setStartTime(rig.getEndTime());
                            startTimeButton.setText(Utility.formatTimeString(rig.getStartTime()));
                        }
                        endTimeButton.setText(Utility.formatTimeString(rig.getEndTime()));
                        rigTimeSpanTextView.setText(Utility.calculateTimeSpan(rig.getStartTime(), rig.getEndTime()));
                    }
                }, rig.getStartTime().get(Calendar.HOUR), rig.getStartTime().get(Calendar.MINUTE), true).show();
                break;
            case R.id.button_confirm_dst_detailed:
                Intent intent = new Intent();
                intent.putExtra("rig", rig);
                this.setResult(RESULT_OK, intent);
                this.finish();
                break;
            case R.id.button_preview_dst:
                //TODO: Johnson. Save the dst table to a temp folder and pass the folder to the intent
                Intent intent2 = new Intent(this, HtmlViewActivity.class);

                intent2.putExtra("table_path", "file:///sdcard/Download/a.html");
                startActivity(intent2);
                break;
            case R.id.button_argument_reference_dst:
                Intent intent3 = new Intent(this, ConfigurationActivity.class);

                intent3.putExtra("request_type", "QUERY_DST");
                startActivity(intent3);
                break;
        }
    }

    private void refreshRigInfoTable() {
        classPeopleCountEditText.setText(rig.getClassPeopleCount());
        startTimeButton.setText(Utility.formatTimeString(rig.getStartTime()));
        endTimeButton.setText(Utility.formatTimeString(rig.getEndTime()));
        rigDateButton.setText(Utility.formatCalendarDateString(rig.getDate()));
        rigTimeSpanTextView.setText(Utility.calculateTimeSpan(rig.getStartTime(), rig.getEndTime()));
    }
}
