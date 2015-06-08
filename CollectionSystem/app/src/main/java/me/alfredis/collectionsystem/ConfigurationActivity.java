package me.alfredis.collectionsystem;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;


public class ConfigurationActivity extends ActionBarActivity {
    private static final int QUERY_SPT = 0;
    private static final int QUERY_DST = 1;
    private static final int MODIFY_CONFIG = 2;

    private TextView sptConfigurationTable1Argument1TextView;
    private TextView sptConfigurationTable1Argument2TextView;
    private TextView sptConfigurationTable1Argument3TextView;
    private TextView sptConfigurationTable1Argument4TextView;
    private TextView sptConfigurationTable1Argument5TextView;
    private TextView sptConfigurationTable1Argument6TextView;

    private EditText sptConfigurationTable1Argument1HighEditText;
    private EditText sptConfigurationTable1Argument2LowEditText;
    private EditText sptConfigurationTable1Argument2HighEditText;
    private EditText sptConfigurationTable1Argument3LowEditText;
    private EditText sptConfigurationTable1Argument3HighEditText;
    private EditText sptConfigurationTable1Argument4LowEditText;
    private EditText sptConfigurationTable1Argument4HighEditText;
    private EditText sptConfigurationTable1Argument5LowEditText;
    private EditText sptConfigurationTable1Argument5HighEditText;
    private EditText sptConfigurationTable1Argument6LowEditText;

    private TextView sptConfigurationTable2Argument1TextView;
    private TextView sptConfigurationTable2Argument2TextView;
    private TextView sptConfigurationTable2Argument3TextView;
    private TextView sptConfigurationTable2Argument4TextView;

    private EditText sptConfigurationTable2Argument1HighEditText;
    private EditText sptConfigurationTable2Argument2LowEditText;
    private EditText sptConfigurationTable2Argument2HighEditText;
    private EditText sptConfigurationTable2Argument3LowEditText;
    private EditText sptConfigurationTable2Argument3HighEditText;
    private EditText sptConfigurationTable2Argument4LowEditText;

    private TextView sptConfigurationTable3Argument1TextView;
    private TextView sptConfigurationTable3Argument2TextView;
    private TextView sptConfigurationTable3Argument3TextView;
    private TextView sptConfigurationTable3Argument4TextView;

    private EditText sptConfigurationTable3Argument1HighEditText;
    private EditText sptConfigurationTable3Argument2LowEditText;
    private EditText sptConfigurationTable3Argument2HighEditText;
    private EditText sptConfigurationTable3Argument3LowEditText;
    private EditText sptConfigurationTable3Argument3HighEditText;
    private EditText sptConfigurationTable3Argument4LowEditText;

    private TextView dstConfigurationTable1_63_5_HeaderTextView;
    private TextView dstConfigurationTable1_63_5_Argument1TextView;
    private EditText dstConfigurationTable1_63_5_Argument1LowEditText;
    private EditText dstConfigurationTable1_63_5_Argument1HighEditText;
    private TextView dstConfigurationTable1_63_5_Argument2TextView;
    private EditText dstConfigurationTable1_63_5_Argument2LowEditText;
    private EditText dstConfigurationTable1_63_5_Argument2HighEditText;
    private TextView dstConfigurationTable1_63_5_Argument3TextView;
    private EditText dstConfigurationTable1_63_5_Argument3LowEditText;
    private EditText dstConfigurationTable1_63_5_Argument3HighEditText;
    private TextView dstConfigurationTable1_63_5_Argument4TextView;
    private EditText dstConfigurationTable1_63_5_Argument4LowEditText;
    private EditText dstConfigurationTable1_63_5_Argument4HighEditText;

    private TextView dstConfigurationTable1_120_HeaderTextView;
    private TextView dstConfigurationTable1_120_Argument1TextView;
    private EditText dstConfigurationTable1_120_Argument1LowEditText;
    private EditText dstConfigurationTable1_120_Argument1HighEditText;
    private TextView dstConfigurationTable1_120_Argument2TextView;
    private EditText dstConfigurationTable1_120_Argument2LowEditText;
    private EditText dstConfigurationTable1_120_Argument2HighEditText;
    private TextView dstConfigurationTable1_120_Argument3TextView;
    private EditText dstConfigurationTable1_120_Argument3LowEditText;
    private EditText dstConfigurationTable1_120_Argument3HighEditText;
    private TextView dstConfigurationTable1_120_Argument4TextView;
    private EditText dstConfigurationTable1_120_Argument4LowEditText;
    private EditText dstConfigurationTable1_120_Argument4HighEditText;
    private TextView dstConfigurationTable1_120_Argument5TextView;
    private EditText dstConfigurationTable1_120_Argument5LowEditText;
    private EditText dstConfigurationTable1_120_Argument5HighEditText;

