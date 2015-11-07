package me.alfredis.collectionsystem;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

import me.alfredis.collectionsystem.datastructure.Hole;
import me.alfredis.collectionsystem.datastructure.SamplingRig;
import me.alfredis.collectionsystem.parser.HtmlParser;

public class SamplingDetailedActivity extends ActionBarActivity implements View.OnClickListener {
    private String holeId;

    private SamplingRig rig;

    private Button applyButton;
    private Button rigDateButton;
    private Button startTimeButton;
    private Button endTimeButton;
    private TextView rigTimeSpanTextView;

    private EditText classPeopleCountEditText;
    private EditText sampleStatusEditText;
    private EditText samplerTypeEditText;
    private EditText sampleIdEditText;
    private EditText sampleDiameterEditText;
    private EditText sampleStartDepthEditText;
    private EditText sampleEndDepthEditText;
    private EditText sampleCountEditText;

    private Button previewSampleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampling_detailed);

        holeId = getIntent().getStringExtra("holeId");

        rig = (SamplingRig) getIntent().getSerializableExtra("rig");

        applyButton = (Button) findViewById(R.id.button_confirm_sampling_detailed);
        rigDateButton = (Button) findViewById(R.id.button_rig_date_sampling_detail);
        startTimeButton = (Button) findViewById(R.id.button_start_time_sampling_detail);
        endTimeButton = (Button) findViewById(R.id.button_end_time_sampling_detail);
        rigTimeSpanTextView = (TextView) findViewById(R.id.textview_rig_time_duration_sampling_detail);

        classPeopleCountEditText = (EditText) findViewById(R.id.edit_text_class_people_count_sampling_detail);
        sampleStatusEditText = (EditText) findViewById(R.id.edit_text_sample_status);
        samplerTypeEditText = (EditText) findViewById(R.id.edit_text_sampler_type);
        sampleIdEditText = (EditText) findViewById(R.id.edit_text_sampling_id);
        sampleDiameterEditText = (EditText) findViewById(R.id.edit_text_sampling_diameter);
        sampleStartDepthEditText = (EditText) findViewById(R.id.edit_text_sample_start_depth);
        sampleEndDepthEditText = (EditText) findViewById(R.id.edit_text_sample_end_depth);
        sampleCountEditText = (EditText) findViewById(R.id.edit_text_sample_amount);

        previewSampleButton = (Button) findViewById(R.id.button_preview_sampling_detailed);

        applyButton.setOnClickListener(this);
        rigDateButton.setOnClickListener(this);
        startTimeButton.setOnClickListener(this);
        endTimeButton.setOnClickListener(this);
        previewSampleButton.setOnClickListener(this);

        classPeopleCountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rig.setClassPeopleCount(editable.toString());
            }
        });

        sampleStatusEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rig.setSampleStatus(editable.toString());
            }
        });

        samplerTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rig.setSamplerType(editable.toString());
            }
        });

        sampleIdEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rig.setSampleId(editable.toString());
            }
        });

        sampleDiameterEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rig.setSampleDiameter(Double.valueOf(editable.toString()));
            }
        });

        sampleStartDepthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rig.setStartDepth(Double.valueOf(editable.toString()));
            }
        });

        sampleEndDepthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rig.setEndDepth(Double.valueOf(editable.toString()));
            }
        });

        sampleCountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                rig.setSampleCount(Integer.valueOf(editable.toString()));
            }
        });

        refreshSamplingDetailedTable();
    }

    @Override
    public void onClick(View view) {
        Calendar canlendar = Calendar.getInstance();

        switch (view.getId()) {
            case R.id.button_start_time_sampling_detail:
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
            case R.id.button_end_time_sampling_detail:
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
            case R.id.button_confirm_sampling_detailed:
                Intent intent = new Intent();
                intent.putExtra("rig", rig);
                this.setResult(RESULT_OK, intent);
                this.finish();
                break;
            case R.id.button_rig_date_sampling_detail:
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
            case R.id.button_preview_sampling_detailed:
                //TODO: PREIEW sampling
                /*
                String baseDir = Environment.getExternalStorageDirectory().getPath()+"/ZuanTan/";
                File tempHtmls = new File(baseDir+"tempHtmls");
                if(!tempHtmls.exists()){
                    tempHtmls.mkdirs();
                }


                AssetManager assetManageer = getAssets();
                Hole tempHole = new Hole();
                tempHole.getRigList().add(rig);
                HtmlParser.parseSptRig(tempHtmls.getPath() + "/", tempHole, assetManageer);
                Intent intent2 = new Intent(this, HtmlViewActivity.class);

                Uri uri = Uri.fromFile(new File(tempHtmls, "sptRigEvent.html"));
                intent2.putExtra("table_path", uri.toString());
                startActivity(intent2);
                */
                break;
        }
    }

    private void refreshSamplingDetailedTable() {
        classPeopleCountEditText.setText(rig.getClassPeopleCount());
        startTimeButton.setText(Utility.formatTimeString(rig.getStartTime()));
        endTimeButton.setText(Utility.formatTimeString(rig.getEndTime()));
        rigDateButton.setText(Utility.formatCalendarDateString(rig.getDate()));
        rigTimeSpanTextView.setText(Utility.calculateTimeSpan(rig.getStartTime(), rig.getEndTime()));
        sampleStatusEditText.setText(rig.getSampleStatus());
        samplerTypeEditText.setText(rig.getSamplerType());
        sampleIdEditText.setText(rig.getSampleId());
        sampleDiameterEditText.setText(String.valueOf(rig.getSampleDiameter()));
        sampleStartDepthEditText.setText(String.valueOf(rig.getStartDepth()));
        sampleEndDepthEditText.setText(String.valueOf(rig.getEndDepth()));
        sampleCountEditText.setText(String.valueOf(rig.getSampleCount()));
    }
}
