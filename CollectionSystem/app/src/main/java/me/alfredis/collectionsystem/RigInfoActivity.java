package me.alfredis.collectionsystem;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import me.alfredis.collectionsystem.datastructure.CasedRig;
import me.alfredis.collectionsystem.datastructure.DSTRig;
import me.alfredis.collectionsystem.datastructure.RigEvent;
import me.alfredis.collectionsystem.datastructure.SPTRig;

public class RigInfoActivity extends ActionBarActivity implements View.OnClickListener {

    private RigEvent rig;
    private String requestCode;
    private String holeId;
    private int selectedRigType;
    private int queryRigIndex;

    private boolean firstStart;

    private static final int STOP_RIG = 0;
    private static final int DRY_RIG = 1;
    private static final int WATER_MIX_RIG = 2;
    private static final int D_RIG = 3;
    private static final int STEEL_RIG = 4;
    private static final int SPT_RIG = 5;
    private static final int DST_RIG = 6;
    private static final int DOWN_RIG = 7;

    private static final int DST_DETAIL = 0;
    private static final int SPT_DETAIL = 1;

    private EditText classPeopleCountEditText;
    private EditText drillPipeIdEditText;
    private EditText drillPipeLengthEditText;
    private EditText cumulativeLengthEditText;
    private EditText coreBarreliLengthEditText;
    private EditText coreBarreliLengthPenetrationEditText;
    private EditText drillTypeEditText;
    private EditText drillDiameterEditText;
    private EditText drillLengthEditText;
    private EditText drillToolTotalLengthEditText;
    private EditText drillToolRemainLengthEditText;
    private EditText roundTripMeterageEditText;
    private EditText cumulativeMeteragEditText;
    private EditText rockCoreIdEditText;
    private EditText rockCoreLengthEditText;
    private EditText rockCoreRecoveryEditText;
    private EditText startEndDepthEditText;
    private EditText groundColorEditText;
    private EditText groundDensityEditText;
    private EditText groundSaturationEditText;
    private EditText groundWeatheringEditText;
    private EditText groundNoteEditText;
    private EditText rigHoleSaturationEditText;
    private EditText specialThingsEditText;

    private Button addRigButton;
    private Button startTimeButton;
    private Button endTimeButton;
    private Button rigDateButton;
    private Button sptButton;
    private Button dstButton;

    private TextView rigTimeDurationTextView;

    private Spinner rigTypeSpinner;
    private Spinner coreBarreliDiameterSpinner;
    private Spinner coreBarreliDiameterPenetrationSpinner;
    private Spinner groundNameSpinner;

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
    private TableRow downSpecialRigRow;

    private static final String TAG = "CollectionSystem";

    private static final int ADD_RIG = 0;
    private static final int QUERY_RIG = 1;

    private static final String[] RIG_TYPE_SPINNER_OPTIONS = {"搬家移孔、下雨停工，其他",
            "干钻", "合水钻", "金刚石钻", "钢粒钻", "标准贯入试验", "动力触探试验", "下套管"};
    private static final String[] CORE_BARRELI_DIAMATER_OPTIONS = {"98cm", "108cm", "130cm"};
    private static final String[] CORE_BARRELI_DIAMATER_PENETRATION_OPTIONS = {"98cm", "108cm", "130cm"};
    private static final String[] GROUND_NAME_OPTION = {"黏土", "粉质黏土", "粉土", "粉砂", "细砂", "粗砂",
    "砾砂", "细圆砾土", "粗圆砾土", "卵石", "块石", "漂石", "灰岩"};

    private static final HashMap<String, String> GROUND_COLOR_MAP = new HashMap<String, String>();
    private static final HashMap<String, String> GROUND_DENSITY_MAP = new HashMap<String, String>();
    private static final HashMap<String, String> GROUND_SATURATION_MAP = new HashMap<String, String>();
    private static final HashMap<String, String> GROUND_WEATHERING_MAP = new HashMap<String, String>();
    private static final HashMap<String, String> GROUND_NOTE_MAP = new HashMap<String, String>();

