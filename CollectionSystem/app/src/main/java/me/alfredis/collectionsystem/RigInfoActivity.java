package me.alfredis.collectionsystem;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import me.alfredis.collectionsystem.datastructure.RigEvent;

public class RigInfoActivity extends ActionBarActivity implements View.OnClickListener {

    private RigEvent rig;
    private String requestCode;

    private EditText classPeopleCountEditText;

    private Button addRigButton;
    private Button startTimeButton;
    private Button endTimeButton;

    private TextView rigTimeDurationTextView;

    private Spinner rigTypeSpinner;

    private static final String TAG = "CollectionSystem";

    private static final int ADD_RIG = 0;

    private static final String[] RIG_TYPE_SPINNER_OPTIONS = {"搬家移孔、下雨停工，其他",
            "干钻", "合水钻", "金刚石钻", "x粒钻", "标准贯入试验", "动力触探试验", "下套管"};

    private  ArrayAdapter<String> rigTypeAdapter;

    private BlankRigFragment blankRigFragment;
    private NormalRigFragment normalRigFragment;
    private DSTRigFragment dstRigFragment;
    private SPTRigFragment sptRigFragment;
    private CasedRigFragment casedRigFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rig_info);

        blankRigFragment = new BlankRigFragment();
        normalRigFragment = new NormalRigFragment();
        dstRigFragment = new DSTRigFragment();
        sptRigFragment = new SPTRigFragment();
        casedRigFragment = new CasedRigFragment();

        classPeopleCountEditText = (EditText) findViewById(R.id.class_people_count);

        addRigButton = (Button) findViewById(R.id.button_confirm_add_rig);
        startTimeButton = (Button) findViewById(R.id.button_start_time);
        endTimeButton = (Button) findViewById(R.id.button_end_time);

        addRigButton.setOnClickListener(this);
        startTimeButton.setOnClickListener(this);
        endTimeButton.setOnClickListener(this);

        rigTimeDurationTextView = (TextView) findViewById(R.id.textview_rig_time_duration);

        rigTypeSpinner = (Spinner) findViewById(R.id.spinner_rig_type);

        rigTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, RIG_TYPE_SPINNER_OPTIONS);
        rigTypeSpinner.setAdapter(rigTypeAdapter);
        rigTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        getFragmentManager().beginTransaction().replace(R.id.fragment_rig_details, blankRigFragment).commit();
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        getFragmentManager().beginTransaction().replace(R.id.fragment_rig_details, normalRigFragment).commit();
                        break;
                    case 5:
                        getFragmentManager().beginTransaction().replace(R.id.fragment_rig_details, dstRigFragment).commit();
                        break;
                    case 6:
                        getFragmentManager().beginTransaction().replace(R.id.fragment_rig_details, sptRigFragment).commit();
                        break;
                    case 7:
                        getFragmentManager().beginTransaction().replace(R.id.fragment_rig_details, casedRigFragment).commit();
                        break;
                    default:
                        break;
                }
                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        requestCode = getIntent().getStringExtra("requestCode");

        switch (requestCode) {
            case "ADD_RIG":
                rig = new RigEvent();

                refreshRigInfoTable();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rig_info, menu);
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
            case R.id.button_confirm_add_rig:
                break;
            case R.id.button_start_time:
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(1, 1, 1, hourOfDay, minute);
                        rig.setStartTime(temp);
                        if (rig.getStartTime().compareTo(rig.getEndTime()) > 0) {
                            rig.setEndTime(rig.getStartTime());
                            endTimeButton.setText(Utility.formatTimeString(rig.getEndTime()));
                        }
                        startTimeButton.setText(Utility.formatTimeString(rig.getStartTime()));
                        rigTimeDurationTextView.setText(Utility.calculateTimeSpan(rig.getStartTime(), rig.getEndTime()));
                    }
                }, rig.getStartTime().get(Calendar.HOUR), rig.getStartTime().get(Calendar.MINUTE), true).show();
                break;
            case R.id.button_end_time:
                new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(1, 1, 1, hourOfDay, minute);
                        rig.setEndTime(temp);
                        if (rig.getStartTime().compareTo(rig.getEndTime()) > 0) {
                            rig.setStartTime(rig.getEndTime());
                            startTimeButton.setText(Utility.formatTimeString(rig.getStartTime()));
                        }
                        endTimeButton.setText(Utility.formatTimeString(rig.getEndTime()));
                        rigTimeDurationTextView.setText(Utility.calculateTimeSpan(rig.getStartTime(), rig.getEndTime()));
                    }
                }, rig.getStartTime().get(Calendar.HOUR), rig.getStartTime().get(Calendar.MINUTE), true).show();
                break;
            default:
                break;
        }
    }

    private void refreshRigInfoTable() {
        startTimeButton.setText(Utility.formatTimeString(rig.getStartTime()));
        endTimeButton.setText(Utility.formatTimeString(rig.getEndTime()));
    }
}
