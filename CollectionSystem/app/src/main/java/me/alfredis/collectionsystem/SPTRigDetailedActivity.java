package me.alfredis.collectionsystem;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import me.alfredis.collectionsystem.datastructure.Hole;
import me.alfredis.collectionsystem.datastructure.RigEvent;
import me.alfredis.collectionsystem.datastructure.SPTRig;
import me.alfredis.collectionsystem.parser.HtmlParser;
import me.alfredis.collectionsystem.parser.XlsParser;


public class SPTRigDetailedActivity extends ActionBarActivity implements View.OnClickListener {

    private String holeId;

    private SPTRig rig;

    private static final String[] GROUND_NAME_OPTION = {"黏土", "粉质黏土", "粉土", "粉砂", "细砂", "中砂" , "粗砂", "砾砂", "漂石",
            "块石", "卵石", "碎石", "粗圆砾", "粗角砾", "细圆砾", "细角砾"};
    private static final String[] GROUND_SATURATION_OPTION = {"稍湿", "潮湿", "饱和"};
    private static final String[] GROUND_COLOR_OPTION = {"浅蓝色", "蓝色", "浅蓝色", "灰色", "青灰色", "深灰色", "紫色", "棕黄色", "浅黄色", "褐黄色", "红褐色", "棕红色", "棕色", "褐色", "黄褐色",
            "青色","灰绿色","浅紫色", "暗红色", "黑色"};

    private Button applyButton;
    private Button rigDateButton;
    private Button startTimeButton;
    private Button endTimeButton;
    private Button previewSPTButton;
    private Button argumentReferenceButton;

    private EditText classPeopleCountEditText;
    private EditText sptEventTotalStartEditText;
    private EditText sptEventTotalEndEditText;
    private EditText sptEventCountDepth1StartEditText;
    private EditText sptEventCountDepth1EndEditText;
    private EditText sptHit1EditText;
    private EditText sptEventCountDepth2StartEditText;
    private EditText sptEventCountDepth2EndEditText;
    private EditText sptHit2EditText;
    private EditText sptEventCountDepth3StartEditText;
    private EditText sptEventCountDepth3EndEditText;
    private EditText sptHit3EditText;
    private EditText sptEventDig1StartEditText;
    private EditText sptEventDig1EndEditText;
    private EditText sptEventDig2StartEditText;
    private EditText sptEventDig2EndEditText;
    private EditText sptEventDig3StartEditText;
    private EditText sptEventDig3EndEditText;
    private EditText sptHitTotalCountEditText;

    private EditText sptGroundColorEditText;
    private EditText sptDestinyEditText;
    private EditText sptSaturationEditText;
    private EditText sptOtherEditText;

    private Spinner rockNameSpinner;

    private ArrayAdapter<String> rockNameAdapter;

    private static final String[] ROCK_NAME_ARRAY = {"黏性土", "砂类土", "粉土"};