    static {
        for (String str : GROUND_NAME_OPTION) {
            GROUND_COLOR_MAP.put(str, str + "颜色");
        }

        for (String str : GROUND_NAME_OPTION) {
            GROUND_DENSITY_MAP.put(str, str + "稠度/密实度");
        }

        for (String str : GROUND_NAME_OPTION) {
            GROUND_SATURATION_MAP.put(str, str + "饱和度");
        }

        for (String str : GROUND_NAME_OPTION) {
            GROUND_WEATHERING_MAP.put(str, str + "风化程度");
        }

        for (String str : GROUND_NAME_OPTION) {
            GROUND_NOTE_MAP.put(str, str + "名称及岩性");
        }

    }

    private ArrayAdapter<String> rigTypeAdapter;
    private ArrayAdapter<String> coreBarreliDiameterAdapter;
    private ArrayAdapter<String> coreBarreliDiameterPenetrationAdapter;
    private ArrayAdapter<String> groundNameAdapter;

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
        downSpecialRigRow = (TableRow) findViewById(R.id.down_rig_detailed_table_row);

        classPeopleCountEditText = (EditText) findViewById(R.id.class_people_count);
        drillPipeIdEditText = (EditText) findViewById(R.id.edit_text_drill_pipe_id);
        drillPipeLengthEditText = (EditText) findViewById(R.id.edit_text_drill_pipe_length);
        cumulativeLengthEditText = (EditText) findViewById(R.id.edit_text_cumulative_length);
        coreBarreliLengthEditText = (EditText) findViewById(R.id.edit_text_core_barreli_length);
        coreBarreliLengthPenetrationEditText = (EditText) findViewById(R.id.edit_text_core_barreli_length_penetration);
        drillTypeEditText = (EditText) findViewById(R.id.edit_text_drill_type);
        drillDiameterEditText = (EditText) findViewById(R.id.edit_text_drill_diameter);
        drillLengthEditText = (EditText) findViewById(R.id.edit_text_drill_length);
        drillToolTotalLengthEditText = (EditText) findViewById(R.id.edit_text_drill_tool_total_length);
        drillToolRemainLengthEditText = (EditText) findViewById(R.id.edit_text_drill_tool_remain_length);
        roundTripMeterageEditText = (EditText) findViewById(R.id.edit_text_round_trip_meterage);
        cumulativeMeteragEditText = (EditText) findViewById(R.id.edit_text_cumulative_meterage);

        rockCoreIdEditText = (EditText) findViewById(R.id.edit_text_rock_core_id);
        rockCoreLengthEditText = (EditText) findViewById(R.id.edit_text_rock_core_length);
        rockCoreRecoveryEditText  = (EditText) findViewById(R.id.edit_text_rock_core_recovery);

        startEndDepthEditText = (EditText) findViewById(R.id.edit_text_start_depth);
        groundColorEditText = (EditText) findViewById(R.id.edit_text_ground_color);
        groundDensityEditText = (EditText) findViewById(R.id.edit_text_ground_density);
        groundSaturationEditText = (EditText) findViewById(R.id.edit_text_ground_saturation);
        groundWeatheringEditText = (EditText) findViewById(R.id.edit_text_ground_weathering);
        groundNoteEditText = (EditText) findViewById(R.id.edit_text_ground_note);