    private TextView dstConfigurationTable2_63_6_HeaderTextView;
    private TextView dstConfigurationTable2_63_6_Argument1TextView;
    private EditText dstConfigurationTable2_63_6_Argument1LowEditText;
    private EditText dstConfigurationTable2_63_6_Argument1HighEditText;
    private TextView dstConfigurationTable2_63_6_Argument2TextView;
    private EditText dstConfigurationTable2_63_6_Argument2LowEditText;
    private EditText dstConfigurationTable2_63_6_Argument2HighEditText;
    private TextView dstConfigurationTable2_63_6_Argument3TextView;
    private EditText dstConfigurationTable2_63_6_Argument3LowEditText;
    private EditText dstConfigurationTable2_63_6_Argument3HighEditText;
    private TextView dstConfigurationTable2_63_6_Argument4TextView;
    private EditText dstConfigurationTable2_63_6_Argument4LowEditText;
    private EditText dstConfigurationTable2_63_6_Argument4HighEditText;

    private TextView dstConfigurationTable2_63_7_HeaderTextView;
    private TextView dstConfigurationTable2_63_7_Argument1TextView;
    private EditText dstConfigurationTable2_63_7_Argument1LowEditText;
    private EditText dstConfigurationTable2_63_7_Argument1HighEditText;
    private TextView dstConfigurationTable2_63_7_Argument2TextView;
    private EditText dstConfigurationTable2_63_7_Argument2LowEditText;
    private EditText dstConfigurationTable2_63_7_Argument2HighEditText;
    private TextView dstConfigurationTable2_63_7_Argument3TextView;
    private EditText dstConfigurationTable2_63_7_Argument3LowEditText;
    private EditText dstConfigurationTable2_63_7_Argument3HighEditText;
    private TextView dstConfigurationTable2_63_7_Argument4TextView;
    private EditText dstConfigurationTable2_63_7_Argument4LowEditText;
    private EditText dstConfigurationTable2_63_7_Argument4HighEditText;

    private TextView dstConfigurationTable2_63_8_HeaderTextView;
    private TextView dstConfigurationTable2_63_8_Argument1TextView;
    private EditText dstConfigurationTable2_63_8_Argument1LowEditText;
    private EditText dstConfigurationTable2_63_8_Argument1HighEditText;
    private TextView dstConfigurationTable2_63_8_Argument2TextView;
    private EditText dstConfigurationTable2_63_8_Argument2LowEditText;
    private EditText dstConfigurationTable2_63_8_Argument2HighEditText;
    private TextView dstConfigurationTable2_63_8_Argument3TextView;
    private EditText dstConfigurationTable2_63_8_Argument3LowEditText;
    private EditText dstConfigurationTable2_63_8_Argument3HighEditText;
    private TextView dstConfigurationTable2_63_8_Argument4TextView;
    private EditText dstConfigurationTable2_63_8_Argument4LowEditText;
    private EditText dstConfigurationTable2_63_8_Argument4HighEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        sptConfigurationTable1Argument1TextView = (TextView) findViewById(R.id.spt_configuration_table1_argument1_text_view);
        sptConfigurationTable1Argument2TextView = (TextView) findViewById(R.id.spt_configuration_table1_argument2_text_view);
        sptConfigurationTable1Argument3TextView = (TextView) findViewById(R.id.spt_configuration_table1_argument3_text_view);
        sptConfigurationTable1Argument4TextView = (TextView) findViewById(R.id.spt_configuration_table1_argument4_text_view);
        sptConfigurationTable1Argument5TextView = (TextView) findViewById(R.id.spt_configuration_table1_argument5_text_view);
        sptConfigurationTable1Argument6TextView = (TextView) findViewById(R.id.spt_configuration_table1_argument6_text_view);

