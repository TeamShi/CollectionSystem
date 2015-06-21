package me.alfredis.collectionsystem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hpsf.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import me.alfredis.collectionsystem.datastructure.Hole;
import me.alfredis.collectionsystem.datastructure.RigEvent;
import me.alfredis.collectionsystem.parser.HtmlParser;
import me.alfredis.collectionsystem.parser.MdbParser;
import me.alfredis.collectionsystem.parser.XlsParser;


public class StartActivity extends ActionBarActivity implements View.OnClickListener {
    private Button messageInputButton;
    private Button saveButton;
    private Button loadButton;
    private Button exportTablesAll;
    private Button previewButton;

    private EditText licenseEditText;
    private TextView licenseTextView;
    private Button licenseButton;

    private String licenseString;

    public String getLicenseString() {
        return licenseString;
    }

    public void setLicenseString(String licenseString) {
        this.licenseString = licenseString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        messageInputButton = (Button) findViewById(R.id.button_message_input);
        saveButton = (Button) findViewById(R.id.button_main_save);
        loadButton = (Button) findViewById(R.id.button_main_load);
        exportTablesAll = (Button) findViewById(R.id.button_main_export_tables);
        previewButton = (Button) findViewById(R.id.button_preview_table);

        licenseButton = (Button) findViewById(R.id.button_license);
        licenseTextView = (TextView) findViewById(R.id.text_view_lincense_hint);
        licenseEditText = (EditText) findViewById(R.id.edit_text_license);

        messageInputButton.setEnabled(false);
        saveButton.setEnabled(false);
        loadButton.setEnabled(false);
        exportTablesAll.setEnabled(false);
        previewButton.setEnabled(false);

        messageInputButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);
        exportTablesAll.setOnClickListener(this);
        previewButton.setOnClickListener(this);
        licenseButton.setOnClickListener(this);

