package turathalanbiaa.app.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import turathalanbiaa.app.myapplication.Model.Item;
import turathalanbiaa.app.myapplication.SharedPrefrencesSession.SessionManager;

public class WebActivity extends AppCompatActivity {
    private WebView webView;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent i = getIntent();
        Integer type = i.getIntExtra("type",1);


        String user_name ;
        String user_id;
        Integer menu_id;

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        path = sharedPreferences.getString("server_path", "192.168.0.125");
        SessionManager session;
        session = new SessionManager(getApplicationContext());

        user_name = session.getshared("name");
        user_id = session.getshared("id");

        String url;

        if (type == 1)
        {
               url  ="http://" + path + "/user/" + user_id +"/"+user_name;

        }else
        {

            menu_id = i.getIntExtra("menu_id",1);
            url  ="http://" + path + "/user-edit-menu/" + menu_id +"/"+user_id+"/"+user_name;


        }




        webView  = new WebView(this);

        webView.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;

        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });


        webView.loadUrl(url);
        setContentView(webView );





    }
}
