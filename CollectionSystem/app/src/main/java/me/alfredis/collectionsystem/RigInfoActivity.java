package me.alfredis.collectionsystem;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

import me.alfredis.collectionsystem.datastructure.DSTRig;
import me.alfredis.collectionsystem.datastructure.RigEvent;
import me.alfredis.collectionsystem.datastructure.SPTRig;

public class RigInfoActivity extends ActionBarActivity implements View.OnClickListener {

    private RigEvent rig;
    private String requestCode;
    private String holeId;
    private int selectedRigType;

    private static final int STOP_RIG = 0;
    private static final int DRY_RIG = 1;
    private static final int WATER_MIX_RIG = 2;
    private static final int D_RIG = 3;
    private static final int STEEL_RIG = 4;
    private static final int SPT_RIG = 5;
    private static final int DST_RIG = 6;
    private static final int DOWN_RIG = 7;

    private EditText classPeopleCountEditText;
    private EditText drillPipeIdEditText;
    private EditText drillPipeLengthEditText;
    private EditText cumulativeLengthEditText;

    private Button addRigButton;
    private Button startTimeButton;
    private Button endTimeButton;
    private Button rigDateButton;
    private Button sptButton;
    private Button dstButton;

    private TextView rigTimeDurationTextView;

    private Spinner rigTypeSpinner;

    private TableRow rigDrillTableRow;
    private TableRow coreBarreliTableRow;
    private TableRow penetrationTableRow;
    private TableRow drillTableRow;
    private TableRow drillToolTableRow1;
    private TableRow drillToolTableRow2;
    private TableRow rockCoreTableRow;
    private TableRow groundTableRow;
    private TableRow groundTableRow2;
    private TableRow groundTableRow3;
    private TableRow wallTableRow;
    private TableRow wallTableRow2;
    private TableRow specialRigRow;

    private static final String TAG = "CollectionSystem";

    private static final int ADD_RIG = 0;

    private static final String[] RIG_TYPE_SPINNER_OPTIONS = {"搬家移孔、下雨停工，其他",
            "干钻", "合水钻", "金刚石钻", "钢粒钻", "标准贯入试验", "动力触探试验", "下套管"};