        String licenseFilePath = Environment.getExternalStorageDirectory().getPath() + "/ZuanTan/config/license.dat";
        File licenseFile = new File(licenseFilePath);
        if (licenseFile.exists()) {
            FileReader fr = null;
            try {
                fr = new FileReader(licenseFilePath);
                BufferedReader br = new BufferedReader(fr);
                licenseString = br.readLine();
                br.close();
                fr.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (Utility.validateDate(licenseString)) {
                messageInputButton.setEnabled(true);
                saveButton.setEnabled(true);
                loadButton.setEnabled(true);
                exportTablesAll.setEnabled(true);
                previewButton.setEnabled(true);
                licenseEditText.setVisibility(View.GONE);
                licenseButton.setVisibility(View.GONE);

                Calendar c = new GregorianCalendar();
                c.setTimeInMillis(Utility.getExpiredDate(licenseString) * 1000);
                licenseTextView.setText("验证成功。过期时间：" + c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + (c.get(Calendar.DAY_OF_MONTH)));
            } else {
                licenseTextView.setText("有效期过期，请重新输入授权码。");
            }
        }

        File configDir = new  File(Environment.getExternalStorageDirectory().getPath() + "/ZuanTan/config/");
        if (!configDir.exists()) {
            configDir.mkdirs();
        }



        /*
        if (!licenseFile.exists()) {

        }*/


        File configFile = new File(configDir + "/config.ser");
        if (!configFile.exists()) {
            try {
                InputStream configFileStream = getAssets().open("config.ser");
                Utility.copyFile(configFileStream, configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ConfigurationManager.loadConfig(configFile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
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
    public void onClick(View view) {
        String baseDir = Environment.getExternalStorageDirectory().getPath()+"/ZuanTan/";
        String xlsPath = baseDir + "zuantan.xls";
        AssetManager assetManageer = getAssets();

        switch(view.getId()) {
            case R.id.button_license:
                if (licenseEditText.getText().length() > 10) {
                    licenseTextView.setText("有效期过期，请重新输入授权码。");
                    return;
                }
                if (Utility.validateDate(licenseEditText.getText().toString())) {
                    messageInputButton.setEnabled(true);
                    saveButton.setEnabled(true);
                    loadButton.setEnabled(true);
                    exportTablesAll.setEnabled(true);
                    previewButton.setEnabled(true);
                    licenseEditText.setVisibility(View.GONE);
                    licenseButton.setVisibility(View.GONE);

                    Calendar c = new GregorianCalendar();
                    c.setTimeInMillis(Utility.getExpiredDate(licenseEditText.getText().toString()) * 1000);
                    licenseTextView.setText("验证成功。过期时间：" + c.get(Calendar.YEAR) + "/" + (c.get(Calendar.MONTH) + 1) + "/" + (c.get(Calendar.DAY_OF_MONTH)));

                    String licenseFilePath = Environment.getExternalStorageDirectory().getPath() + "/ZuanTan/config/license.dat";
                    try {
                        FileWriter fw = new FileWriter(licenseFilePath);
                        BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(licenseEditText.getText().toString().toUpperCase());
                        bw.close();
                        fw.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    licenseTextView.setText("有效期过期，请重新输入授权码。");
                }
                break;
            case R.id.button_message_input:
                Intent intent = new Intent(this, HoleIndexActivity.class);
                startActivity(intent);
                break;
            case R.id.button_main_save:
                try {
                    boolean exportXls =  XlsParser.parse(xlsPath,DataManager.holes);
                    if(exportXls){
                        Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "保存失败！" , Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "保存失败！" , Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case R.id.button_main_load:
                try {
                    DataManager.holes.clear();
                    ArrayList<Hole> holeArrayList = XlsParser.parse(xlsPath);
                    DataManager.holes.addAll(holeArrayList);
                    Toast.makeText(getApplicationContext(), "载入成功！", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "载入失败！", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case R.id.button_preview_table:
                File tempHtmls = new File(baseDir+"tempHtmls");
                if(!tempHtmls.exists()){
                    tempHtmls.mkdirs();
                }

                HtmlParser.parseRig(tempHtmls.getPath() + "/", DataManager.holes, assetManageer);

                Intent intent2 = new Intent(this, HtmlViewActivity.class);
                Uri uri = Uri.fromFile(new File(tempHtmls, "rigEvent.html"));
                intent2.putExtra("table_path", uri.toString());
                startActivity(intent2);
                break;
            case R.id.button_main_export_tables:
                File exportDir = new File(baseDir+"export/");
                if(!exportDir.exists()){
                    exportDir.mkdirs();
                }

                File tempDir = new File(baseDir + "temp/");
                tempDir.mkdir();

                String mdbTempPath = tempDir.getPath() + "/DlcGeoInfo.mdb";
                String xlsTempPath = tempDir.getPath() + "/zuantan.xls";
                File photoTempDir = new File(tempDir.getPath() + "/photo");
                if(!photoTempDir.exists()) {
                    photoTempDir.mkdirs();
                }
                File htmlTempDir = new File(tempDir.getPath() + "/html");
                if(!htmlTempDir.exists()) {
                    htmlTempDir.mkdirs();
                }
                File imageTempDir = new File(Environment.getExternalStorageDirectory().getPath()+"/ZuanTan/tempPhotoes");
                if(!imageTempDir.exists()) {
                    imageTempDir.mkdirs();
                }


                try {
                    ArrayList<Hole> holeList = DataManager.holes;
                    for (Hole hole : holeList) {
                        if (null == hole.getRigList()) {
                            hole.setRigList(new ArrayList<RigEvent>());
                        }
                        File file = new File(imageTempDir+"/"+hole.getHoleId()+".jpg");
                        if(file.exists()) {
                            File destFile = new File(photoTempDir,hole.getHoleId()+".jpg");
                            InputStream inputStream = new FileInputStream(file);
                            Utility.copyFile(inputStream,destFile);
                        }
                    }
                    File mdbFile = new File(mdbTempPath);
                    if (!mdbFile.exists()) {
                        InputStream is =  assetManageer.open("DlcGeoInfo.mdb");
                        Utility.copyFile(is, mdbFile);
                    }

                    boolean exportXls = XlsParser.parse(xlsTempPath, holeList);
                    boolean exportHtml = HtmlParser.parse(htmlTempDir.getPath(), holeList, assetManageer);
                    boolean exportMdb = MdbParser.parse(mdbFile, holeList);

                    //zip temp dir to export dir
                    String timeStamp = Utility.formatCalendarDateString(Calendar.getInstance(), "yyyy-MM-dd-HH-mm-ss");
                    String zipFileName = timeStamp+".zip";
                    Utility.zip(tempDir.getPath(),exportDir.getPath(),zipFileName);

                    if (exportHtml && exportMdb && exportXls) {
                        Toast.makeText(getApplicationContext(), "导出所有内容成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "导出所有内容失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "导出所有内容失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


}
