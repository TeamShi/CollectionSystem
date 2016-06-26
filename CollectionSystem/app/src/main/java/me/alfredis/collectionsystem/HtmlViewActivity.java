package me.alfredis.collectionsystem;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class HtmlViewActivity extends AppCompatActivity implements View.OnClickListener {
    private WebView webView;

    private Button nextRigButton;

    private Button prevRigButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_view);
        webView = (WebView) findViewById(R.id.table_preview_web_view);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.loadUrl(getIntent().getStringExtra("table_path"));
//        webView.loadData("<html><body>You scored <b>192</b> points.</body></html>","text/html", null);
        nextRigButton = (Button) findViewById(R.id.button_next_rig);
        prevRigButton = (Button) findViewById(R.id.button_previous_rig);
        nextRigButton.setEnabled(true);
        prevRigButton.setEnabled(true);
        nextRigButton.setOnClickListener(this);
        prevRigButton.setOnClickListener(this);

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                // Here the String url hold 'Clicked URL'
                return false;
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_html_view, menu);
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
        switch (view.getId()) {
            case R.id.button_previous_rig:
                String url = webView.getUrl();
                int start = url.lastIndexOf("_") + 1;
                int index = Integer.parseInt(url.substring(start, start + 1));
                if (index == 0) {
                    Toast.makeText(getApplicationContext(), "已经是第一个页面！", Toast.LENGTH_SHORT).show();
                    return;
                }
                url = url.substring(0, start) + (--index) + ".html";
                webView.loadUrl(url);
                break;
            case R.id.button_next_rig:
                url = webView.getUrl();
                File file = null;
                try {
                    file = (new File(new URL(url).toURI()));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                int max = file.getParentFile().listFiles().length - 1;
                start = url.lastIndexOf("_") + 1;
                index = Integer.parseInt(url.substring(start, start + 1));
                if (index == max) {
                    Toast.makeText(getApplicationContext(), "已经是最后一个页面！", Toast.LENGTH_SHORT).show();
                    return;
                }
                url = url.substring(0, start) + (++index) + ".html";
                webView.loadUrl(url);
                break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "HtmlView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://me.alfredis.collectionsystem/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "HtmlView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://me.alfredis.collectionsystem/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