        sptConfigurationTable1Argument1HighEditText = (EditText) findViewById(R.id.spt_configuration_table1_argument1_high_edit_text);
        sptConfigurationTable1Argument2LowEditText = (EditText) findViewById(R.id.spt_configuration_table1_argument2_low_edit_text);
        sptConfigurationTable1Argument2HighEditText = (EditText) findViewById(R.id.spt_configuration_table1_argument2_high_edit_text);
        sptConfigurationTable1Argument3LowEditText = (EditText) findViewById(R.id.spt_configuration_table1_argument3_low_edit_text);
        sptConfigurationTable1Argument3HighEditText = (EditText) findViewById(R.id.spt_configuration_table1_argument3_high_edit_text);
        sptConfigurationTable1Argument4LowEditText = (EditText) findViewById(R.id.spt_configuration_table1_argument4_low_edit_text);
        sptConfigurationTable1Argument4HighEditText = (EditText) findViewById(R.id.spt_configuration_table1_argument4_high_edit_text);
        sptConfigurationTable1Argument5LowEditText = (EditText) findViewById(R.id.spt_configuration_table1_argument5_low_edit_text);
        sptConfigurationTable1Argument5HighEditText = (EditText) findViewById(R.id.spt_configuration_table1_argument5_high_edit_text);
        sptConfigurationTable1Argument6LowEditText = (EditText) findViewById(R.id.spt_configuration_table1_argument6_low_edit_text);

        sptConfigurationTable2Argument1TextView = (TextView) findViewById(R.id.spt_configuration_table2_argument1_text_view);
        sptConfigurationTable2Argument2TextView = (TextView) findViewById(R.id.spt_configuration_table2_argument2_text_view);
        sptConfigurationTable2Argument3TextView = (TextView) findViewById(R.id.spt_configuration_table2_argument3_text_view);
        sptConfigurationTable2Argument4TextView = (TextView) findViewById(R.id.spt_configuration_table2_argument4_text_view);

        sptConfigurationTable2Argument1HighEditText = (EditText) findViewById(R.id.spt_configuration_table2_argument1_high_edit_text);
        sptConfigurationTable2Argument2LowEditText = (EditText) findViewById(R.id.spt_configuration_table2_argument2_low_edit_text);
        sptConfigurationTable2Argument2HighEditText = (EditText) findViewById(R.id.spt_configuration_table2_argument2_high_edit_text);
        sptConfigurationTable2Argument3LowEditText = (EditText) findViewById(R.id.spt_configuration_table2_argument3_low_edit_text);
        sptConfigurationTable2Argument3HighEditText = (EditText) findViewById(R.id.spt_configuration_table2_argument3_high_edit_text);
        sptConfigurationTable2Argument4LowEditText = (EditText) findViewById(R.id.spt_configuration_table2_argument4_low_edit_text);

        sptConfigurationTable3Argument1TextView = (TextView) findViewById(R.id.spt_configuration_table3_argument1_text_view);
        sptConfigurationTable3Argument2TextView = (TextView) findViewById(R.id.spt_configuration_table3_argument2_text_view);
        sptConfigurationTable3Argument3TextView = (TextView) findViewById(R.id.spt_configuration_table3_argument3_text_view);
        sptConfigurationTable3Argument4TextView = (TextView) findViewById(R.id.spt_configuration_table3_argument4_text_view);

        sptConfigurationTable3Argument1HighEditText  = (EditText) findViewById(R.id.spt_configuration_table3_argument1_high_edit_text);
        sptConfigurationTable3Argument2LowEditText = (EditText) findViewById(R.id.spt_configuration_table3_argument2_low_edit_text);
        sptConfigurationTable3Argument2HighEditText  = (EditText) findViewById(R.id.spt_configuration_table3_argument2_high_edit_text);
        sptConfigurationTable3Argument3LowEditText = (EditText) findViewById(R.id.spt_configuration_table3_argument3_low_edit_text);
        sptConfigurationTable3Argument3HighEditText = (EditText) findViewById(R.id.spt_configuration_table3_argument3_high_edit_text);
        sptConfigurationTable3Argument4LowEditText = (EditText) findViewById(R.id.spt_configuration_table3_argument4_low_edit_text);

