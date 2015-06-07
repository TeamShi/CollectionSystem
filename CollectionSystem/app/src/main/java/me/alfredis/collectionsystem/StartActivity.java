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
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import me.alfredis.collectionsystem.datastructure.Hole;
import me.alfredis.collectionsystem.datastructure.RigEvent;
import me.alfredis.collectionsystem.parser.HtmlParser;
import me.alfredis.collectionsystem.parser.MdbParser;
import me.alfredis.collectionsystem.parser.XlsParser;


public class StartActivity extends ActionBarActivity implements View.OnClickListener {
    private Button messageInputButton;
    private Button modifyConfigurationButton;
    private Button saveButton;
    private Button loadButton;
    private Button exportTablesAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        messageInputButton = (Button) findViewById(R.id.button_message_input);
        modifyConfigurationButton = (Button) findViewById(R.id.button_modify_configurations);
        saveButton = (Button) findViewById(R.id.button_main_save);
        loadButton = (Button) findViewById(R.id.button_main_load);
        exportTablesAll = (Button) findViewById(R.id.button_main_export_tables);

        messageInputButton.setOnClickListener(this);
        modifyConfigurationButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        loadButton.setOnClickListener(this);
        exportTablesAll.setOnClickListener(this);
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
            case R.id.button_main_export_tables:
                File exportDir = new File(baseDir+"export/");
                if(!exportDir.exists()){
                    exportDir.mkdirs();
                }

                File tempDir = new File(baseDir + "temp/");
                tempDir.mkdir();

                String mdbPath = tempDir.getPath() + "/DlcGeoInfo.mdb";
                String xlsTempPath = tempDir.getPath() + "/zuantan.xls";

                try {
                    AssetManager assetManageer = getAssets();
                    File mdbFile = new File(mdbPath);
                    if (!mdbFile.exists()) {
                        InputStream is = null;
                        is = assetManageer.open("DlcGeoInfo.mdb");
                        Utility.copyFile(is, mdbFile);
                    }
                    ArrayList<Hole> holeList = DataManager.holes;
                    for (Hole hole : holeList) {
                        if (null == hole.getRigList()) {
                            hole.setRigList(new ArrayList<RigEvent>());
                        }
                    }

                    boolean exportXls = XlsParser.parse(xlsTempPath, DataManager.holes);
                    boolean exportHtml = HtmlParser.parse(tempDir.getPath(), DataManager.holes, assetManageer);
                    boolean exportMdb = MdbParser.parse(mdbFile, DataManager.holes);

                    if (exportHtml && exportMdb && exportXls) {
                        Toast.makeText(getApplicationContext(), "导出所有内容成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "导出所有内容失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "导出所有内容失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
