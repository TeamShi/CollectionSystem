package me.alfredis.collectionsystem;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.Sampler;
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
import me.alfredis.collectionsystem.datastructure.SamplingRig;
import me.alfredis.collectionsystem.parser.XlsParser;

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
    private static final int SAMPLING = 8;

    private static final int DST_DETAIL = 0;
    private static final int SPT_DETAIL = 1;
    private static final int SAMPLING_DETAIL = 2;

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
    private EditText groundNoteEditText;
    private EditText rigHoleSaturationEditText;
    private EditText specialThingsEditText;

    private EditText wallTypeEditText;
    private EditText wallNumberEditText;
    private EditText wallDiameterEditText;
    private EditText wallTotalLengthEditText;

    private Button addRigButton;
    private Button startTimeButton;
    private Button endTimeButton;
    private Button rigDateButton;
    private Button sptButton;
    private Button dstButton;
    private Button samplingButton;

    private TextView rigTimeDurationTextView;

    private Spinner rigTypeSpinner;
    private Spinner coreBarreliDiameterSpinner;
    private Spinner coreBarreliDiameterPenetrationSpinner;
    private Spinner groundNameSpinner;
    private Spinner groundDestinySpinner;
    private Spinner groundSaturationSpinner;
    private Spinner groundWeatheringSpinner;
    private Spinner groundColorSpinner;

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
            "干钻", "合水钻", "金刚石钻", "钢粒钻", "标准贯入试验", "动力触探试验", "下套管", "取样"};
    private static final String[] CORE_BARRELI_DIAMATER_OPTIONS = {"89cm", "108cm", "127cm"};
    private static final String[] CORE_BARRELI_DIAMATER_PENETRATION_OPTIONS = {"51mm", "74mm"};
    private static final String[] GROUND_NAME_OPTION = {"黏土", "粉质黏土", "粉土", "粉砂", "细砂", "中砂" , "粗砂", "砾砂", "漂石",
            "块石", "卵石", "碎石", "粗圆砾", "粗角砾", "细圆砾", "细角砾"};
    private static final String[] GROUND_DENSITY_OPTION = {"坚硬", "硬塑", "软塑", "流塑", "稍密", "中密", "密实", "松散"};
    private static final String[] GROUND_SATURATION_OPTION = {"稍湿", "潮湿", "饱和"};
    private static final String[] GROUND_COLOR_OPTION = {"浅蓝色", "蓝色", "浅蓝色", "灰色", "青灰色", "深灰色", "紫色", "棕黄色", "浅黄色", "褐黄色", "红褐色", "棕红色", "棕色", "褐色", "黄褐色",
            "青色","灰绿色","浅紫色", "暗红色", "黑色"};
    private static final String[] GROUND_WEATHERING_OPTION = {"全风化", "强风化", "中风化", "弱风化", "微风化", "未风化"};

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
    private ArrayAdapter<String> groundDestinyAdapter;
    private ArrayAdapter<String> groundSaturationAdapter;
    private ArrayAdapter<String> groundColorAdapter;
    private ArrayAdapter<String> groundWeatheringAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rig_info);

        holeId = getIntent().getStringExtra("holeId");
        requestCode = getIntent().getStringExtra("requestCode");

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
        rockCoreRecoveryEditText = (EditText) findViewById(R.id.edit_text_rock_core_recovery);

        startEndDepthEditText = (EditText) findViewById(R.id.edit_text_start_depth);
        groundNoteEditText = (EditText) findViewById(R.id.edit_text_ground_note);

        wallTypeEditText = (EditText) findViewById(R.id.edit_text_wall_type);
        wallNumberEditText = (EditText) findViewById(R.id.edit_text_wall_number);
        wallDiameterEditText = (EditText) findViewById(R.id.edit_text_wall_diameter);
        wallTotalLengthEditText = (EditText) findViewById(R.id.edit_text_wall_total_length);

        rigHoleSaturationEditText = (EditText) findViewById(R.id.edit_text_rig_hole_situration);
        specialThingsEditText = (EditText) findViewById(R.id.edit_text_special_things);

        addRigButton = (Button) findViewById(R.id.button_confirm_add_rig);
        startTimeButton = (Button) findViewById(R.id.button_start_time);
        endTimeButton = (Button) findViewById(R.id.button_end_time);
        rigDateButton = (Button) findViewById(R.id.button_rig_date);
        sptButton = (Button) findViewById(R.id.button_spt_detail);
        dstButton = (Button) findViewById(R.id.button_dst_detail);
        samplingButton = (Button) findViewById(R.id.button_sampling_detail);

        addRigButton.setOnClickListener(this);
        startTimeButton.setOnClickListener(this);
        endTimeButton.setOnClickListener(this);
        rigDateButton.setOnClickListener(this);
        sptButton.setOnClickListener(this);
        dstButton.setOnClickListener(this);
        samplingButton.setOnClickListener(this);

        classPeopleCountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                rig.setClassPeopleCount(s.toString());
            }
        });

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
                    if (requestCode.equals("ADD_RIG") && !firstStart) {
                        if (DataManager.getHole(holeId).getCurrentRemainToolLength() != 0) {
                            rig.setDrillToolTotalLength(DataManager.getHole(holeId).getCurrentDrillToolLength());
                            rig.setCumulativeLength(DataManager.getHole(holeId).getCurrentDrillPipeTotalLength());
                            rig.setDrillToolRemainLength(Double.parseDouble(s.toString()));
                            drillToolRemainLengthEditText.setText(String.valueOf(rig.getDrillToolRemainLength()));
                            drillToolTotalLengthEditText.setText(String.valueOf(rig.getDrillToolTotalLength()));
                            cumulativeLengthEditText.setText(String.valueOf(rig.getCumulativeLength()));
                        } else {
                            rig.setDrillToolTotalLength(Double.parseDouble(s.toString()) + DataManager.getHole(holeId).getCurrentDrillToolLength());
                            rig.setCumulativeLength(Double.parseDouble(s.toString()) + DataManager.getHole(holeId).getCurrentDrillPipeTotalLength());
                            rig.setDrillToolRemainLength(Double.parseDouble(s.toString()));
                            drillToolRemainLengthEditText.setText(String.valueOf(rig.getDrillToolRemainLength()));
                            drillToolTotalLengthEditText.setText(String.valueOf(rig.getDrillToolTotalLength()));
                            cumulativeLengthEditText.setText(String.valueOf(rig.getCumulativeLength()));
                        }
                    }

                    if (DataManager.getHole(holeId).getCurrentRemainToolLength() != 0) {
                        cumulativeLengthEditText.setText(String.valueOf(Double.parseDouble(s.toString())));
                    } else {
                        cumulativeLengthEditText.setText(String.valueOf(DataManager.getHole(holeId).getCurrentDrillPipeTotalLength() + Double.parseDouble(s.toString())));
                    }
                    drillPipeLengthEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
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

        //TODO: Alfred. for SPT
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

                    if (requestCode.equals("ADD_RIG") && !firstStart) {
                        rig.setCumulativeMeterage(Double.parseDouble(s.toString()) + DataManager.getHole(holeId).getCurrentTotalInputLength());
                        if (rig.getDrillPipeId() > DataManager.getHole(holeId).getCurrentDrillToolCount()) {
                            rig.setDrillToolRemainLength(DataManager.getHole(holeId).getCurrentRemainToolLength() + rig.getDrillPipeLength() - Double.parseDouble(s.toString()));
                        } else {
                            rig.setDrillToolRemainLength(DataManager.getHole(holeId).getCurrentRemainToolLength() - Double.parseDouble(s.toString()));
                        }
                        cumulativeMeteragEditText.setText(String.valueOf(rig.getCumulativeMeterage()));
                        drillToolRemainLengthEditText.setText(String.valueOf(rig.getDrillToolRemainLength()));

                        rig.setRockCoreLength(Double.parseDouble(rig.toString()));
                        rockCoreLengthEditText.setText(String.valueOf(rig.getCoreBarreliLength()));
                        rig.setRockCoreRecovery(1.0);
                        rockCoreRecoveryEditText.setText("100%");

                        rig.setStartEndDiscription(String.valueOf(rig.getCumulativeMeterage() - rig.getRoundTripMeterage()) + "~" + String.valueOf(rig.getCumulativeMeterage()));
                        startEndDepthEditText.setText(String.valueOf(rig.getCumulativeMeterage() - rig.getRoundTripMeterage()) + "~" + String.valueOf(rig.getCumulativeMeterage()));
                    }

                    roundTripMeterageEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    if (!(e instanceof NumberFormatException)) {
                        roundTripMeterageEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    }
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

                rig.setStartEndDiscription(String.valueOf(rig.getCumulativeMeterage() - rig.getRoundTripMeterage()) + "~" + String.valueOf(rig.getCumulativeMeterage()));
                startEndDepthEditText.setText(String.valueOf(rig.getCumulativeMeterage() - rig.getRoundTripMeterage()) + "~" + String.valueOf(rig.getCumulativeMeterage()));
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

                    rig.setRockCoreRecovery(rig.getRockCoreLength() / rig.getRoundTripMeterage());
                    rockCoreRecoveryEditText.setText(String.format("%.2f", rig.getRockCoreRecovery() * 100) + "%");
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
                    //rockCoreRecoveryEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
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

        wallTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ((CasedRig) rig).setDadoType(s.toString());
            }
        });

        wallNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    ((CasedRig) rig).setCasedId(Integer.parseInt(s.toString()));
                } catch (Exception e) {

                }
            }
        });

        wallDiameterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    ((CasedRig) rig).setCasedDiameter(Double.parseDouble(s.toString()));
                } catch (Exception e) {

                }
            }
        });

        wallTotalLengthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    ((CasedRig) rig).setCasedTotalLength(Double.parseDouble(s.toString()));
                } catch (Exception e) {

                }
            }
        });

        rigTimeDurationTextView = (TextView) findViewById(R.id.textview_rig_time_duration);

        rigTypeSpinner = (Spinner) findViewById(R.id.spinner_rig_type);
        coreBarreliDiameterSpinner = (Spinner) findViewById(R.id.spinner_core_barreli_diameter);
        coreBarreliDiameterPenetrationSpinner = (Spinner) findViewById(R.id.spinner_core_barreli_diameter_penetration);
        groundNameSpinner = (Spinner) findViewById(R.id.spinner_ground_name);
        groundDestinySpinner = (Spinner) findViewById(R.id.spinner_ground_density);
        groundSaturationSpinner = (Spinner) findViewById(R.id.spinner_ground_saturation);
        groundColorSpinner = (Spinner) findViewById(R.id.spinner_ground_color);
        groundWeatheringSpinner = (Spinner) findViewById(R.id.spinner_ground_weathering);

        groundColorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GROUND_COLOR_OPTION);
        groundColorSpinner.setAdapter(groundColorAdapter);
        groundColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rig.setGroundName(parent.getItemAtPosition(position).toString());
                rig.setGroundColor(GROUND_COLOR_OPTION[groundColorSpinner.getSelectedItemPosition()]);
                rig.setGroundDensity(GROUND_DENSITY_OPTION[groundDestinySpinner.getSelectedItemPosition()]);
                rig.setGroundSaturation(GROUND_SATURATION_OPTION[groundSaturationSpinner.getSelectedItemPosition()]);
                rig.setGroundWeathering(GROUND_WEATHERING_OPTION[groundWeatheringSpinner.getSelectedItemPosition()]);
                rig.setSpecialNote(rig.getGroundName() + ", " + rig.getGroundColor() + ", " + rig.getGroundDensity() + ", "
                        + rig.getGroundSaturation() + ", " + rig.getGroundWeathering());

                groundNoteEditText.setText(rig.getGroundName() + ", " + rig.getGroundColor() + ", " + rig.getGroundDensity() + ", "
                        + rig.getGroundSaturation() + ", " + rig.getGroundWeathering());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        groundWeatheringAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GROUND_WEATHERING_OPTION);
        groundWeatheringSpinner.setAdapter(groundWeatheringAdapter);
        groundWeatheringSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rig.setGroundName(parent.getItemAtPosition(position).toString());
                rig.setGroundColor(GROUND_COLOR_OPTION[groundColorSpinner.getSelectedItemPosition()]);
                rig.setGroundDensity(GROUND_DENSITY_OPTION[groundDestinySpinner.getSelectedItemPosition()]);
                rig.setGroundSaturation(GROUND_SATURATION_OPTION[groundSaturationSpinner.getSelectedItemPosition()]);
                rig.setGroundWeathering(GROUND_WEATHERING_OPTION[groundWeatheringSpinner.getSelectedItemPosition()]);
                rig.setSpecialNote(rig.getGroundName() + ", " + rig.getGroundColor() + ", " + rig.getGroundDensity() + ", "
                        + rig.getGroundSaturation() + ", " + rig.getGroundWeathering());

                groundNoteEditText.setText(rig.getGroundName() + ", " + rig.getGroundColor() + ", " + rig.getGroundDensity() + ", "
                        + rig.getGroundSaturation() + ", " + rig.getGroundWeathering());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        groundDestinyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GROUND_DENSITY_OPTION);
        groundDestinySpinner.setAdapter(groundDestinyAdapter);
        groundDestinySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rig.setGroundName(parent.getItemAtPosition(position).toString());
                rig.setGroundColor(GROUND_COLOR_OPTION[groundColorSpinner.getSelectedItemPosition()]);
                rig.setGroundDensity(GROUND_DENSITY_OPTION[groundDestinySpinner.getSelectedItemPosition()]);
                rig.setGroundSaturation(GROUND_SATURATION_OPTION[groundSaturationSpinner.getSelectedItemPosition()]);
                rig.setGroundWeathering(GROUND_WEATHERING_OPTION[groundWeatheringSpinner.getSelectedItemPosition()]);
                rig.setSpecialNote(rig.getGroundName() + ", " + rig.getGroundColor() + ", " + rig.getGroundDensity() + ", "
                        + rig.getGroundSaturation() + ", " + rig.getGroundWeathering());

                groundNoteEditText.setText(rig.getGroundName() + ", " + rig.getGroundColor() + ", " + rig.getGroundDensity() + ", "
                        + rig.getGroundSaturation() + ", " + rig.getGroundWeathering());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        groundSaturationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GROUND_SATURATION_OPTION);
        groundSaturationSpinner.setAdapter(groundSaturationAdapter);
        groundSaturationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rig.setGroundName(parent.getItemAtPosition(position).toString());
                rig.setGroundColor(GROUND_COLOR_OPTION[groundColorSpinner.getSelectedItemPosition()]);
                rig.setGroundDensity(GROUND_DENSITY_OPTION[groundDestinySpinner.getSelectedItemPosition()]);
                rig.setGroundSaturation(GROUND_SATURATION_OPTION[groundSaturationSpinner.getSelectedItemPosition()]);
                rig.setGroundWeathering(GROUND_WEATHERING_OPTION[groundWeatheringSpinner.getSelectedItemPosition()]);
                rig.setSpecialNote(rig.getGroundName() + ", " + rig.getGroundColor() + ", " + rig.getGroundDensity() + ", "
                        + rig.getGroundSaturation() + ", " + rig.getGroundWeathering());

                groundNoteEditText.setText(rig.getGroundName() + ", " + rig.getGroundColor() + ", " + rig.getGroundDensity() + ", "
                        + rig.getGroundSaturation() + ", " + rig.getGroundWeathering());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rigTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, RIG_TYPE_SPINNER_OPTIONS);
        rigTypeSpinner.setAdapter(rigTypeAdapter);
        rigTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String rigInitClass = DataManager.getHole(holeId).getRigInitClass();

                Calendar c = DataManager.getHole(holeId).getRigInitStartDate();



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

                            if (rigInitClass != null) {
                                rig.setClassPeopleCount(rigInitClass);
                            }

                            if (c != null) {
                                rig.setDate(c);
                            }

                            refreshRigInfoTable();
                        }

                        rig.setProjectName(RIG_TYPE_SPINNER_OPTIONS[selectedRigType]);

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

                            if (rigInitClass != null) {
                                rig.setClassPeopleCount(rigInitClass);
                            }

                            if (c != null) {
                                rig.setDate(c);
                            }

                            if (DataManager.getHole(holeId).getCurrentRemainToolLength() == 0) {
                                rig.setDrillPipeId(DataManager.getHole(holeId).getCurrentDrillToolCount() + 1);

                                rig.setDrillPipeLength(0);
                                rig.setDrillToolTotalLength(DataManager.getHole(holeId).getCurrentDrillToolLength());
                                rig.setDrillToolRemainLength(DataManager.getHole(holeId).getCurrentRemainToolLength());
                                rig.setRoundTripMeterage(0);
                                rig.setCumulativeMeterage(DataManager.getHole(holeId).getCurrentTotalInputLength());
                                rig.setCumulativeLength(DataManager.getHole(holeId).getCurrentDrillPipeTotalLength());
                            } else {
                                rig.setDrillPipeId(DataManager.getHole(holeId).getCurrentDrillToolCount());

                                rig.setDrillPipeLength(DataManager.getHole(holeId).getLastDrillToolLength());
                                rig.setDrillToolTotalLength(DataManager.getHole(holeId).getCurrentDrillToolLength());
                                rig.setDrillToolRemainLength(DataManager.getHole(holeId).getCurrentRemainToolLength());
                                rig.setRoundTripMeterage(0);
                                rig.setCumulativeMeterage(DataManager.getHole(holeId).getCurrentTotalInputLength());
                                rig.setCumulativeLength(DataManager.getHole(holeId).getCurrentDrillPipeTotalLength());
                            }

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
                        samplingButton.setVisibility(View.GONE);
                        downSpecialRigRow.setVisibility(View.GONE);
                        selectedRigType = SPT_RIG;
                        if (!firstStart) {
                            rig = new SPTRig(holeId);

                            if (rigInitClass != null) {
                                rig.setClassPeopleCount(rigInitClass);
                            }

                            if (c != null) {
                                rig.setDate(c);
                            }

                            if (DataManager.getHole(holeId).getCurrentRemainToolLength() == 0) {
                                rig.setDrillToolTotalLength(DataManager.getHole(holeId).getCurrentDrillToolLength());
                                rig.setDrillToolRemainLength(DataManager.getHole(holeId).getCurrentRemainToolLength());
                                rig.setRoundTripMeterage(0);
                                rig.setCumulativeMeterage(DataManager.getHole(holeId).getCurrentTotalInputLength());
                            } else {
                                rig.setDrillToolTotalLength(DataManager.getHole(holeId).getCurrentDrillToolLength());
                                rig.setDrillToolRemainLength(DataManager.getHole(holeId).getCurrentRemainToolLength());
                                rig.setRoundTripMeterage(0);
                                rig.setCumulativeMeterage(DataManager.getHole(holeId).getCurrentTotalInputLength());
                            }

                            rig.setDrillType("管靴");
                            drillTypeEditText.setText(rig.getDrillType());
                            rig.setDrillDiameter(51);
                            drillDiameterEditText.setText(String.valueOf(rig.getDrillDiameter()));
                            rig.setDrillLength(50);
                            drillLengthEditText.setText(String.valueOf(rig.getDrillLength()));
                            rig.setPenetrationLength(5);
                            coreBarreliLengthPenetrationEditText.setText(String.valueOf(rig.getPenetrationLength()));
                            coreBarreliDiameterPenetrationSpinner.setSelection(0);
                            rig.setPenetrationDiameter(51);
                            rig.setProjectName(RIG_TYPE_SPINNER_OPTIONS[selectedRigType]);
                            refreshRigInfoTable();
                        }

                        firstStart = false;
                        break;
                    case DST_RIG:
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
                        sptButton.setVisibility(View.GONE);
                        dstButton.setVisibility(View.VISIBLE);
                        samplingButton.setVisibility(View.GONE);
                        selectedRigType = DST_RIG;
                        if (!firstStart) {
                            rig = new DSTRig();

                            if (rigInitClass != null) {
                                rig.setClassPeopleCount(rigInitClass);
                            }

                            if (c != null) {
                                rig.setDate(c);
                            }

                            rig.setProjectName(RIG_TYPE_SPINNER_OPTIONS[selectedRigType]);

                            refreshRigInfoTable();
                        }

                        if (DataManager.getHole(holeId).getCurrentRemainToolLength() == 0) {
                            rig.setDrillToolTotalLength(DataManager.getHole(holeId).getCurrentDrillToolLength());
                            rig.setDrillToolRemainLength(DataManager.getHole(holeId).getCurrentRemainToolLength());
                            rig.setRoundTripMeterage(0);
                            rig.setCumulativeMeterage(DataManager.getHole(holeId).getCurrentTotalInputLength());
                        } else {
                            rig.setDrillToolTotalLength(DataManager.getHole(holeId).getCurrentDrillToolLength());
                            rig.setDrillToolRemainLength(DataManager.getHole(holeId).getCurrentRemainToolLength());
                            rig.setRoundTripMeterage(0);
                            rig.setCumulativeMeterage(DataManager.getHole(holeId).getCurrentTotalInputLength());
                        }

                        TextView tv = (TextView) findViewById(R.id.drill_name_text_view);
                        tv.setText("探头组成");
                        TextView tv2 = (TextView) findViewById(R.id.textview_temp);
                        tv2.setText("贯入器");
                        rig.setDrillLength(250);
                        drillLengthEditText.setText(String.valueOf(rig.getDrillLength()));
                        coreBarreliDiameterPenetrationSpinner.setSelection(1);
                        rig.setPenetrationDiameter(74);
                        coreBarreliLengthPenetrationEditText.setText(String.valueOf(rig.getPenetrationLength()));
                        rig.setProjectName(RIG_TYPE_SPINNER_OPTIONS[selectedRigType]);

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

                            if (rigInitClass != null) {
                                rig.setClassPeopleCount(rigInitClass);
                            }

                            if (c != null) {
                                rig.setDate(c);
                            }

                            rig.setProjectName(RIG_TYPE_SPINNER_OPTIONS[selectedRigType]);

                            refreshRigInfoTable();
                        }

                        firstStart = false;
                        break;
                    case SAMPLING:
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
                        specialRigRow.setVisibility(View.VISIBLE);
                        sptButton.setVisibility(View.GONE);
                        dstButton.setVisibility(View.GONE);
                        samplingButton.setVisibility(View.VISIBLE);
                        downSpecialRigRow.setVisibility(View.GONE);
                        selectedRigType = SAMPLING;
                        if (!firstStart) {
                            rig = new SamplingRig();

                            if (rigInitClass != null) {
                                rig.setClassPeopleCount(rigInitClass);
                            }

                            if (c != null) {
                                rig.setDate(c);
                            }

                            refreshRigInfoTable();
                        }

                        rig.setProjectName(RIG_TYPE_SPINNER_OPTIONS[selectedRigType]);

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
        coreBarreliDiameterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rig.setCoreBarreliDiameter(Double.valueOf(CORE_BARRELI_DIAMATER_OPTIONS[position].substring(0, CORE_BARRELI_DIAMATER_OPTIONS[position].indexOf("cm"))));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        coreBarreliDiameterPenetrationAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CORE_BARRELI_DIAMATER_PENETRATION_OPTIONS);
        coreBarreliDiameterPenetrationSpinner.setAdapter(coreBarreliDiameterPenetrationAdapter);
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
                rig.setGroundColor(GROUND_COLOR_OPTION[groundColorSpinner.getSelectedItemPosition()]);
                rig.setGroundDensity(GROUND_DENSITY_OPTION[groundDestinySpinner.getSelectedItemPosition()]);
                rig.setGroundSaturation(GROUND_SATURATION_OPTION[groundSaturationSpinner.getSelectedItemPosition()]);
                rig.setGroundWeathering(GROUND_WEATHERING_OPTION[groundWeatheringSpinner.getSelectedItemPosition()]);
                rig.setSpecialNote(rig.getGroundName() + ", " + rig.getGroundColor() + ", " + rig.getGroundDensity() + ", "
                        + rig.getGroundSaturation() + ", " + rig.getGroundWeathering());

                refreshRigInfoTable();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        firstStart = true;



        switch (requestCode) {
            case "ADD_RIG":
                rig = new RigEvent();

                queryRigIndex = DataManager.getRigEventListByHoleId(holeId).size();

                String rigInitClass = DataManager.getHole(holeId).getRigInitClass();
                if (rigInitClass != null) {
                    rig.setClassPeopleCount(rigInitClass);
                }

                Calendar c = DataManager.getHole(holeId).getRigInitStartDate();
                if (c != null) {
                    rig.setDate(c);
                }

                refreshRigInfoTable();
                break;
            case "QUERY_RIG":
                queryRigIndex = Integer.parseInt(getIntent().getStringExtra("rigIndex"));

                addRigButton.setText("保存");

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
                    if (rigTypeSpinner.getSelectedItemPosition() >= 1 && rigTypeSpinner.getSelectedItemPosition() <= 6) {
                        if (rig.getDrillPipeId() > DataManager.getHole(holeId).getCurrentDrillToolCount()) {
                            DataManager.getHole(holeId).setCurrentDrillToolCount(DataManager.getHole(holeId).getCurrentDrillToolCount() + 1);
                            DataManager.getHole(holeId).setLastDrillToolLength(rig.getDrillPipeLength());
                            DataManager.getHole(holeId).setCurrentDrillToolLength(rig.getDrillToolTotalLength());
                            DataManager.getHole(holeId).setCurrentTotalInputLength(rig.getCumulativeMeterage());
                            DataManager.getHole(holeId).setCurrentRemainToolLength(rig.getDrillToolRemainLength());
                            DataManager.getHole(holeId).setCurrentDrillPipeTotalLength(rig.getCumulativeLength());
                        } else {
                            DataManager.getHole(holeId).setCurrentTotalInputLength(rig.getCumulativeMeterage());
                            DataManager.getHole(holeId).setCurrentRemainToolLength(rig.getDrillToolRemainLength());
                        }
                    }


                    DataManager.AddRigByHoleId(holeId, rig);
                    DataManager.getHole(holeId).setActuralDepth(rig.getCumulativeMeterage());

                    if (DataManager.getHole(holeId).getRigInitClass() == null) {
                        DataManager.getHole(holeId).setRigInitClass(rig.getClassPeopleCount());
                    }

                    if (DataManager.getHole(holeId).getRigInitStartDate() == null) {
                        DataManager.getHole(holeId).setRigInitStartDate(rig.getDate());
                    }

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
            case R.id.button_sampling_detail:
                Log.d(TAG, "Go to sampling detail info");
                Intent intent3 = new Intent(this, SamplingDetailedActivity.class);
                intent3.putExtra("holeId", holeId);
                intent3.putExtra("rig", rig);
                startActivityForResult(intent3, SAMPLING_DETAIL);
            default:
                break;
        }
        //backup
        String xlsPath = Environment.getExternalStorageDirectory().getPath()+"/ZuanTan/" + "zuantan.xls";
        XlsParser.parse(xlsPath, DataManager.holes);
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
            case "取样":
                rigTypeSpinner.setSelection(8);
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
        } else if (requestCode == SAMPLING_DETAIL) {
            if (resultCode == RESULT_OK) {
                rig = (SamplingRig) data.getSerializableExtra("rig");
                refreshRigInfoTable();
            }
        }
    }
}