        /*dstConfigurationTable1_63_5_HeaderTextView;
        dstConfigurationTable1_63_5_Argument1TextView;
        dstConfigurationTable1_63_5_Argument1LowEditText;
        dstConfigurationTable1_63_5_Argument1HighEditText;
        dstConfigurationTable1_63_5_Argument2TextView;
        dstConfigurationTable1_63_5_Argument2LowEditText;
        dstConfigurationTable1_63_5_Argument2HighEditText;
        dstConfigurationTable1_63_5_Argument3TextView;
        dstConfigurationTable1_63_5_Argument3LowEditText;
        dstConfigurationTable1_63_5_Argument3HighEditText;
        dstConfigurationTable1_63_5_Argument4TextView;
        dstConfigurationTable1_63_5_Argument4LowEditText;
        dstConfigurationTable1_63_5_Argument4HighEditText;

        dstConfigurationTable1_120_HeaderTextView;
        dstConfigurationTable1_120_Argument1TextView;
        dstConfigurationTable1_120_Argument1LowEditText;
        dstConfigurationTable1_120_Argument1HighEditText;
        dstConfigurationTable1_120_Argument2TextView;
        dstConfigurationTable1_120_Argument2LowEditText;
        dstConfigurationTable1_120_Argument2HighEditText;
        dstConfigurationTable1_120_Argument3TextView;
        dstConfigurationTable1_120_Argument3LowEditText;
        dstConfigurationTable1_120_Argument3HighEditText;
        dstConfigurationTable1_120_Argument4TextView;
        dstConfigurationTable1_120_Argument4LowEditText;
        dstConfigurationTable1_120_Argument4HighEditText;
        dstConfigurationTable1_120_Argument5TextView;
        dstConfigurationTable1_120_Argument5LowEditText;
        dstConfigurationTable1_120_Argument5HighEditText;

        dstConfigurationTable2_63_6_HeaderTextView;
        dstConfigurationTable2_63_6_Argument1TextView;
        dstConfigurationTable2_63_6_Argument1LowEditText;
        dstConfigurationTable2_63_6_Argument1HighEditText;
        dstConfigurationTable2_63_6_Argument2TextView;
        dstConfigurationTable2_63_6_Argument2LowEditText;
        dstConfigurationTable2_63_6_Argument2HighEditText;
        dstConfigurationTable2_63_6_Argument3TextView;
        dstConfigurationTable2_63_6_Argument3LowEditText;
        dstConfigurationTable2_63_6_Argument3HighEditText;
        dstConfigurationTable2_63_6_Argument4TextView;
        dstConfigurationTable2_63_6_Argument4LowEditText;
        dstConfigurationTable2_63_6_Argument4HighEditText;

        dstConfigurationTable2_63_7_HeaderTextView;
        dstConfigurationTable2_63_7_Argument1TextView;
        dstConfigurationTable2_63_7_Argument1LowEditText;
        dstConfigurationTable2_63_7_Argument1HighEditText;
        dstConfigurationTable2_63_7_Argument2TextView;
        dstConfigurationTable2_63_7_Argument2LowEditText;
        dstConfigurationTable2_63_7_Argument2HighEditText;
        dstConfigurationTable2_63_7_Argument3TextView;
        dstConfigurationTable2_63_7_Argument3LowEditText;
        dstConfigurationTable2_63_7_Argument3HighEditText;
        dstConfigurationTable2_63_7_Argument4TextView;
        dstConfigurationTable2_63_7_Argument4LowEditText;
        dstConfigurationTable2_63_7_Argument4HighEditText;

        dstConfigurationTable2_63_8_HeaderTextView;
        dstConfigurationTable2_63_8_Argument1TextView;
        dstConfigurationTable2_63_8_Argument1LowEditText;
        dstConfigurationTable2_63_8_Argument1HighEditText;
        dstConfigurationTable2_63_8_Argument2TextView;
        dstConfigurationTable2_63_8_Argument2LowEditText;
        dstConfigurationTable2_63_8_Argument2HighEditText;
        dstConfigurationTable2_63_8_Argument3TextView;
        dstConfigurationTable2_63_8_Argument3LowEditText;
        dstConfigurationTable2_63_8_Argument3HighEditText;
        dstConfigurationTable2_63_8_Argument4TextView;
        dstConfigurationTable2_63_8_Argument4LowEditText;
        dstConfigurationTable2_63_8_Argument4HighEditText;*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuration, menu);
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
}
