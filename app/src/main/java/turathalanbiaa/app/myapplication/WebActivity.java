package turathalanbiaa.app.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import turathalanbiaa.app.myapplication.Model.Item;
import turathalanbiaa.app.myapplication.SharedPrefrencesSession.SessionManager;
import turathalanbiaa.app.myapplication.blutooth.Main_Activity;

public class WebActivity extends AppCompatActivity {
    private String path;
    WebView web_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);



        web_view = findViewById(R.id.web1);
        web_view.requestFocus();
        web_view.getSettings().setLightTouchEnabled(true);
        web_view.getSettings().setJavaScriptEnabled(true);
        web_view.getSettings().setGeolocationEnabled(true);
        web_view.setSoundEffectsEnabled(true);
        web_view.getSettings().setAppCacheEnabled(true);


        show_menu();




    }

    private void show_menu() {
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

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("انتظر قليلا...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        web_view.loadUrl(url);
        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.hide();

                super.onPageFinished(view, url);

            }

        });
    }


}
