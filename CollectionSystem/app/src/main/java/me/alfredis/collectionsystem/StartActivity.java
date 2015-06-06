package me.alfredis.collectionsystem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


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
        switch(view.getId()) {
            case R.id.button_message_input:
                Intent intent = new Intent(this, HoleIndexActivity.class);
                startActivity(intent);
                break;
            case R.id.button_modify_configurations:
                //TODO: Alfred.
                break;
            case R.id.button_main_save:
                //TODO: Johnson. move function here.
                break;
            case R.id.button_main_load:
                //TODO: Johnson. move function here.
                break;
            case R.id.button_main_export_tables:
                //TODO: Johnson. move function here.
                break;
        }
    }
}