    private TextView rigTimeSpanTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sptrig_detailed);

        holeId = getIntent().getStringExtra("holeId");

        rig = (SPTRig) getIntent().getSerializableExtra("rig");

        rockNameSpinner = (Spinner) findViewById(R.id.spinner_spt_rock_name_spt);

        applyButton = (Button) findViewById(R.id.button_confirm_spt_detailed);
        rigDateButton = (Button) findViewById(R.id.button_rig_date_spt_detail);
        startTimeButton = (Button) findViewById(R.id.button_start_time_spt_detail);
        endTimeButton = (Button) findViewById(R.id.button_end_time_spt_detail);
        previewSPTButton = (Button) findViewById(R.id.button_preview_spt);
        classPeopleCountEditText = (EditText) findViewById(R.id.edit_text_class_people_count_spt_detail);
        rigTimeSpanTextView = (TextView) findViewById(R.id.textview_rig_time_duration_spt_detail);
        argumentReferenceButton = (Button) findViewById(R.id.button_argument_reference);

        sptEventTotalStartEditText = (EditText) findViewById(R.id.edit_text_spt_event_total_start);
        sptEventTotalEndEditText = (EditText) findViewById(R.id.edit_text_spt_event_total_end);
        sptEventCountDepth1StartEditText = (EditText) findViewById(R.id.edit_text_spt_event_count_depth_1_start);
        sptEventCountDepth1EndEditText  = (EditText) findViewById(R.id.edit_text_spt_event_count_depth_1_end);
        sptHit1EditText = (EditText) findViewById(R.id.edit_text_spt_hit_1);
        sptEventCountDepth2StartEditText  = (EditText) findViewById(R.id.edit_text_spt_event_count_depth_2_start);
        sptEventCountDepth2EndEditText = (EditText) findViewById(R.id.edit_text_spt_event_count_depth_2_end);
        sptHit2EditText = (EditText) findViewById(R.id.edit_text_spt_hit_2);
        sptEventCountDepth3StartEditText = (EditText) findViewById(R.id.edit_text_spt_event_count_depth_3_start);
        sptEventCountDepth3EndEditText = (EditText) findViewById(R.id.edit_text_spt_event_count_depth_3_end);
        sptHit3EditText = (EditText) findViewById(R.id.edit_text_spt_hit_3);
        sptHitTotalCountEditText = (EditText) findViewById(R.id.edit_text_spt_hit_total_count);

        sptGroundColorEditText = (EditText) findViewById(R.id.edit_text_spt_ground_color);
        sptDestinyEditText = (EditText) findViewById(R.id.edit_text_spt_destiny);
        sptSaturationEditText = (EditText) findViewById(R.id.edit_text_spt_saturation);
        sptOtherEditText = (EditText) findViewById(R.id.edit_text_spt_other);

        sptEventDig1StartEditText = (EditText) findViewById(R.id.edit_text_spt_event_dig_1_start);
        sptEventDig1EndEditText = (EditText) findViewById(R.id.edit_text_spt_event_dig_1_end);
        sptEventDig2StartEditText = (EditText) findViewById(R.id.edit_text_spt_event_dig_2_start);
        sptEventDig2EndEditText = (EditText) findViewById(R.id.edit_text_spt_event_dig_2_end);
        sptEventDig3StartEditText = (EditText) findViewById(R.id.edit_text_spt_event_dig_3_start);
        sptEventDig3EndEditText = (EditText) findViewById(R.id.edit_text_spt_event_dig_3_end);

        applyButton.setOnClickListener(this);
        rigDateButton.setOnClickListener(this);
        startTimeButton.setOnClickListener(this);
        endTimeButton.setOnClickListener(this);
        previewSPTButton.setOnClickListener(this);
        argumentReferenceButton.setOnClickListener(this);

        rockNameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ROCK_NAME_ARRAY);
        rockNameSpinner.setAdapter(rockNameAdapter);
        rockNameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sptDestinyEditText.setText(ConfigurationManager.getSPTDestiny(rockNameSpinner.getSelectedItemPosition(), Integer.parseInt(sptHitTotalCountEditText.getText().toString())));
                rig.setGroundDensity(sptDestinyEditText.getText().toString());
                rig.setGroundName(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sptGroundColorEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rig.setGroundColor(editable.toString());
            }
        });

        sptSaturationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rig.setGroundSaturation(editable.toString());
            }
        });

        sptOtherEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rig.setSpecialNote(editable.toString());
            }
        });

        sptEventTotalStartEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setPenetrationFrom(Double.valueOf(s.toString()));
                    sptEventTotalStartEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptEventTotalEndEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }


                rig.setPenetrationTo(rig.getPenetrationFrom() + 0.45);
                rig.setPenetration1DepthFrom(rig.getPenetrationFrom() + 0.15);
                rig.setPenetration1DepthTo(rig.getPenetration1DepthFrom() + 0.1);
                rig.setPenetration2DepthFrom(rig.getPenetration1DepthFrom() + 0.1);
                rig.setPenetration2DepthTo(rig.getPenetration2DepthFrom() + 0.1);
                rig.setPenetration3DepthFrom(rig.getPenetration2DepthFrom() + 0.1);
                rig.setPenetration3DepthTo(rig.getPenetration3DepthFrom() + 0.1);

                rig.setRig1DepthFrom(rig.getPenetrationTo());
                rig.setRig1DepthTo(rig.getPenetrationTo() + 0.25);
                rig.setRig2DepthFrom(rig.getRig1DepthFrom());
                rig.setRig2DepthTo(rig.getRig2DepthFrom() + 0.1);
                rig.setRig3DepthFrom(rig.getRig2DepthTo());
                rig.setRig3DepthTo(rig.getRig3DepthFrom() + 0.1);

                sptEventTotalEndEditText.setText(String.format("%.2f", rig.getPenetrationTo()));
                sptEventCountDepth1StartEditText.setText(String.format("%.2f", rig.getPenetration1DepthFrom()));
                sptEventCountDepth1EndEditText.setText(String.format("%.2f", rig.getPenetration1DepthTo()));
                sptHit1EditText.setText(String.valueOf(rig.getPenetration1Count()));
                sptEventCountDepth2StartEditText.setText(String.format("%.2f", rig.getPenetration2DepthFrom()));
                sptEventCountDepth2EndEditText.setText(String.format("%.2f", rig.getPenetration2DepthTo()));
                sptHit2EditText.setText(String.valueOf(rig.getPenetration2Count()));
                sptEventCountDepth3StartEditText.setText(String.format("%.2f", rig.getPenetration3DepthFrom()));
                sptEventCountDepth3EndEditText.setText(String.format("%.2f", rig.getPenetration3DepthTo()));
                sptHit3EditText.setText(String.valueOf(rig.getPenetration3Count()));

                sptEventDig1StartEditText.setText(String.format("%.2f", rig.getRig1DepthFrom()));
                sptEventDig1EndEditText.setText(String.format("%.2f", rig.getRig1DepthTo()));
                sptEventDig2StartEditText.setText(String.format("%.2f", rig.getRig2DepthFrom()));
                sptEventDig2EndEditText.setText(String.format("%.2f", rig.getRig2DepthTo()));
                sptEventDig3StartEditText.setText(String.format("%.2f", rig.getRig3DepthFrom()));
                sptEventDig3EndEditText.setText(String.format("%.2f", rig.getRig3DepthTo()));
            }
        });
        sptEventTotalEndEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setPenetrationTo(Double.valueOf(s.toString()));
                    sptEventTotalEndEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptEventTotalEndEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        sptEventCountDepth1StartEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setPenetration1DepthFrom(Double.valueOf(s.toString()));
                    sptEventCountDepth1StartEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptEventCountDepth1StartEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        sptEventCountDepth1EndEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setPenetration1DepthTo(Double.valueOf(s.toString()));
                    sptEventCountDepth1EndEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptEventCountDepth1EndEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        sptHit1EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setPenetration1Count(Integer.valueOf(s.toString()));
                    sptHit1EditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptHit1EditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    return;
                }

                int totalCount = rig.getPenetration1Count() + rig.getPenetration2Count() + rig.getPenetration3Count();
                sptHitTotalCountEditText.setText(String.valueOf(totalCount));
                sptDestinyEditText.setText(ConfigurationManager.getSPTDestiny(rockNameSpinner.getSelectedItemPosition(), totalCount));
                rig.setGroundDensity(sptDestinyEditText.getText().toString());
                if (totalCount > 50) {
                    Toast.makeText(getApplicationContext(), "三次贯入之和不得大于50", Toast.LENGTH_SHORT).show();
                    sptHit1EditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    sptHit2EditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    sptHit3EditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    sptHitTotalCountEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                } else {
                    sptHit1EditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                    sptHit2EditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                    sptHit3EditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                    sptHitTotalCountEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                }

                rig.setCumulativeCount(totalCount);
            }
        });
        sptEventCountDepth2StartEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setPenetration2DepthFrom(Double.valueOf(s.toString()));
                    sptEventCountDepth2StartEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptEventCountDepth2StartEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        sptEventCountDepth2EndEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setPenetration2DepthTo(Double.valueOf(s.toString()));
                    sptEventCountDepth2EndEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptEventCountDepth2EndEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        sptHit2EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setPenetration2Count(Integer.valueOf(s.toString()));
                    sptHit2EditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptHit2EditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    return;
                }

                int totalCount = rig.getPenetration1Count() + rig.getPenetration2Count() + rig.getPenetration3Count();
                sptHitTotalCountEditText.setText(String.valueOf(totalCount));
                sptDestinyEditText.setText(ConfigurationManager.getSPTDestiny(rockNameSpinner.getSelectedItemPosition(), totalCount));
                rig.setGroundDensity(sptDestinyEditText.getText().toString());
                if (totalCount > 50) {
                    Toast.makeText(getApplicationContext(), "三次贯入之和不得大于50", Toast.LENGTH_SHORT).show();
                    sptHit1EditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    sptHit2EditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    sptHit3EditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    sptHitTotalCountEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));

                } else {
                    sptHit1EditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                    sptHit2EditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                    sptHit3EditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                    sptHitTotalCountEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                }


                rig.setCumulativeCount(totalCount);
            }
        });
        sptEventCountDepth3StartEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setPenetration3DepthFrom(Double.valueOf(s.toString()));
                    sptEventCountDepth3StartEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptEventCountDepth3StartEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        sptEventCountDepth3EndEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setPenetration3DepthTo(Double.valueOf(s.toString()));
                    sptEventCountDepth3EndEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptEventCountDepth3EndEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        sptHit3EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setPenetration3Count(Integer.valueOf(s.toString()));
                    sptHit3EditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptHit3EditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    return;
                }

                int totalCount = rig.getPenetration1Count() + rig.getPenetration2Count() + rig.getPenetration3Count();
                sptHitTotalCountEditText.setText(String.valueOf(totalCount));
                sptDestinyEditText.setText(ConfigurationManager.getSPTDestiny(rockNameSpinner.getSelectedItemPosition(), totalCount));
                rig.setGroundDensity(sptDestinyEditText.getText().toString());
                if (totalCount > 50) {
                    Toast.makeText(getApplicationContext(), "三次贯入之和不得大于50", Toast.LENGTH_SHORT).show();
                    sptHit1EditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    sptHit2EditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    sptHit3EditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    sptHitTotalCountEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                } else {
                    sptHit1EditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                    sptHit2EditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                    sptHitTotalCountEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                }

                rig.setCumulativeCount(totalCount);
            }
        });

        sptEventDig1StartEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setRig1DepthFrom(Double.valueOf(s.toString()));
                    sptEventDig1StartEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptEventDig1StartEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    return;
                }
            }
        });
        sptEventDig1EndEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setRig1DepthTo(Double.valueOf(s.toString()));
                    sptEventDig1EndEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptEventDig1EndEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    return;
                }
            }
        });
        sptEventDig2StartEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setRig2DepthFrom(Double.valueOf(s.toString()));
                    sptEventDig2StartEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptEventDig2StartEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    return;
                }
            }
        });
        sptEventDig2EndEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setRig2DepthTo(Double.valueOf(s.toString()));
                    sptEventDig2EndEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptEventDig2EndEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    return;
                }
            }
        });
        sptEventDig3StartEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setRig3DepthFrom(Double.valueOf(s.toString()));
                    sptEventDig3StartEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptEventDig3StartEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    return;
                }
            }
        });
        sptEventDig3EndEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    rig.setRig3DepthTo(Double.valueOf(s.toString()));
                    sptEventDig3EndEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    sptEventDig3EndEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    return;
                }
            }
        });
        refreshRigInfoTable();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sptrig_detailed, menu);
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
            case R.id.button_rig_date_spt_detail:
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
            case R.id.button_start_time_spt_detail:
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
            case R.id.button_end_time_spt_detail:
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
            case R.id.button_confirm_spt_detailed:
                Intent intent = new Intent();
                intent.putExtra("rig", rig);
                this.setResult(RESULT_OK, intent);
                this.finish();
                break;
            case R.id.button_preview_spt:
                String baseDir = Environment.getExternalStorageDirectory().getPath()+"/ZuanTan/";
                File tempHtmls = new File(baseDir+"tempHtmls");
                if(!tempHtmls.exists()){
                    tempHtmls.mkdirs();
                }


                AssetManager assetManageer = getAssets();
                Hole tempHole = new Hole();
                tempHole.getRigList().add(rig);
                HtmlParser.parseSptRig(tempHtmls.getPath()+"/", tempHole, assetManageer);
                Intent intent2 = new Intent(this, HtmlViewActivity.class);

                Uri uri = Uri.fromFile(new File(tempHtmls, "sptRigEvent.html"));
                intent2.putExtra("table_path", uri.toString());
                startActivity(intent2);
                break;
            case R.id.button_argument_reference:
                Intent intent3 = new Intent(this, ConfigurationActivity.class);

                intent3.putExtra("request_type", "QUERY_SPT");
                startActivity(intent3);
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
        rigTimeSpanTextView.setText(Utility.calculateTimeSpan(rig.getStartTime(), rig.getEndTime()));

        sptEventTotalStartEditText.setText(String.format("%.2f", rig.getPenetrationFrom()));
        sptEventTotalEndEditText.setText(String.format("%.2f", rig.getPenetrationTo()));
        sptEventCountDepth1StartEditText.setText(String.format("%.2f", rig.getPenetration1DepthFrom()));
        sptEventCountDepth1EndEditText.setText(String.format("%.2f", rig.getPenetration1DepthTo()));
        sptHit1EditText.setText(String.valueOf(rig.getPenetration1Count()));
        sptEventCountDepth2StartEditText.setText(String.format("%.2f", rig.getPenetration2DepthFrom()));
        sptEventCountDepth2EndEditText.setText(String.format("%.2f", rig.getPenetration2DepthTo()));
        sptHit2EditText.setText(String.valueOf(rig.getPenetration2Count()));
        sptEventCountDepth3StartEditText.setText(String.format("%.2f", rig.getPenetration3DepthFrom()));
        sptEventCountDepth3EndEditText.setText(String.format("%.2f", rig.getPenetration3DepthTo()));
        sptHit3EditText.setText(String.valueOf(rig.getPenetration3Count()));

        sptEventDig1StartEditText.setText(String.format("%.2f", rig.getRig1DepthFrom()));
        sptEventDig1EndEditText.setText(String.format("%.2f", rig.getRig1DepthTo()));
        sptEventDig2StartEditText.setText(String.format("%.2f", rig.getRig2DepthFrom()));
        sptEventDig2EndEditText.setText(String.format("%.2f", rig.getRig2DepthTo()));
        sptEventDig3StartEditText.setText(String.format("%.2f", rig.getRig3DepthFrom()));
        sptEventDig3EndEditText.setText(String.format("%.2f", rig.getRig3DepthTo()));

        sptGroundColorEditText.setText(rig.getGroundColor());
        sptDestinyEditText.setText(rig.getGroundDensity());
        sptSaturationEditText.setText(rig.getGroundSaturation());
        sptOtherEditText.setText(rig.getSpecialNote());
        sptHitTotalCountEditText.setText(String.valueOf(rig.getCumulativeCount()));

        switch (rig.getGroundName()) {
            case "黏性土":
                rockNameSpinner.setSelection(0);
                break;
            case "砂类土":
                rockNameSpinner.setSelection(1);
                break;
            case "粉土":
                rockNameSpinner.setSelection(2);
                break;
        }

    }
}
