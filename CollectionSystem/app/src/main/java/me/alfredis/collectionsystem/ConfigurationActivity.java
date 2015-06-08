package me.alfredis.collectionsystem;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ConfigurationActivity extends ActionBarActivity {
    private static final int QUERY_SPT = 0;
    private static final int QUERY_DST = 1;
    private static final int MODIFY_CONFIG = 2;

    private static String requestType;

    private LinearLayout sptLinearLayout;
    private LinearLayout dstLinearLayout;

    private TextView sptConfigurationTable1Argument1TextView;
    private TextView sptConfigurationTable1Argument2TextView;
    private TextView sptConfigurationTable1Argument3TextView;
    private TextView sptConfigurationTable1Argument4TextView;
    private TextView sptConfigurationTable1Argument5TextView;
    private TextView sptConfigurationTable1Argument6TextView;

    private TextView sptConfigurationTable2Argument1TextView;
    private TextView sptConfigurationTable2Argument2TextView;
    private TextView sptConfigurationTable2Argument3TextView;
    private TextView sptConfigurationTable2Argument4TextView;

    private TextView sptConfigurationTable3Argument1TextView;
    private TextView sptConfigurationTable3Argument2TextView;
    private TextView sptConfigurationTable3Argument3TextView;
    private TextView sptConfigurationTable3Argument4TextView;

    private TextView dstConfigurationTable1_63_5_HeaderTextView;
    private TextView dstConfigurationTable1_63_5_Argument1TextView;
    private TextView dstConfigurationTable1_63_5_Argument2TextView;
    private TextView dstConfigurationTable1_63_5_Argument3TextView;
    private TextView dstConfigurationTable1_63_5_Argument4TextView;

    private TextView dstConfigurationTable1_120_HeaderTextView;
    private TextView dstConfigurationTable1_120_Argument1TextView;
    private TextView dstConfigurationTable1_120_Argument2TextView;
    private TextView dstConfigurationTable1_120_Argument3TextView;
    private TextView dstConfigurationTable1_120_Argument4TextView;
    private TextView dstConfigurationTable1_120_Argument5TextView;

    private TextView dstConfigurationTable2_63_6_HeaderTextView;
    private TextView dstConfigurationTable2_63_6_Argument1TextView;
    private TextView dstConfigurationTable2_63_6_Argument2TextView;
    private TextView dstConfigurationTable2_63_6_Argument3TextView;
    private TextView dstConfigurationTable2_63_6_Argument4TextView;

    private TextView dstConfigurationTable2_63_7_HeaderTextView;
    private TextView dstConfigurationTable2_63_7_Argument1TextView;
    private TextView dstConfigurationTable2_63_7_Argument2TextView;
    private TextView dstConfigurationTable2_63_7_Argument3TextView;
    private TextView dstConfigurationTable2_63_7_Argument4TextView;

    private TextView dstConfigurationTable2_63_8_HeaderTextView;
    private TextView dstConfigurationTable2_63_8_Argument1TextView;
    private TextView dstConfigurationTable2_63_8_Argument2TextView;
    private TextView dstConfigurationTable2_63_8_Argument3TextView;
    private TextView dstConfigurationTable2_63_8_Argument4TextView;


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

        sptConfigurationTable2Argument1TextView = (TextView) findViewById(R.id.spt_configuration_table2_argument1_text_view);
        sptConfigurationTable2Argument2TextView = (TextView) findViewById(R.id.spt_configuration_table2_argument2_text_view);
        sptConfigurationTable2Argument3TextView = (TextView) findViewById(R.id.spt_configuration_table2_argument3_text_view);
        sptConfigurationTable2Argument4TextView = (TextView) findViewById(R.id.spt_configuration_table2_argument4_text_view);

        sptConfigurationTable3Argument1TextView = (TextView) findViewById(R.id.spt_configuration_table3_argument1_text_view);
        sptConfigurationTable3Argument2TextView = (TextView) findViewById(R.id.spt_configuration_table3_argument2_text_view);
        sptConfigurationTable3Argument3TextView = (TextView) findViewById(R.id.spt_configuration_table3_argument3_text_view);
        sptConfigurationTable3Argument4TextView = (TextView) findViewById(R.id.spt_configuration_table3_argument4_text_view);

        dstConfigurationTable1_63_5_HeaderTextView = (TextView) findViewById(R.id.dst_configuration_table1_63_5_header_argument1_text_view);
        dstConfigurationTable1_63_5_Argument1TextView = (TextView) findViewById(R.id.dst_configuration_table1_63_5_argument1_text_view);
        dstConfigurationTable1_63_5_Argument2TextView = (TextView) findViewById(R.id.dst_configuration_table1_63_5_argument2_text_view);
        dstConfigurationTable1_63_5_Argument3TextView = (TextView) findViewById(R.id.dst_configuration_table1_63_5_argument3_text_view);
        dstConfigurationTable1_63_5_Argument4TextView = (TextView) findViewById(R.id.dst_configuration_table1_63_5_argument4_text_view);

        dstConfigurationTable1_120_HeaderTextView = (TextView) findViewById(R.id.dst_configuration_table1_120_header_argument1_text_view);
        dstConfigurationTable1_120_Argument1TextView = (TextView) findViewById(R.id.dst_configuration_table1_120_argument1_text_view);
        dstConfigurationTable1_120_Argument2TextView = (TextView) findViewById(R.id.dst_configuration_table1_120_argument2_text_view);
        dstConfigurationTable1_120_Argument3TextView = (TextView) findViewById(R.id.dst_configuration_table1_120_argument3_text_view);
        dstConfigurationTable1_120_Argument4TextView = (TextView) findViewById(R.id.dst_configuration_table1_120_argument4_text_view);
        dstConfigurationTable1_120_Argument5TextView = (TextView) findViewById(R.id.dst_configuration_table1_120_argument5_text_view);

        dstConfigurationTable2_63_6_HeaderTextView = (TextView) findViewById(R.id.dst_configuration_table2_63_6_header_text_view);
        dstConfigurationTable2_63_6_Argument1TextView = (TextView) findViewById(R.id.dst_configuration_table2_63_6_argument1_text_view);
        dstConfigurationTable2_63_6_Argument2TextView = (TextView) findViewById(R.id.dst_configuration_table2_63_6_argument2_text_view);
        dstConfigurationTable2_63_6_Argument3TextView = (TextView) findViewById(R.id.dst_configuration_table2_63_6_argument3_text_view);
        dstConfigurationTable2_63_6_Argument4TextView = (TextView) findViewById(R.id.dst_configuration_table2_63_6_argument4_text_view);

        dstConfigurationTable2_63_7_HeaderTextView = (TextView) findViewById(R.id.dst_configuration_table2_63_7_header_text_view);
        dstConfigurationTable2_63_7_Argument1TextView = (TextView) findViewById(R.id.dst_configuration_table2_63_7_argument1_text_view);
        dstConfigurationTable2_63_7_Argument2TextView = (TextView) findViewById(R.id.dst_configuration_table2_63_7_argument2_text_view);
        dstConfigurationTable2_63_7_Argument3TextView = (TextView) findViewById(R.id.dst_configuration_table2_63_7_argument3_text_view);
        dstConfigurationTable2_63_7_Argument4TextView = (TextView) findViewById(R.id.dst_configuration_table2_63_7_argument4_text_view);

        dstConfigurationTable2_63_8_HeaderTextView = (TextView) findViewById(R.id.dst_configuration_table2_63_8_header_text_view);
        dstConfigurationTable2_63_8_Argument1TextView = (TextView) findViewById(R.id.dst_configuration_table2_63_8_argument1_text_view);
        dstConfigurationTable2_63_8_Argument2TextView = (TextView) findViewById(R.id.dst_configuration_table2_63_8_argument2_text_view);
        dstConfigurationTable2_63_8_Argument3TextView = (TextView) findViewById(R.id.dst_configuration_table2_63_8_argument3_text_view);
        dstConfigurationTable2_63_8_Argument4TextView = (TextView) findViewById(R.id.dst_configuration_table2_63_8_argument4_text_view);

        sptLinearLayout = (LinearLayout) findViewById(R.id.spt_configuration_tables);
        dstLinearLayout = (LinearLayout) findViewById(R.id.dst_configuration_tables);

        sptConfigurationTable1Argument1TextView.setText("<" + String.valueOf(ConfigurationManager.getSptTable1Argument1()));
        sptConfigurationTable1Argument2TextView.setText(String.valueOf(ConfigurationManager.getSptTable1Argument1() + "~" + String.valueOf(ConfigurationManager.getSptTable1Argument2())));
        sptConfigurationTable1Argument3TextView.setText(String.valueOf(ConfigurationManager.getSptTable1Argument2() + "~" + String.valueOf(ConfigurationManager.getSptTable1Argument3())));
        sptConfigurationTable1Argument4TextView.setText(String.valueOf(ConfigurationManager.getSptTable1Argument3() + "~" + String.valueOf(ConfigurationManager.getSptTable1Argument4())));
        sptConfigurationTable1Argument5TextView.setText(String.valueOf(ConfigurationManager.getSptTable1Argument4() + "~" + String.valueOf(ConfigurationManager.getSptTable1Argument5())));
        sptConfigurationTable1Argument6TextView.setText(String.valueOf(">" + ConfigurationManager.getSptTable1Argument5()));

        sptConfigurationTable2Argument1TextView.setText("N≤" + String.valueOf(ConfigurationManager.getSptTable2Argument1()));
        sptConfigurationTable2Argument2TextView.setText(String.valueOf(ConfigurationManager.getSptTable2Argument1() + "<N≤" + String.valueOf(ConfigurationManager.getSptTable2Argument2())));
        sptConfigurationTable2Argument3TextView.setText(String.valueOf(ConfigurationManager.getSptTable2Argument2() + "<N≤" + String.valueOf(ConfigurationManager.getSptTable2Argument3())));
        sptConfigurationTable2Argument4TextView.setText("N>" + String.valueOf(ConfigurationManager.getSptTable2Argument3()));

        sptConfigurationTable3Argument1TextView.setText("N≤" + String.valueOf(ConfigurationManager.getSptTable3Argument1()));
        sptConfigurationTable3Argument2TextView.setText(String.valueOf(ConfigurationManager.getSptTable3Argument1() + "<N≤" + String.valueOf(ConfigurationManager.getSptTable3Argument2())));
        sptConfigurationTable3Argument3TextView.setText(String.valueOf(ConfigurationManager.getSptTable3Argument2() + "<N≤" + String.valueOf(ConfigurationManager.getSptTable3Argument3())));
        sptConfigurationTable3Argument4TextView.setText("N>" + String.valueOf(ConfigurationManager.getSptTable3Argument3()));

        dstConfigurationTable1_63_5_HeaderTextView.setText(Html.fromHtml("重型圆锥动力触探击数N<sub>63.5<sub>"));
        dstConfigurationTable1_120_HeaderTextView.setText(Html.fromHtml("重型圆锥动力触探击数N<sub>120<sub>"));
        dstConfigurationTable2_63_6_HeaderTextView.setText(Html.fromHtml("重型圆锥动力触探击数N<sub>63.6<sub>"));
        dstConfigurationTable2_63_7_HeaderTextView.setText(Html.fromHtml("重型圆锥动力触探击数N<sub>63.7<sub>"));
        dstConfigurationTable2_63_8_HeaderTextView.setText(Html.fromHtml("重型圆锥动力触探击数N<sub>63.8<sub>"));

        dstConfigurationTable1_63_5_Argument1TextView.setText(Html.fromHtml("N<sub>63.5</sub>≤" + String.valueOf(ConfigurationManager.getDstTable1_63_5_Argument2())));
        dstConfigurationTable1_63_5_Argument2TextView.setText(Html.fromHtml(String.valueOf(ConfigurationManager.getDstTable1_63_5_Argument2()) + "&lt;N<sub>63.5</sub>≤" + String.valueOf(ConfigurationManager.getDstTable1_63_5_Argument3())));
        dstConfigurationTable1_63_5_Argument3TextView.setText(Html.fromHtml(String.valueOf(ConfigurationManager.getDstTable1_63_5_Argument3()) + "&lt;N<sub>63.5</sub>≤" + String.valueOf(ConfigurationManager.getDstTable1_63_5_Argument4())));
        dstConfigurationTable1_63_5_Argument4TextView.setText(Html.fromHtml("N<sub>63.5</sub>" + "&gt;" + String.valueOf(ConfigurationManager.getDstTable1_63_5_Argument4())));

        dstConfigurationTable1_120_Argument1TextView.setText(Html.fromHtml("N<sub>120</sub>≤" + String.valueOf(ConfigurationManager.getDstTable1_120_Argument2())));
        dstConfigurationTable1_120_Argument2TextView.setText(Html.fromHtml(String.valueOf(ConfigurationManager.getDstTable1_120_Argument2()) + "&lt;" + "N<sub>120</sub>" + "≤" + String.valueOf(ConfigurationManager.getDstTable1_120_Argument3())));
        dstConfigurationTable1_120_Argument3TextView.setText(Html.fromHtml(String.valueOf(ConfigurationManager.getDstTable1_120_Argument3()) + "&lt;" + "N<sub>120</sub>" + "≤" + String.valueOf(ConfigurationManager.getDstTable1_120_Argument4())));
        dstConfigurationTable1_120_Argument4TextView.setText(Html.fromHtml(String.valueOf(ConfigurationManager.getDstTable1_120_Argument4()) + "&lt;" + "N<sub>120</sub>" + "≤" + String.valueOf(ConfigurationManager.getDstTable1_120_Argument5())));
        dstConfigurationTable1_120_Argument5TextView.setText(Html.fromHtml("N<sub>120</sub>" + "&gt;" + String.valueOf(ConfigurationManager.getDstTable1_120_Argument5())));

        dstConfigurationTable2_63_6_Argument1TextView.setText(Html.fromHtml("N<sub>63.6</sub>≤" + ConfigurationManager.getDstTable2_63_6_Argument1()));
        dstConfigurationTable2_63_6_Argument2TextView.setText(Html.fromHtml(ConfigurationManager.getDstTable2_63_6_Argument1() + "&lt;N<sub>63.6</sub>≤" + ConfigurationManager.getDstTable2_63_6_Argument2()));
        dstConfigurationTable2_63_6_Argument3TextView.setText(Html.fromHtml(ConfigurationManager.getDstTable2_63_6_Argument2() + "&lt;N<sub>63.6</sub>≤" + ConfigurationManager.getDstTable2_63_6_Argument3()));
        dstConfigurationTable2_63_6_Argument4TextView.setText(Html.fromHtml("N<sub>63.6</sub>&gt;" + ConfigurationManager.getDstTable2_63_6_Argument3()));

        dstConfigurationTable2_63_7_Argument1TextView.setText(Html.fromHtml("N<sub>63.7</sub>≤" + ConfigurationManager.getDstTable2_63_7_Argument1()));
        dstConfigurationTable2_63_7_Argument2TextView.setText(Html.fromHtml(ConfigurationManager.getDstTable2_63_7_Argument1() + "&lt;N<sub>63.7</sub>≤" + ConfigurationManager.getDstTable2_63_7_Argument2()));
        dstConfigurationTable2_63_7_Argument3TextView.setText(Html.fromHtml(ConfigurationManager.getDstTable2_63_7_Argument2() + "&lt;N<sub>63.7</sub>≤" + ConfigurationManager.getDstTable2_63_7_Argument3()));
        dstConfigurationTable2_63_7_Argument4TextView.setText(Html.fromHtml("N<sub>63.7</sub>&gt;" + ConfigurationManager.getDstTable2_63_7_Argument3()));

        dstConfigurationTable2_63_8_Argument1TextView.setText(Html.fromHtml("N<sub>63.8</sub>≤" + ConfigurationManager.getDstTable2_63_8_Argument1()));
        dstConfigurationTable2_63_8_Argument2TextView.setText(Html.fromHtml(ConfigurationManager.getDstTable2_63_8_Argument1() + "&lt;N<sub>63.8</sub>≤" + ConfigurationManager.getDstTable2_63_8_Argument2()));
        dstConfigurationTable2_63_8_Argument3TextView.setText(Html.fromHtml(ConfigurationManager.getDstTable2_63_8_Argument2() + "&lt;N<sub>63.8</sub>≤" + ConfigurationManager.getDstTable2_63_8_Argument3()));
        dstConfigurationTable2_63_8_Argument4TextView.setText(Html.fromHtml("N<sub>63.8</sub>&gt;" + ConfigurationManager.getDstTable2_63_8_Argument3()));

        requestType = getIntent().getStringExtra("request_type");

        switch (requestType) {
            case "QUERY_SPT":
                dstLinearLayout.setVisibility(View.GONE);
                sptLinearLayout.setVisibility(View.VISIBLE);
                break;
            case "QUERY_DST":
                dstLinearLayout.setVisibility(View.VISIBLE);
                sptLinearLayout.setVisibility(View.GONE);
                break;
        }

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
