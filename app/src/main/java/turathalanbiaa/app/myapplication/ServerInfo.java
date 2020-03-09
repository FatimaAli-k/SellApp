package turathalanbiaa.app.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class ServerInfo {
    private String path;
    private String url;
    private String route;
    private Context context;

    public ServerInfo(Context context) {
        this.context = context;
    }

    public String getUrl(String route) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        this.path = sharedPreferences.getString("server_path", "192.168.0.125");
        this.url = "http://" + this.path +"/api/"+ route;
        return url;
    }

}
