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

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import me.alfredis.collectionsystem.datastructure.DSTRig;
import me.alfredis.collectionsystem.datastructure.RigEvent;
import me.alfredis.collectionsystem.parser.HtmlParser;


public class DSTRigDetailedActivity extends ActionBarActivity implements View.OnClickListener {

    private String holeId;

    private DSTRig rig;

    private Button applyButton;
    private Button rigDateButton;
    private Button startTimeButton;
    private Button endTimeButton;
    private Button previewDSTButton;
    private Button argumentReferenceDSTButton;

    private EditText drillLengthDstRigEditText1;
    private EditText groundDepthDstRigEditText1;
    private EditText drillIntoLengthDstRigEditText1;
    private EditText drillCountDstRigEditText1;
    private EditText drillTypeLengthDSTRigEditText1;
    private EditText drillLengthDstRigEditText2;
    private EditText groundDepthDstRigEditText2;
    private EditText drillIntoLengthDstRigEditText2;
    private EditText drillCountDstRigEditText2;
    private EditText drillTypeLengthDSTRigEditText2;
    private EditText drillLengthDstRigEditText3;
    private EditText groundDepthDstRigEditText3;
    private EditText drillIntoLengthDstRigEditText3;
    private EditText drillCountDstRigEditText3;
    private EditText drillTypeLengthDSTRigEditText3;
    private EditText drillLengthDstRigEditText4;
    private EditText groundDepthDstRigEditText4;
    private EditText drillIntoLengthDstRigEditText4;
    private EditText drillCountDstRigEditText4;
    private EditText drillTypeLengthDSTRigEditText4;
    private EditText drillLengthDstRigEditText5;
    private EditText groundDepthDstRigEditText5;
    private EditText drillIntoLengthDstRigEditText5;
    private EditText drillCountDstRigEditText5;
    private EditText drillTypeLengthDSTRigEditText5;
    private EditText drillLengthDstRigEditText6;
    private EditText groundDepthDstRigEditText6;
    private EditText drillIntoLengthDstRigEditText6;
    private EditText drillCountDstRigEditText6;
    private EditText drillTypeLengthDSTRigEditText6;

    private Spinner rockNameDstSpinner;
    private ArrayAdapter<String> rockNameDSTAdapter;
    private static final String[] ROCK_NAME_DST_OPTIONS = {"碎石土63.5", "碎石土120", "砂土 - 砾砂", "砂土 - 粗砂", "砂土 - 中砂"};

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

        rockNameDstSpinner = (Spinner) findViewById(R.id.spinner_rock_name_dst_rig);

