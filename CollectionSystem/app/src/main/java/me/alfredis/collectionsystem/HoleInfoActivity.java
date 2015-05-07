package me.alfredis.collectionsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;
import java.util.Random;

import me.alfredis.collectionsystem.datastructure.Hole;


public class HoleInfoActivity extends ActionBarActivity implements View.OnClickListener {


    private Hole hole;

    private Button addButton;
    private Button backButton;

    private static final String TAG = "CollectionSystem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hole_info);

        addButton = (Button) findViewById(R.id.button_confirm_add_hole);
        backButton = (Button) findViewById(R.id.button_cancel_add_hole);

        addButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

        String requestCode = getIntent().getStringExtra("requestCode");
        Toast.makeText(getApplicationContext(), requestCode, Toast.LENGTH_SHORT).show();
        switch (requestCode) {
            case "ADD_HOLE":
                break;
            case "QUERY_HOLE":
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
        Intent intent;

        switch (v.getId()) {
            case R.id.button_confirm_add_hole:
                Log.d(TAG, "Add button clicked.");

                //test code
                Random r = new Random();
                Hole hole = new Hole(String.valueOf(r.nextInt()), "pn", "a", "a", 123, 123.45, 123, 123, 123, "alfred", new Date(1212313), "alfred", new Date(123123123), "test note", 123123);

                intent = new Intent();
                intent.putExtra("hole", hole);
                this.setResult(RESULT_OK, intent);
                this.finish();
                break;
            case R.id.button_cancel_add_hole:
                Log.d(TAG, "Cancel button clicked.");
                intent = new Intent();
                this.setResult(RESULT_CANCELED, intent);
                this.finish();
                break;
            default:
                break;
        }
    }
}
