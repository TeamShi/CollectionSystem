package me.alfredis.collectionsystem;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import me.alfredis.collectionsystem.datastructure.RigEvent;

public class RigInfoActivity extends ActionBarActivity {

    private RigEvent rig;
    private String requestCode;

    private EditText classPeopleCountEditText;

    private Button addRigButton;

    private Spinner rigTypeSpinner;

    private static final String TAG = "CollectionSystem";

    private static final int ADD_RIG = 0;

    private static final String[] RIG_TYPE_SPINNER_OPTIONS = {"搬家移孔、下雨停工，其他",
            "干钻", "合水钻", "金刚石钻", "x粒钻", "标准贯入试验", "动力触探试验", "下套管"};

    private  ArrayAdapter<String> rigTypeAdapter;

    private BlankRigFragment blankRigFragment;
    private NormalRigFragment normalRigFragment;
    private DSTRigFragment dstRigFragment;
    private SPTRigFragment sptRigFragment;
    private CasedRigFragment casedRigFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rig_info);

        blankRigFragment = new BlankRigFragment();
        normalRigFragment = new NormalRigFragment();
        dstRigFragment = new DSTRigFragment();
        sptRigFragment = new SPTRigFragment();
        casedRigFragment = new CasedRigFragment();

        classPeopleCountEditText = (EditText) findViewById(R.id.class_people_count);

        addRigButton = (Button) findViewById(R.id.button_add_rig);

        rigTypeSpinner = (Spinner) findViewById(R.id.spinner_rig_type);

        rigTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, RIG_TYPE_SPINNER_OPTIONS);
        rigTypeSpinner.setAdapter(rigTypeAdapter);
        rigTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        getFragmentManager().beginTransaction().replace(R.id.fragment_rig_details, blankRigFragment).commit();
                        break;
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        getFragmentManager().beginTransaction().replace(R.id.fragment_rig_details, normalRigFragment).commit();
                        break;
                    case 5:
                        getFragmentManager().beginTransaction().replace(R.id.fragment_rig_details, dstRigFragment).commit();
                        break;
                    case 6:
                        getFragmentManager().beginTransaction().replace(R.id.fragment_rig_details, sptRigFragment).commit();
                        break;
                    case 7:
                        getFragmentManager().beginTransaction().replace(R.id.fragment_rig_details, casedRigFragment).commit();
                        break;
                    default:
                        break;
                }
                Toast.makeText(getApplicationContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rig_info, menu);
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
