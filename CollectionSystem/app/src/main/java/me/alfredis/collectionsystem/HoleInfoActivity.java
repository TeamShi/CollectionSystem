package me.alfredis.collectionsystem;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.Html;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
    private Button takePhotoButton;

    private EditText mileageEditText;
    private EditText offsetEditText;
    private EditText projectNameEditText;
    private EditText rigTypeEditText;
    private EditText engineTypeEditText;
    private EditText pumpTypeEditText;
    private EditText elevationEditText;
    private EditText designedDepthEditText;
    private EditText initialLevelDepthEditText;
    private EditText stableLevelDepthEditText;
    private EditText longitudeDistanceEditText;
    private EditText latitudeDistanceEditText;
    private EditText explorationUnitEditText;
    private EditText machineNumberEditText;
    private EditText acturalDepthEditText;
    private EditText noteEditText;
    private EditText recorderEditText;
    private EditText reviewerEditText;
    private EditText squadEditText;
    private EditText captainEditText;

    private Spinner holeIdPart1Spinner;
    private TextView holeIdPart2TextView;
    private Spinner holeIdPart3Spinner;
    private EditText holeIdPart4EditText;

    private Spinner projectStageSpinner;
    private Spinner articleSpinner;

    private ImageView photoView;
    private TableRow photoTableRow;

    private ArrayAdapter<String> projectStageSpinnerAdapter;
    private ArrayAdapter<String> holeIdPart1SpinnerAdapter;
    private ArrayAdapter<String> holeIdPart3SpinnerAdapter;
    private ArrayAdapter<String> articleSpinnerAdapter;

    private static final String TAG = "CollectionSystem";

    private static final String[] PROJECT_ID_PART1_SPINNER_OPTIONS = {"JC", "JZ"};
    private static final String[] PROJECT_STAGE_SPINNER_OPTIONS = {"I", "II", "III", "IV"};
    private static final String[] PROJECT_ID_PART3_SPINNER_OPTIONS = {"1", "2", "3", "4"};
    private static final String[] ARTICLE_SPINNER_OPTIONS = {"K", "DK", "AK", "ACK", "CDK"};

    private static final int TAKE_PHOTO = 0;
    private static final int CROP_PHOTO = 1;

    private static Uri imageUri;

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
        takePhotoButton = (Button) findViewById(R.id.button_take_photo);

        mileageEditText = (EditText) findViewById(R.id.hole_mileage);
        offsetEditText = (EditText) findViewById(R.id.hole_offset);
        projectNameEditText = (EditText) findViewById(R.id.edittext_hole_project_name);
        rigTypeEditText = (EditText) findViewById(R.id.hole_rig_type);
        engineTypeEditText = (EditText) findViewById(R.id.hole_engine_type);
        pumpTypeEditText = (EditText) findViewById(R.id.hole_engine_type);
        elevationEditText = (EditText) findViewById(R.id.hole_elevation);
        designedDepthEditText = (EditText) findViewById(R.id.hole_designed_depth);
        initialLevelDepthEditText = (EditText) findViewById(R.id.hole_initial_level);
        stableLevelDepthEditText = (EditText) findViewById(R.id.hole_stable_level);
        longitudeDistanceEditText = (EditText) findViewById(R.id.hole_longitude_distance);
        latitudeDistanceEditText = (EditText) findViewById(R.id.hole_latitude_distance);
        explorationUnitEditText = (EditText) findViewById(R.id.hole_exploration_unit);
        machineNumberEditText = (EditText) findViewById(R.id.hole_machine_number);
        acturalDepthEditText = (EditText) findViewById(R.id.hole_actural_depth);
        noteEditText = (EditText) findViewById(R.id.hole_note);
        recorderEditText = (EditText) findViewById(R.id.hole_recorder_name);
        reviewerEditText = (EditText) findViewById(R.id.hole_reviewer_name);
        squadEditText = (EditText) findViewById(R.id.hole_squad_name);
        captainEditText = (EditText) findViewById(R.id.hole_captain_name);

        holeIdPart1Spinner = (Spinner) findViewById(R.id.spinner_hole_id_part1);
        holeIdPart2TextView = (TextView) findViewById(R.id.textview_hole_id_part2);
        holeIdPart3Spinner = (Spinner) findViewById(R.id.spinner_hole_id_part3);
        holeIdPart4EditText = (EditText) findViewById(R.id.edittext_hole_id_part4);

        photoView = (ImageView) findViewById(R.id.image_view_hole_id);
        photoTableRow = (TableRow) findViewById(R.id.table_row_photo);

        projectStageSpinner = (Spinner) findViewById(R.id.spinner_hole_project_stage);
        articleSpinner = (Spinner) findViewById(R.id.spinner_hole_article);

        addButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        startDateButton.setOnClickListener(this);
        endDateButton.setOnClickListener(this);
        initialLevelButton.setOnClickListener(this);
        stableLevelButton.setOnClickListener(this);
        recordDateButton.setOnClickListener(this);
        reviewDateButton.setOnClickListener(this);
        takePhotoButton.setOnClickListener(this);

        projectStageSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, PROJECT_STAGE_SPINNER_OPTIONS);
        projectStageSpinner.setAdapter(projectStageSpinnerAdapter);
        projectStageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hole.setProjectStage(Enum.valueOf(Hole.ProjectStageType.class, parent.getItemAtPosition(position).toString()));
                holeIdPart2TextView.setText(Html.fromHtml(formatHoleIdPart2(hole.getHoleIdPart2())));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holeIdPart1SpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PROJECT_ID_PART1_SPINNER_OPTIONS);
        holeIdPart1Spinner.setAdapter(holeIdPart1SpinnerAdapter);
        holeIdPart1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hole.setHoleIdPart1(Enum.valueOf(Hole.HoleIdPart1Type.class, parent.getItemAtPosition(position).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holeIdPart3SpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PROJECT_ID_PART3_SPINNER_OPTIONS);
        holeIdPart3Spinner.setAdapter(holeIdPart3SpinnerAdapter);
        holeIdPart3Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hole.setHoleIdPart3(String.valueOf(parent.getItemAtPosition(position)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        articleSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ARTICLE_SPINNER_OPTIONS);
        articleSpinner.setAdapter(articleSpinnerAdapter);
        articleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hole.setArticle(Enum.valueOf(Hole.ArticleType.class, parent.getItemAtPosition(position).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        projectNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hole.setProjectName(s.toString());
            }
        });
        rigTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hole.setRigType(s.toString());
            }
        });
        engineTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hole.setEngineType(s.toString());
            }
        });
        pumpTypeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hole.setPumpType(s.toString());
            }
        });
        elevationEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    hole.setHoleElevation(Double.valueOf(s.toString()));
                    elevationEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    elevationEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        designedDepthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    hole.setDesignedDepth(Double.valueOf(s.toString()));
                    designedDepthEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    designedDepthEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        initialLevelDepthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    hole.setInitialLevel(Double.valueOf(s.toString()));
                    initialLevelDepthEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    initialLevelDepthEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        stableLevelDepthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    hole.setStableLevel(Double.valueOf(s.toString()));
                    stableLevelDepthEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    stableLevelDepthEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        longitudeDistanceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    hole.setLongitudeDistance(Double.valueOf(s.toString()));
                    longitudeDistanceEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    longitudeDistanceEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        latitudeDistanceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    hole.setLatitudeDistance(Double.valueOf(s.toString()));
                    latitudeDistanceEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    latitudeDistanceEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        explorationUnitEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hole.setExplorationUnit(s.toString());
            }
        });
        machineNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hole.setMachineNumber(s.toString());
            }
        });
        acturalDepthEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    hole.setActuralDepth(Double.parseDouble(s.toString()));
                    acturalDepthEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    acturalDepthEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        noteEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hole.setNote(s.toString());
            }
        });
        recorderEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hole.setRecorderName(s.toString());
            }
        });
        reviewerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hole.setReviewerName(s.toString());
            }
        });
        squadEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hole.setSquadName(s.toString());
            }
        });
        captainEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hole.setCaptainName(s.toString());
            }
        });
        mileageEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    hole.setMileage(Double.parseDouble(s.toString()));
                    mileageEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    mileageEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        offsetEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    hole.setOffset(Double.parseDouble(s.toString()));
                    offsetEditText.setBackgroundColor(getResources().getColor(android.R.color.white));
                } catch (Exception e) {
                    offsetEditText.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                }
            }
        });
        holeIdPart4EditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                hole.setHoleIdPart4(s.toString());
            }
        });

        requestCode = getIntent().getStringExtra("requestCode");

        switch (requestCode) {
            case "ADD_HOLE":
                hole = new Hole();


                refreshHoleInfoTable();
                break;
            case "QUERY_HOLE":
                hole = DataManager.holes.get(getIntent().getIntExtra("holeIndex", -1));
                File photo = new File(Environment.getExternalStorageDirectory().getPath() + "/tempPhotoes/" + hole.getHoleId() + ".jpg");
                if (photo.exists()) {
                    try {
                        imageUri = Uri.fromFile(photo);
                        Bitmap bitmap = BitmapFactory.decodeStream(
                                getContentResolver().openInputStream(imageUri));
                        photoTableRow.setVisibility(View.VISIBLE);
                        photoView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

                addButton.setText("保存");

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
        final Intent intent;

        Calendar calendar = Calendar.getInstance();

        File imageTempDir = new File(Environment.getExternalStorageDirectory().getPath()+"/tempPhotoes");
        if(!imageTempDir.exists()) {
            imageTempDir.mkdirs();
        }

        switch (v.getId()) {
            case R.id.button_confirm_add_hole:
                Log.d(TAG, "Add button clicked.");

                if (requestCode.equals("ADD_HOLE")) {
                    if (DataManager.isHoleIdExist(hole.getHoleId())) {
                        Toast.makeText(getApplicationContext(), "钻探编号已存在", Toast.LENGTH_SHORT).show();
                    } else {
                        DataManager.holes.add(hole);
                        this.setResult(RESULT_OK);
                        this.finish();
                    }
                } else if (requestCode.equals("QUERY_HOLE")) {
                    DataManager.holes.set(getIntent().getIntExtra("holeIndex", -1), hole);
                    this.setResult(RESULT_OK);
                    this.finish();
                }
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
                        if (hole.getStartDate().compareTo(hole.getEndDate()) > 0) {
                            hole.setEndDate(hole.getStartDate());
                            endDateButton.setText(Utility.formatCalendarDateString(hole.getEndDate()));
                        }
                        startDateButton.setText(Utility.formatCalendarDateString(hole.getStartDate()));
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
                        if (hole.getStartDate().compareTo(hole.getEndDate()) > 0) {
                            hole.setStartDate(hole.getEndDate());
                            startDateButton.setText(Utility.formatCalendarDateString(hole.getStartDate()));
                        }
                        endDateButton.setText(Utility.formatCalendarDateString(hole.getEndDate()));
                    }
                }, endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.button_initial_level_date:
                final Calendar initialLevelDate = hole.getInitialLevelMeasuringDate();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(year, monthOfYear, dayOfMonth);
                        hole.setInitialLevelMeasuringDate(temp);
                        if (hole.getInitialLevelMeasuringDate().compareTo(hole.getStableLevelMeasuringDate()) > 0) {
                            hole.setStableLevelMeasuringDate(hole.getInitialLevelMeasuringDate());
                            stableLevelButton.setText(Utility.formatCalendarDateString(hole.getStableLevelMeasuringDate()));
                        }
                        initialLevelButton.setText(Utility.formatCalendarDateString(hole.getInitialLevelMeasuringDate()));
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
                        if (hole.getInitialLevelMeasuringDate().compareTo(hole.getStableLevelMeasuringDate()) > 0) {
                            hole.setInitialLevelMeasuringDate(hole.getStableLevelMeasuringDate());
                            initialLevelButton.setText(Utility.formatCalendarDateString(hole.getInitialLevelMeasuringDate()));
                        }
                        stableLevelButton.setText(Utility.formatCalendarDateString(hole.getStableLevelMeasuringDate()));
                    }
                }, stableLevelDate.get(Calendar.YEAR), stableLevelDate.get(Calendar.MONTH), stableLevelDate.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.button_record_date:
                final Calendar recordDate = hole.getRecordDate();
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        GregorianCalendar temp = new GregorianCalendar();
                        temp.set(year, monthOfYear, dayOfMonth);
                        hole.setRecordDate(temp);
                        if (hole.getRecordDate().compareTo(hole.getReviewDate()) > 0) {
                            hole.setReviewDate(hole.getRecordDate());
                            reviewDateButton.setText(Utility.formatCalendarDateString(hole.getReviewDate()));
                        }
                        recordDateButton.setText(Utility.formatCalendarDateString(hole.getRecordDate()));
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
                        if (hole.getRecordDate().compareTo(hole.getReviewDate()) > 0) {
                            hole.setRecordDate(hole.getReviewDate());
                            recordDateButton.setText(Utility.formatCalendarDateString(hole.getRecordDate()));
                        }
                        reviewDateButton.setText(Utility.formatCalendarDateString(hole.getReviewDate()));
                    }
                }, reviewDate.get(Calendar.YEAR), reviewDate.get(Calendar.MONTH), reviewDate.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.button_take_photo:
                File file = new File(imageTempDir,hole.getHoleId() + ".jpg");

                try {
                    if (file.exists()) {
                        file.delete();
                    }
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                imageUri = Uri.fromFile(file);
                Intent photoIintent = new Intent("android.media.action.IMAGE_CAPTURE"); //照相
                photoIintent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //指定图片输出地址
                startActivityForResult(photoIintent,TAKE_PHOTO);

                break;
            default:
                break;
        }
    }

    private void refreshHoleInfoTable() {
        projectNameEditText.setText(hole.getProjectName());

        holeIdPart1Spinner.setSelection(Utility.getHoleIdPart1Index(hole.getHoleIdPart1()));
        holeIdPart2TextView.setText(Html.fromHtml(formatHoleIdPart2(hole.getHoleIdPart2())));
        holeIdPart3Spinner.setSelection(Utility.getProjectIdPart3Index(hole.getHoleIdPart3()));
        holeIdPart4EditText.setText(hole.getHoleIdPart4());

        projectStageSpinner.setSelection(Utility.getProjectStageIndex(hole.getProjectStage()));
        articleSpinner.setSelection(Utility.getArticleIndex(hole.getArticle()));

        mileageEditText.setText(String.valueOf(hole.getMileage()));
        rigTypeEditText.setText(hole.getRigType());
        engineTypeEditText.setText(hole.getEngineType());
        pumpTypeEditText.setText(hole.getPumpType());

        initialLevelDepthEditText.setText(String.valueOf(hole.getInitialLevel()));
        stableLevelDepthEditText.setText(String.valueOf(hole.getStableLevel()));
        elevationEditText.setText(String.valueOf(hole.getHoleElevation()));
        designedDepthEditText.setText(String.valueOf(hole.getDesignedDepth()));
        longitudeDistanceEditText.setText(String.valueOf(hole.getLongitudeDistance()));
        latitudeDistanceEditText.setText(String.valueOf(hole.getLatitudeDistance()));
        explorationUnitEditText.setText(hole.getExplorationUnit());
        machineNumberEditText.setText(hole.getMachineNumber());
        acturalDepthEditText.setText(String.valueOf(hole.getActuralDepth()));
        noteEditText.setText(hole.getNote());
        recorderEditText.setText(hole.getRecorderName());
        reviewerEditText.setText(hole.getReviewerName());
        captainEditText.setText(hole.getCaptainName());
        squadEditText.setText(hole.getSquadName());
        offsetEditText.setText(String.valueOf(hole.getOffset()));

        startDateButton.setText(Utility.formatCalendarDateString(hole.getStartDate()));
        endDateButton.setText(Utility.formatCalendarDateString(hole.getEndDate()));
        initialLevelButton.setText(Utility.formatCalendarDateString(hole.getInitialLevelMeasuringDate()));
        stableLevelButton.setText(Utility.formatCalendarDateString(hole.getStableLevelMeasuringDate()));
        recordDateButton.setText(Utility.formatCalendarDateString(hole.getRecordDate()));
        reviewDateButton.setText(Utility.formatCalendarDateString(hole.getReviewDate()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PHOTO:
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(imageUri, "image/*");
                intent.putExtra("scale", true);

                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 800);
                intent.putExtra("outputY", 600);

                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                Intent intentBc = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intentBc.setData(imageUri);
                this.sendBroadcast(intentBc);
                startActivityForResult(intent, CROP_PHOTO);
                break;
            case CROP_PHOTO:
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(
                            getContentResolver().openInputStream(imageUri));
                    Toast.makeText(this, "照片保存成功", Toast.LENGTH_SHORT).show();
                    photoTableRow.setVisibility(View.VISIBLE);
                    photoView.setImageBitmap(bitmap);
                } catch(FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    private String formatHoleIdPart2(String str) {
        StringBuilder sb = new StringBuilder();

        if (str.startsWith("I") && !str.startsWith("II")) {
           sb.append("I");
        } else if (str.startsWith("II") && !str.startsWith("III")) {
           sb.append("II");
        } else if (str.startsWith("III")) {
           sb.append("III");
        } else if (str.startsWith("IV")) {
           sb.append("IV");
        }

        sb.append("<sub>");
        sb.append(str.substring(str.length() - 2));
        sb.append("</sub>");

        return sb.toString();
    }
}