        rockNameDSTAdapter  = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ROCK_NAME_DST_OPTIONS);
        rockNameDstSpinner.setAdapter(rockNameDSTAdapter);
        rockNameDstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        applyButton.setOnClickListener(this);
        rigDateButton.setOnClickListener(this);
        startTimeButton.setOnClickListener(this);
        endTimeButton.setOnClickListener(this);
        previewDSTButton.setOnClickListener(this);
        argumentReferenceDSTButton.setOnClickListener(this);

        drillLengthDstRigEditText1 = (EditText) findViewById(R.id.edit_text_drill_length_dst_rig_1);
        groundDepthDstRigEditText1 = (EditText) findViewById(R.id.edit_text_in_ground_depth_dst_rig_1);
        drillIntoLengthDstRigEditText1 = (EditText) findViewById(R.id.edit_text_drill_into_length_dst_rig_1);
        drillCountDstRigEditText1 = (EditText) findViewById(R.id.edit_text_drill_count_dst_rig_1);
        drillTypeLengthDSTRigEditText1 = (EditText) findViewById(R.id.edit_text_drill_type_length_dst_rig_1);
        drillLengthDstRigEditText2 = (EditText) findViewById(R.id.edit_text_drill_length_dst_rig_2);
        groundDepthDstRigEditText2 = (EditText) findViewById(R.id.edit_text_in_ground_depth_dst_rig_2);
        drillIntoLengthDstRigEditText2 = (EditText) findViewById(R.id.edit_text_drill_into_length_dst_rig_2);
        drillCountDstRigEditText2 = (EditText) findViewById(R.id.edit_text_drill_count_dst_rig_2);
        drillTypeLengthDSTRigEditText2 = (EditText) findViewById(R.id.edit_text_drill_type_length_dst_rig_2);
        drillLengthDstRigEditText3 = (EditText) findViewById(R.id.edit_text_drill_length_dst_rig_3);
        groundDepthDstRigEditText3 = (EditText) findViewById(R.id.edit_text_in_ground_depth_dst_rig_3);
        drillIntoLengthDstRigEditText3 = (EditText) findViewById(R.id.edit_text_drill_into_length_dst_rig_3);
        drillCountDstRigEditText3 = (EditText) findViewById(R.id.edit_text_drill_count_dst_rig_3);
        drillTypeLengthDSTRigEditText3 = (EditText) findViewById(R.id.edit_text_drill_type_length_dst_rig_3);
        drillLengthDstRigEditText4 = (EditText) findViewById(R.id.edit_text_drill_length_dst_rig_4);
        groundDepthDstRigEditText4 = (EditText) findViewById(R.id.edit_text_in_ground_depth_dst_rig_4);
        drillIntoLengthDstRigEditText4 = (EditText) findViewById(R.id.edit_text_drill_into_length_dst_rig_4);
        drillCountDstRigEditText4 = (EditText) findViewById(R.id.edit_text_drill_count_dst_rig_4);
        drillTypeLengthDSTRigEditText4 = (EditText) findViewById(R.id.edit_text_drill_type_length_dst_rig_4);
        drillLengthDstRigEditText5 = (EditText) findViewById(R.id.edit_text_drill_length_dst_rig_5);
        groundDepthDstRigEditText5 = (EditText) findViewById(R.id.edit_text_in_ground_depth_dst_rig_5);
        drillIntoLengthDstRigEditText5 = (EditText) findViewById(R.id.edit_text_drill_into_length_dst_rig_5);
        drillCountDstRigEditText5 = (EditText) findViewById(R.id.edit_text_drill_count_dst_rig_5);
        drillTypeLengthDSTRigEditText5 = (EditText) findViewById(R.id.edit_text_drill_type_length_dst_rig_5);
        drillLengthDstRigEditText6 = (EditText) findViewById(R.id.edit_text_drill_length_dst_rig_6);
        groundDepthDstRigEditText6 = (EditText) findViewById(R.id.edit_text_in_ground_depth_dst_rig_6);
        drillIntoLengthDstRigEditText6 = (EditText) findViewById(R.id.edit_text_drill_into_length_dst_rig_6);
        drillCountDstRigEditText6 = (EditText) findViewById(R.id.edit_text_drill_count_dst_rig_6);
        drillTypeLengthDSTRigEditText6 = (EditText) findViewById(R.id.edit_text_drill_type_length_dst_rig_6);

        drillCountDstRigEditText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    drillTypeLengthDSTRigEditText1.setText(ConfigurationManager.getDSTDestiny(rockNameDstSpinner.getSelectedItemPosition(), Integer.parseInt(s.toString())));
                } catch (Exception e) {

                }
            }
        });

        drillCountDstRigEditText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    drillTypeLengthDSTRigEditText2.setText(ConfigurationManager.getDSTDestiny(rockNameDstSpinner.getSelectedItemPosition(), Integer.parseInt(s.toString())));
                } catch (Exception e) {

                }
            }
        });

        drillCountDstRigEditText3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    drillTypeLengthDSTRigEditText3.setText(ConfigurationManager.getDSTDestiny(rockNameDstSpinner.getSelectedItemPosition(), Integer.parseInt(s.toString())));
                } catch (Exception e) {

                }
            }
        });

        drillCountDstRigEditText4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    drillTypeLengthDSTRigEditText4.setText(ConfigurationManager.getDSTDestiny(rockNameDstSpinner.getSelectedItemPosition(), Integer.parseInt(s.toString())));
                } catch (Exception e) {

                }
            }
        });

        drillCountDstRigEditText5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    drillTypeLengthDSTRigEditText5.setText(ConfigurationManager.getDSTDestiny(rockNameDstSpinner.getSelectedItemPosition(), Integer.parseInt(s.toString())));
                } catch (Exception e) {

                }
            }
        });

        drillCountDstRigEditText6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    drillTypeLengthDSTRigEditText6.setText(ConfigurationManager.getDSTDestiny(rockNameDstSpinner.getSelectedItemPosition(), Integer.parseInt(s.toString())));
                } catch (Exception e) {

                }
            }
        });

        rockNameDstSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (drillCountDstRigEditText1.getText().length() > 0) {
                        drillTypeLengthDSTRigEditText1.setText(ConfigurationManager.getDSTDestiny(rockNameDstSpinner.getSelectedItemPosition(), Integer.parseInt(drillCountDstRigEditText1.getText().toString())));
                    }

                    if (drillCountDstRigEditText2.getText().length() > 0) {
                        drillTypeLengthDSTRigEditText2.setText(ConfigurationManager.getDSTDestiny(rockNameDstSpinner.getSelectedItemPosition(), Integer.parseInt(drillCountDstRigEditText2.getText().toString())));
                    }

                    if (drillCountDstRigEditText3.getText().length() > 0) {
                        drillTypeLengthDSTRigEditText3.setText(ConfigurationManager.getDSTDestiny(rockNameDstSpinner.getSelectedItemPosition(), Integer.parseInt(drillCountDstRigEditText3.getText().toString())));
                    }

                    if (drillCountDstRigEditText4.getText().length() > 0) {
                        drillTypeLengthDSTRigEditText4.setText(ConfigurationManager.getDSTDestiny(rockNameDstSpinner.getSelectedItemPosition(), Integer.parseInt(drillCountDstRigEditText4.getText().toString())));
                    }

                    if (drillCountDstRigEditText5.getText().length() > 0) {
                        drillTypeLengthDSTRigEditText5.setText(ConfigurationManager.getDSTDestiny(rockNameDstSpinner.getSelectedItemPosition(), Integer.parseInt(drillCountDstRigEditText5.getText().toString())));
                    }

                    if (drillCountDstRigEditText6.getText().length() > 0) {
                        drillTypeLengthDSTRigEditText6.setText(ConfigurationManager.getDSTDestiny(rockNameDstSpinner.getSelectedItemPosition(), Integer.parseInt(drillCountDstRigEditText6.getText().toString())));
                    }
                } catch (Exception e) {

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (rig.getDynamicSoundingEvents().size() == 0) {
            drillLengthDstRigEditText1.setText("74");
            drillLengthDstRigEditText2.setText("74");
            drillLengthDstRigEditText3.setText("74");
            drillLengthDstRigEditText4.setText("74");
            drillLengthDstRigEditText5.setText("74");
            drillLengthDstRigEditText6.setText("74");
            drillIntoLengthDstRigEditText1.setText("10");
            drillIntoLengthDstRigEditText2.setText("10");
            drillIntoLengthDstRigEditText3.setText("10");
            drillIntoLengthDstRigEditText4.setText("10");
            drillIntoLengthDstRigEditText5.setText("10");
            drillIntoLengthDstRigEditText6.setText("10");
        }

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

                rig.getDynamicSoundingEvents().clear();

                try {
                    if (
                            drillLengthDstRigEditText1.getText().length() > 0 &&
                                    groundDepthDstRigEditText1.getText().length() > 0 &&
                                    drillIntoLengthDstRigEditText1.getText().length() > 0 &&
                                    drillCountDstRigEditText1.getText().length() > 0 &&
                                    drillTypeLengthDSTRigEditText1.getText().length() > 0
                            ) {
                        rig.getDynamicSoundingEvents().add(new DSTRig.DynamicSoundingEvent(
                                Double.valueOf(drillLengthDstRigEditText1.getText().toString()),
                                Double.valueOf(groundDepthDstRigEditText1.getText().toString()),
                                Double.valueOf(drillIntoLengthDstRigEditText1.getText().toString()),
                                Integer.valueOf(drillCountDstRigEditText1.getText().toString()),
                                drillTypeLengthDSTRigEditText1.getText().toString()
                        ));
                    }
                } catch (Exception e) {

                }


                try {
                    if (
                            drillLengthDstRigEditText2.getText().length() > 0 &&
                                    groundDepthDstRigEditText2.getText().length() > 0 &&
                                    drillIntoLengthDstRigEditText2.getText().length() > 0 &&
                                    drillCountDstRigEditText2.getText().length() > 0 &&
                                    drillTypeLengthDSTRigEditText2.getText().length() > 0
                            ) {
                        rig.getDynamicSoundingEvents().add(new DSTRig.DynamicSoundingEvent(
                                Double.valueOf(drillLengthDstRigEditText2.getText().toString()),
                                Double.valueOf(groundDepthDstRigEditText2.getText().toString()),
                                Double.valueOf(drillIntoLengthDstRigEditText2.getText().toString()),
                                Integer.valueOf(drillCountDstRigEditText2.getText().toString()),
                                drillTypeLengthDSTRigEditText2.getText().toString()
                        ));
                    }
                } catch (Exception e) {

                    }


                try {
                    if (
                            drillLengthDstRigEditText3.getText().length() > 0 &&
                                    groundDepthDstRigEditText3.getText().length() > 0 &&
                                    drillIntoLengthDstRigEditText3.getText().length() > 0 &&
                                    drillCountDstRigEditText3.getText().length() > 0 &&
                                    drillTypeLengthDSTRigEditText3.getText().length() > 0
                            ) {
                        rig.getDynamicSoundingEvents().add(new DSTRig.DynamicSoundingEvent(
                                Double.valueOf(drillLengthDstRigEditText3.getText().toString()),
                                Double.valueOf(groundDepthDstRigEditText3.getText().toString()),
                                Double.valueOf(drillIntoLengthDstRigEditText3.getText().toString()),
                                Integer.valueOf(drillCountDstRigEditText3.getText().toString()),
                                drillTypeLengthDSTRigEditText3.getText().toString()
                        ));
                    }
                } catch (Exception e) {

                }

                try {
                    if (
                            drillLengthDstRigEditText4.getText().length() > 0 &&
                                    groundDepthDstRigEditText4.getText().length() > 0 &&
                                    drillIntoLengthDstRigEditText4.getText().length() > 0 &&
                                    drillCountDstRigEditText4.getText().length() > 0 &&
                                    drillTypeLengthDSTRigEditText4.getText().length() > 0
                            ) {
                        rig.getDynamicSoundingEvents().add(new DSTRig.DynamicSoundingEvent(
                                Double.valueOf(drillLengthDstRigEditText4.getText().toString()),
                                Double.valueOf(groundDepthDstRigEditText4.getText().toString()),
                                Double.valueOf(drillIntoLengthDstRigEditText4.getText().toString()),
                                Integer.valueOf(drillCountDstRigEditText4.getText().toString()),
                                drillTypeLengthDSTRigEditText4.getText().toString()
                        ));
                    }
                } catch (Exception e) {

                }

                try {
                    if (
                            drillLengthDstRigEditText5.getText().length() > 0 &&
                                    groundDepthDstRigEditText5.getText().length() > 0 &&
                                    drillIntoLengthDstRigEditText5.getText().length() > 0 &&
                                    drillCountDstRigEditText5.getText().length() > 0 &&
                                    drillTypeLengthDSTRigEditText5.getText().length() > 0
                            ) {
                        rig.getDynamicSoundingEvents().add(new DSTRig.DynamicSoundingEvent(
                                Double.valueOf(drillLengthDstRigEditText5.getText().toString()),
                                Double.valueOf(groundDepthDstRigEditText5.getText().toString()),
                                Double.valueOf(drillIntoLengthDstRigEditText5.getText().toString()),
                                Integer.valueOf(drillCountDstRigEditText5.getText().toString()),
                                drillTypeLengthDSTRigEditText5.getText().toString()
                        ));
                    }
                } catch (Exception e) {

                }
                try {
                    if (
                            drillLengthDstRigEditText6.getText().length() > 0 &&
                                    groundDepthDstRigEditText6.getText().length() > 0 &&
                                    drillIntoLengthDstRigEditText6.getText().length() > 0 &&
                                    drillCountDstRigEditText6.getText().length() > 0 &&
                                    drillTypeLengthDSTRigEditText6.getText().length() > 0
                            ) {
                        rig.getDynamicSoundingEvents().add(new DSTRig.DynamicSoundingEvent(
                                Double.valueOf(drillLengthDstRigEditText6.getText().toString()),
                                Double.valueOf(groundDepthDstRigEditText6.getText().toString()),
                                Double.valueOf(drillIntoLengthDstRigEditText6.getText().toString()),
                                Integer.valueOf(drillCountDstRigEditText6.getText().toString()),
                                drillTypeLengthDSTRigEditText6.getText().toString()
                        ));
                    }
                } catch (Exception e) {

                }


                intent.putExtra("rig", rig);
                this.setResult(RESULT_OK, intent);
                this.finish();
                break;
            case R.id.button_preview_dst:
                String baseDir = Environment.getExternalStorageDirectory().getPath()+"/ZuanTan/";
                File tempHtmls = new File(baseDir+"tempHtmls");
                if(!tempHtmls.exists()){
                    tempHtmls.mkdirs();
                }

                AssetManager assetManageer = getAssets();
                ArrayList<RigEvent> rigEvents = new ArrayList<>();
                rigEvents.add(rig);
                HtmlParser.parseDstRig(tempHtmls.getPath() + "/", rigEvents, assetManageer);
                Intent intent2 = new Intent(this, HtmlViewActivity.class);

                Uri uri = Uri.fromFile(new File(tempHtmls, "dstRigEvent.html"));
                intent2.putExtra("table_path", uri.toString());
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

        int index = 0;
        for (DSTRig.DynamicSoundingEvent dynamicSoundingEvent : rig.getDynamicSoundingEvents()) {
            if (index == 0) {
                drillLengthDstRigEditText1.setText(String.valueOf(dynamicSoundingEvent.getTotalLength()));
                groundDepthDstRigEditText1.setText(String.valueOf(dynamicSoundingEvent.getCompactness()));
                drillIntoLengthDstRigEditText1.setText(String.valueOf(dynamicSoundingEvent.getPenetration()));
                drillCountDstRigEditText1.setText(String.valueOf(dynamicSoundingEvent.getHammeringCount()));
                drillTypeLengthDSTRigEditText1.setText(String.valueOf(dynamicSoundingEvent.getCompactness()));
            } else if (index == 1) {
                drillLengthDstRigEditText2.setText(String.valueOf(dynamicSoundingEvent.getTotalLength()));
                groundDepthDstRigEditText2.setText(String.valueOf(dynamicSoundingEvent.getCompactness()));
                drillIntoLengthDstRigEditText2.setText(String.valueOf(dynamicSoundingEvent.getPenetration()));
                drillCountDstRigEditText2.setText(String.valueOf(dynamicSoundingEvent.getHammeringCount()));
                drillTypeLengthDSTRigEditText2.setText(String.valueOf(dynamicSoundingEvent.getCompactness()));
            } else if (index == 2) {
                drillLengthDstRigEditText3.setText(String.valueOf(dynamicSoundingEvent.getTotalLength()));
                groundDepthDstRigEditText3.setText(String.valueOf(dynamicSoundingEvent.getCompactness()));
                drillIntoLengthDstRigEditText3.setText(String.valueOf(dynamicSoundingEvent.getPenetration()));
                drillCountDstRigEditText3.setText(String.valueOf(dynamicSoundingEvent.getHammeringCount()));
                drillTypeLengthDSTRigEditText3.setText(String.valueOf(dynamicSoundingEvent.getCompactness()));
            } else if (index == 3) {
                drillLengthDstRigEditText4.setText(String.valueOf(dynamicSoundingEvent.getTotalLength()));
                groundDepthDstRigEditText4.setText(String.valueOf(dynamicSoundingEvent.getCompactness()));
                drillIntoLengthDstRigEditText4.setText(String.valueOf(dynamicSoundingEvent.getPenetration()));
                drillCountDstRigEditText4.setText(String.valueOf(dynamicSoundingEvent.getHammeringCount()));
                drillTypeLengthDSTRigEditText4.setText(String.valueOf(dynamicSoundingEvent.getCompactness()));
            } else if (index == 4) {
                drillLengthDstRigEditText5.setText(String.valueOf(dynamicSoundingEvent.getTotalLength()));
                groundDepthDstRigEditText5.setText(String.valueOf(dynamicSoundingEvent.getCompactness()));
                drillIntoLengthDstRigEditText5.setText(String.valueOf(dynamicSoundingEvent.getPenetration()));
                drillCountDstRigEditText5.setText(String.valueOf(dynamicSoundingEvent.getHammeringCount()));
                drillTypeLengthDSTRigEditText5.setText(String.valueOf(dynamicSoundingEvent.getCompactness()));
            } else if (index == 5) {
                drillLengthDstRigEditText6.setText(String.valueOf(dynamicSoundingEvent.getTotalLength()));
                groundDepthDstRigEditText6.setText(String.valueOf(dynamicSoundingEvent.getCompactness()));
                drillIntoLengthDstRigEditText6.setText(String.valueOf(dynamicSoundingEvent.getPenetration()));
                drillCountDstRigEditText6.setText(String.valueOf(dynamicSoundingEvent.getHammeringCount()));
                drillTypeLengthDSTRigEditText6.setText(String.valueOf(dynamicSoundingEvent.getCompactness()));
            }
            index++;
        }
    }
}