    private  ArrayAdapter<String> rigTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rig_info);

        holeId = getIntent().getStringExtra("holeId");

        rigDrillTableRow = (TableRow) findViewById(R.id.rig_drill_table_row);
        drillTableRow = (TableRow) findViewById(R.id.drill_table_row);
        coreBarreliTableRow = (TableRow) findViewById(R.id.coreBarreli_table_row);
        penetrationTableRow = (TableRow) findViewById(R.id.penetration_table_row);
        drillToolTableRow1 = (TableRow) findViewById(R.id.drill_tool_table_row);
        drillToolTableRow2 = (TableRow) findViewById(R.id.drill_tool_table_row_2);
        rockCoreTableRow = (TableRow) findViewById(R.id.rock_core_table_row);
        groundTableRow = (TableRow) findViewById(R.id.ground_table_row);
        groundTableRow2 = (TableRow) findViewById(R.id.ground_table_row_2);
        groundTableRow3 = (TableRow) findViewById(R.id.ground_table_row_3);
        wallTableRow = (TableRow) findViewById(R.id.wall_table_row);
        wallTableRow2 = (TableRow) findViewById(R.id.wall_table_row_2);
        specialRigRow = (TableRow) findViewById(R.id.special_rig_table_row);

        classPeopleCountEditText = (EditText) findViewById(R.id.class_people_count);
        drillPipeIdEditText = (EditText) findViewById(R.id.edit_text_drill_pipe_id);
        drillPipeLengthEditText = (EditText) findViewById(R.id.edit_text_drill_pipe_length);
        cumulativeLengthEditText = (EditText) findViewById(R.id.edit_text_cumulative_length);

        addRigButton = (Button) findViewById(R.id.button_confirm_add_rig);
        startTimeButton = (Button) findViewById(R.id.button_start_time);
        endTimeButton = (Button) findViewById(R.id.button_end_time);
        rigDateButton = (Button) findViewById(R.id.button_rig_date);
        sptButton = (Button) findViewById(R.id.button_spt_detail);
        dstButton = (Button) findViewById(R.id.button_dst_detail);

        addRigButton.setOnClickListener(this);
        startTimeButton.setOnClickListener(this);
        endTimeButton.setOnClickListener(this);
        rigDateButton.setOnClickListener(this);
        sptButton.setOnClickListener(this);
        dstButton.setOnClickListener(this);

        drillPipeIdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setDrillPipeId(Integer.parseInt(s.toString()));
                    drillPipeIdEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    drillPipeIdEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        drillPipeLengthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setDrillPipeLength(Double.parseDouble(s.toString()));
                    drillPipeLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                    rig.setCumulativeLength(rig.getCumulativeLength() + Double.parseDouble(s.toString()));
                    cumulativeLengthEditText.setText(String.valueOf(rig.getCumulativeLength()));
                } catch (Exception e) {
                    drillPipeLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        cumulativeLengthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setDrillPipeLength(Double.parseDouble(s.toString()));
                    drillPipeLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    drillPipeLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        rigTimeDurationTextView = (TextView) findViewById(R.id.textview_rig_time_duration);

        rigTypeSpinner = (Spinner) findViewById(R.id.spinner_rig_type);

        rigTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, RIG_TYPE_SPINNER_OPTIONS);
        rigTypeSpinner.setAdapter(rigTypeAdapter);
        rigTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case STOP_RIG:
                        rigDrillTableRow.setVisibility(View.GONE);
                        drillTableRow.setVisibility(View.GONE);
                        coreBarreliTableRow.setVisibility(View.GONE);
                        penetrationTableRow.setVisibility(View.GONE);
                        drillToolTableRow1.setVisibility(View.GONE);
                        drillToolTableRow2.setVisibility(View.GONE);
                        rockCoreTableRow.setVisibility(View.GONE);
                        groundTableRow.setVisibility(View.GONE);
                        groundTableRow2.setVisibility(View.GONE);
                        groundTableRow3.setVisibility(View.GONE);
                        wallTableRow.setVisibility(View.GONE);
                        wallTableRow2.setVisibility(View.GONE);
                        specialRigRow.setVisibility(View.GONE);
                        selectedRigType = STOP_RIG;
                        rig = new RigEvent();
                        break;
                    case DRY_RIG:
                    case WATER_MIX_RIG:
                    case D_RIG:
                    case STEEL_RIG:
                        rigDrillTableRow.setVisibility(View.VISIBLE);
                        drillTableRow.setVisibility(View.VISIBLE);
                        coreBarreliTableRow.setVisibility(View.VISIBLE);
                        penetrationTableRow.setVisibility(View.VISIBLE);
                        drillToolTableRow1.setVisibility(View.VISIBLE);
                        drillToolTableRow2.setVisibility(View.VISIBLE);
                        rockCoreTableRow.setVisibility(View.VISIBLE);
                        groundTableRow.setVisibility(View.VISIBLE);
                        groundTableRow2.setVisibility(View.VISIBLE);
                        groundTableRow3.setVisibility(View.VISIBLE);
                        wallTableRow.setVisibility(View.GONE);
                        wallTableRow2.setVisibility(View.GONE);
                        specialRigRow.setVisibility(View.GONE);
                        selectedRigType = DRY_RIG;
                        rig = new RigEvent();

                        rig.setDrillPipeId(DataManager.getLatestRigPipeId(holeId));
                        rig.setCumulativeLength(DataManager.calculateCumulativePipeLength(holeId));

                        refreshRigInfoTable();
                        break;
                    case SPT_RIG:
                        rigDrillTableRow.setVisibility(View.GONE);
                        drillTableRow.setVisibility(View.VISIBLE);
                        coreBarreliTableRow.setVisibility(View.GONE);
                        penetrationTableRow.setVisibility(View.VISIBLE);
                        drillToolTableRow1.setVisibility(View.VISIBLE);
                        drillToolTableRow2.setVisibility(View.VISIBLE);
                        rockCoreTableRow.setVisibility(View.GONE);
                        groundTableRow.setVisibility(View.GONE);
                        groundTableRow2.setVisibility(View.GONE);
                        groundTableRow3.setVisibility(View.GONE);
                        wallTableRow.setVisibility(View.GONE);
                        wallTableRow2.setVisibility(View.GONE);
                        specialRigRow.setVisibility(View.VISIBLE);
                        sptButton.setVisibility(View.VISIBLE);
                        dstButton.setVisibility(View.GONE);
                        selectedRigType = SPT_RIG;
                        rig = new SPTRig();
                        break;
                    case DST_RIG:
                        rigDrillTableRow.setVisibility(View.GONE);
                        drillTableRow.setVisibility(View.VISIBLE);
                        coreBarreliTableRow.setVisibility(View.VISIBLE);
                        penetrationTableRow.setVisibility(View.GONE);
                        drillToolTableRow1.setVisibility(View.VISIBLE);
                        drillToolTableRow2.setVisibility(View.VISIBLE);
                        rockCoreTableRow.setVisibility(View.GONE);
                        groundTableRow.setVisibility(View.GONE);
                        groundTableRow2.setVisibility(View.GONE);
                        groundTableRow3.setVisibility(View.GONE);
                        wallTableRow.setVisibility(View.GONE);
                        wallTableRow2.setVisibility(View.GONE);
                        specialRigRow.setVisibility(View.VISIBLE);
                        sptButton.setVisibility(View.GONE);
                        dstButton.setVisibility(View.VISIBLE);
                        selectedRigType = DST_RIG;
                        rig = new DSTRig();
                        break;
                    case DOWN_RIG:
                        rigDrillTableRow.setVisibility(View.GONE);
                        drillTableRow.setVisibility(View.GONE);
                        coreBarreliTableRow.setVisibility(View.GONE);
                        penetrationTableRow.setVisibility(View.GONE);
                        drillToolTableRow1.setVisibility(View.GONE);
                        drillToolTableRow2.setVisibility(View.GONE);
                        rockCoreTableRow.setVisibility(View.GONE);
                        groundTableRow.setVisibility(View.GONE);
                        groundTableRow2.setVisibility(View.GONE);
                        groundTableRow3.setVisibility(View.GONE);
                        wallTableRow.setVisibility(View.VISIBLE);
                        wallTableRow2.setVisibility(View.VISIBLE);
                        specialRigRow.setVisibility(View.GONE);
                        selectedRigType = DOWN_RIG;
                        rig = new RigEvent();
                        break;
                    default:
                        break;
                }
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
            case R.id.button_rig_date:
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
            case R.id.button_confirm_add_rig:
                Log.d(TAG, "Add button clicked.");

                if (requestCode.equals("ADD_RIG")) {
                    DataManager.AddRigByHoleId(holeId, rig);
                    this.setResult(RESULT_OK);
                    this.finish();
                } else if (requestCode.equals("QUERY_RIG")) {
                }
                break;
            case R.id.button_start_time:
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
                        if (rig.getStartTime().get(Calendar.HOUR_OF_DAY) > rig.getEndTime().get(Calendar.HOUR_OF_DAY)
                                || (rig.getStartTime().get(Calendar.HOUR_OF_DAY) == rig.getEndTime().get(Calendar.HOUR_OF_DAY)
                                && (rig.getStartTime().get(Calendar.MINUTE) > rig.getEndTime().get(Calendar.MINUTE)))) {
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
        classPeopleCountEditText.setText(rig.getClassPeopleCount());
        startTimeButton.setText(Utility.formatTimeString(rig.getStartTime()));
        endTimeButton.setText(Utility.formatTimeString(rig.getEndTime()));
        rigDateButton.setText(Utility.formatCalendarDateString(rig.getDate()));
        rigTimeDurationTextView.setText(Utility.calculateTimeSpan(rig.getStartTime(), rig.getEndTime()));
        drillPipeIdEditText.setText(String.valueOf(rig.getDrillPipeId()));
        drillPipeLengthEditText.setText(String.valueOf(rig.getDrillPipeLength()));
        cumulativeLengthEditText.setText(String.valueOf(rig.getCumulativeLength()));
    }
}
