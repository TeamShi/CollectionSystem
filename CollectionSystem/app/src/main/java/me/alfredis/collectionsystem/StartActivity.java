package me.alfredis.collectionsystem;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        messageInputButton = (Button) findViewById(R.id.button_message_input);
        saveButton = (Button) findViewById(R.id.button_main_save);
        loadButton = (Button) findViewById(R.id.button_main_load);
        exportTablesAll = (Button) findViewById(R.id.button_main_export_tables);
        previewButton = (Button) findViewById(R.id.button_preview_table);

        messageInputButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);
        exportTablesAll.setOnClickListener(this);
        previewButton.setOnClickListener(this);

        //TODO: Johnson. load configuration.
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

        switch(view.getId()) {
            case R.id.button_message_input:
                Intent intent = new Intent(this, HoleIndexActivity.class);
                startActivity(intent);
                break;
            case R.id.button_modify_configurations:
                //TODO: Alfred.
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
                    if(DataManager.holes.addAll(holeArrayList)){
                        Toast.makeText(getApplicationContext(), "载入成功！", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "载入失败！", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "载入失败！", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            case R.id.button_preview_table:
                //TODO: Johnson. Save the table to a temp folder and pass the folder to the intent
                Intent intent2 = new Intent(this, HtmlViewActivity.class);

                intent2.putExtra("table_path", "file:///sdcard/Download/a.html");
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
                    AssetManager assetManageer = getAssets();
                    File mdbFile = new File(mdbTempPath);
                    if (!mdbFile.exists()) {
                        InputStream is = assetManageer.open("DlcGeoInfo.mdb");
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