        rigHoleSaturationEditText = (EditText) findViewById(R.id.edit_text_rig_hole_situration);
        specialThingsEditText = (EditText) findViewById(R.id.edit_text_special_things);

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
                    cumulativeLengthEditText.setText(String.valueOf(DataManager.calculateCumulativePipeLength(holeId) + Double.parseDouble(s.toString())));
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
                    rig.setCumulativeLength(Double.parseDouble(s.toString()));
                    drillPipeLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    drillPipeLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        coreBarreliLengthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setCoreBarreliLength(Double.parseDouble(s.toString()));
                    coreBarreliLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    coreBarreliLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        //TODO: for SPT
        coreBarreliLengthPenetrationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        drillTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rig.setDrillType(s.toString());
            }
        });
        drillDiameterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setDrillDiameter(Double.parseDouble(s.toString()));
                    drillDiameterEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    drillDiameterEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        drillLengthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setDrillLength(Double.parseDouble(s.toString()));
                    drillLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    drillLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        drillToolTotalLengthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setDrillToolTotalLength(Double.parseDouble(s.toString()));
                    drillToolTotalLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    drillToolTotalLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        drillToolRemainLengthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setDrillToolRemainLength(Double.parseDouble(s.toString()));
                    drillToolRemainLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    drillToolRemainLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        roundTripMeterageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setRoundTripMeterage(Double.parseDouble(s.toString()));
                    roundTripMeterageEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    roundTripMeterageEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        cumulativeMeteragEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setCumulativeMeterage(Double.parseDouble(s.toString()));
                    cumulativeMeteragEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    cumulativeMeteragEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        rockCoreIdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rig.setRockCoreId(s.toString());
            }
        });
        rockCoreLengthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setRockCoreLength(Double.parseDouble(s.toString()));
                    rockCoreLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    rockCoreLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        rockCoreRecoveryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setRockCoreRecovery(Double.parseDouble(s.toString()));
                    rockCoreRecoveryEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    rockCoreRecoveryEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });

        startEndDepthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rig.setStartEndDiscription(s.toString());
            }
        });

        rigHoleSaturationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ((CasedRig) rig).setCasedSituation(s.toString());
            }
        });
        specialThingsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ((CasedRig) rig).setSpecialNote(s.toString());
            }
        });

        rigTimeDurationTextView = (TextView) findViewById(R.id.textview_rig_time_duration);

        rigTypeSpinner = (Spinner) findViewById(R.id.spinner_rig_type);
        coreBarreliDiameterSpinner = (Spinner) findViewById(R.id.spinner_core_barreli_diameter);
        coreBarreliDiameterPenetrationSpinner = (Spinner) findViewById(R.id.spinner_core_barreli_diameter_penetration);
        groundNameSpinner = (Spinner) findViewById(R.id.spinner_ground_name);

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
                        downSpecialRigRow.setVisibility(View.GONE);
                        selectedRigType = STOP_RIG;
                        if (!firstStart) {
                            rig = new RigEvent();

                            rig.setProjectName(RIG_TYPE_SPINNER_OPTIONS[selectedRigType]);
                            refreshRigInfoTable();
                        }

                        firstStart = false;
                        break;
                    case DRY_RIG:
                    case WATER_MIX_RIG:
                    case D_RIG:
                    case STEEL_RIG:
                        rigDrillTableRow.setVisibility(View.VISIBLE);
                        drillTableRow.setVisibility(View.VISIBLE);
                        coreBarreliTableRow.setVisibility(View.VISIBLE);
                        penetrationTableRow.setVisibility(View.GONE);
                        drillToolTableRow1.setVisibility(View.VISIBLE);
                        drillToolTableRow2.setVisibility(View.VISIBLE);
                        rockCoreTableRow.setVisibility(View.VISIBLE);
                        groundTableRow.setVisibility(View.VISIBLE);
                        groundTableRow2.setVisibility(View.VISIBLE);
                        groundTableRow3.setVisibility(View.VISIBLE);
                        wallTableRow.setVisibility(View.GONE);
                        wallTableRow2.setVisibility(View.GONE);
                        specialRigRow.setVisibility(View.GONE);
                        downSpecialRigRow.setVisibility(View.GONE);
                        selectedRigType = DRY_RIG;
                        if (!firstStart) {
                            rig = new RigEvent();

                            rig.setDrillPipeId(DataManager.getLatestRigPipeId(holeId));
                            rig.setCumulativeLength(DataManager.calculateCumulativePipeLength(holeId));

                            rig.setProjectName(RIG_TYPE_SPINNER_OPTIONS[position]);

                            refreshRigInfoTable();
                        }

                        firstStart = false;
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
                        downSpecialRigRow.setVisibility(View.GONE);
                        selectedRigType = SPT_RIG;
                        if (!firstStart) {
                            rig = new SPTRig(holeId);

                            rig.setProjectName(RIG_TYPE_SPINNER_OPTIONS[selectedRigType]);
                            refreshRigInfoTable();
                        }

                        firstStart = false;
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
                        if (!firstStart) {
                            rig = new DSTRig();

                            rig.setProjectName(RIG_TYPE_SPINNER_OPTIONS[selectedRigType]);

                            refreshRigInfoTable();
                        }

                        firstStart = false;
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
                        downSpecialRigRow.setVisibility(View.VISIBLE);
                        selectedRigType = DOWN_RIG;
                        if (!firstStart) {
                            rig = new CasedRig();

                            rig.setProjectName(RIG_TYPE_SPINNER_OPTIONS[selectedRigType]);

                            refreshRigInfoTable();
                        }

                        firstStart = false;
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        coreBarreliDiameterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CORE_BARRELI_DIAMATER_OPTIONS);
        coreBarreliDiameterSpinner.setAdapter(coreBarreliDiameterAdapter);
        coreBarreliDiameterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rig.setCoreBarreliDiameter(Double.valueOf(CORE_BARRELI_DIAMATER_OPTIONS[position].substring(0, CORE_BARRELI_DIAMATER_OPTIONS[position].indexOf("cm"))));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        coreBarreliDiameterPenetrationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CORE_BARRELI_DIAMATER_PENETRATION_OPTIONS);
        coreBarreliDiameterPenetrationSpinner.setAdapter(coreBarreliDiameterAdapter);
        coreBarreliDiameterPenetrationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        groundNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GROUND_NAME_OPTION);
        groundNameSpinner.setAdapter(groundNameAdapter);
        groundNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rig.setGroundName(parent.getItemAtPosition(position).toString());
                rig.setGroundColor(GROUND_COLOR_MAP.get(parent.getItemAtPosition(position).toString()));
                rig.setGroundDensity(GROUND_DENSITY_MAP.get(parent.getItemAtPosition(position).toString()));
                rig.setGroundSaturation(GROUND_SATURATION_MAP.get(parent.getItemAtPosition(position).toString()));
                rig.setGroundWeathering(GROUND_WEATHERING_MAP.get(parent.getItemAtPosition(position).toString()));
                rig.setSpecialNote(GROUND_NOTE_MAP.get(parent.getItemAtPosition(position).toString()));

                refreshRigInfoTable();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        requestCode = getIntent().getStringExtra("requestCode");

        firstStart = true;

        switch (requestCode) {
            case "ADD_RIG":
                rig = new RigEvent();

                queryRigIndex = DataManager.getRigEventListByHoleId(holeId).size();
                refreshRigInfoTable();
                break;
            case "QUERY_RIG":
                queryRigIndex = Integer.parseInt(getIntent().getStringExtra("rigIndex"));

                rig = DataManager.getRigEventListByHoleId(holeId).get(queryRigIndex);

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

                rig.setCumulativeLength(Double.valueOf(cumulativeLengthEditText.getText().toString()));

                if (requestCode.equals("ADD_RIG")) {
                    DataManager.AddRigByHoleId(holeId, rig);
                    this.setResult(RESULT_OK);
                    this.finish();
                } else if (requestCode.equals("QUERY_RIG")) {
                    DataManager.getRigEventListByHoleId(holeId).set(queryRigIndex, rig);
                    this.setResult(RESULT_OK);
                    this.finish();
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
            case R.id.button_dst_detail:
                Log.d(TAG, "Go to dst detail info.");
                Intent intent = new Intent(this, DSTRigDetailedActivity.class);
                intent.putExtra("holeId", holeId);
                intent.putExtra("rig", rig);
                startActivityForResult(intent, DST_DETAIL);
                break;
            case R.id.button_spt_detail:
                Log.d(TAG, "Go to spt detail info.");
                Intent intent2 = new Intent(this, SPTRigDetailedActivity.class);
                intent2.putExtra("holeId", holeId);
                intent2.putExtra("rig", rig);
                startActivityForResult(intent2, SPT_DETAIL);
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

        switch (rig.getProjectName()) {
            case "搬家移孔、下雨停工，其他":
                rigTypeSpinner.setSelection(0);
                break;
            case "干钻":
                rigTypeSpinner.setSelection(1);
                break;
            case "合水钻":
                rigTypeSpinner.setSelection(2);
                break;
            case "金刚石钻":
                rigTypeSpinner.setSelection(3);
                break;
            case "钢粒钻":
                rigTypeSpinner.setSelection(4);
                break;
            case "标准贯入试验":
                rigTypeSpinner.setSelection(5);
                break;
            case "动力触探试验":
                rigTypeSpinner.setSelection(6);
                break;
            case "下套管":
                rigTypeSpinner.setSelection(7);
                break;
        }

        for (int i = 0; i < CORE_BARRELI_DIAMATER_OPTIONS.length; i++) {
            if (Double.valueOf(CORE_BARRELI_DIAMATER_OPTIONS[i].substring(0, CORE_BARRELI_DIAMATER_OPTIONS[i].indexOf("cm")))
                    == rig.getCoreBarreliDiameter()) {
                coreBarreliDiameterSpinner.setSelection(i);
                break;
            }
        }

        coreBarreliLengthEditText.setText(String.valueOf(rig.getCoreBarreliLength()));

        drillTypeEditText.setText(rig.getDrillType());
        drillDiameterEditText.setText(String.valueOf(rig.getDrillDiameter()));
        drillLengthEditText.setText(String.valueOf(rig.getDrillLength()));

        drillToolTotalLengthEditText.setText(String.valueOf(rig.getDrillToolTotalLength()));
        drillToolRemainLengthEditText.setText(String.valueOf(rig.getDrillToolRemainLength()));
        roundTripMeterageEditText.setText(String.valueOf(rig.getRoundTripMeterage()));
        cumulativeMeteragEditText.setText(String.valueOf(rig.getCumulativeMeterage()));

        rockCoreIdEditText.setText(String.valueOf(rig.getRockCoreId()));
        rockCoreLengthEditText.setText(String.valueOf(rig.getRockCoreLength()));
        rockCoreRecoveryEditText.setText(String.valueOf(rig.getRockCoreRecovery()));

        for (int i = 0; i < GROUND_NAME_OPTION.length; i++) {
            if (rig.getGroundName().equals(GROUND_NAME_OPTION[i])) {
                groundNameSpinner.setSelection(i);
                break;
            }
        }

        groundColorEditText.setText(rig.getGroundColor());
        groundDensityEditText.setText(rig.getGroundDensity());
        groundSaturationEditText.setText(rig.getGroundSaturation());
        groundWeatheringEditText.setText(rig.getGroundWeathering());
        groundNoteEditText.setText(rig.getSpecialNote());
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SPT_DETAIL) {
            if (resultCode == RESULT_OK) {
                rig = (SPTRig) data.getSerializableExtra("rig");
                refreshRigInfoTable();
            }
        } else if (requestCode == DST_DETAIL) {
            if (resultCode == RESULT_OK) {
                rig = (DSTRig) data.getSerializableExtra("rig");
                refreshRigInfoTable();
            }
        }
    }
}
