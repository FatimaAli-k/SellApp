package turathalanbiaa.app.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import turathalanbiaa.app.myapplication.Model.TheMenu;
import turathalanbiaa.app.myapplication.SharedPrefrencesSession.SessionManager;
import turathalanbiaa.app.myapplication.blutooth.Main_Activity;
import turathalanbiaa.app.myapplication.volley.AppController;

public class Custome_base_adabter extends BaseAdapter {

    Context context;
    LayoutInflater inflater ;
    ArrayList<TheMenu> AllMenus;
    public Custome_base_adabter(Context ctx , ArrayList<TheMenu> AllMenus)
    {
        this.context = ctx;
        this.AllMenus= AllMenus ;

        inflater = LayoutInflater.from(ctx);


    }
    @Override
    public int getCount() {
        return AllMenus.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.activity_customer_item_for_listview , null );
        final Integer[] sellMenuId = new Integer[1];
        TextView  name = (TextView) convertView.findViewById(R.id.item_name);
        TextView  id = (TextView) convertView.findViewById(R.id.item_id);
        TextView  customer_id = (TextView) convertView.findViewById(R.id.customer_id);

        Button btn_new= (Button)  convertView.findViewById(R.id.btn_new);

        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                turathalanbiaa.app.myapplication.ServerInfo ServerInfo = new ServerInfo(v.getContext());

                String userNewMenuURL = ServerInfo.getUrl("user_new_sell_menu");
//--------------------------------------------------------------------
                userNewMenuURL= userNewMenuURL + "/" + AllMenus.get(position).getCustomer_id().toString();

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        userNewMenuURL, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            // Parsing json object response
                            // response will be a json object


                            Intent i = new Intent(context, Main_Activity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            i.putExtra("new_menu_id",response.getInt("id"));
                            v.getContext().startActivity(i);



                        } catch (JSONException e) {
                            e.printStackTrace();


                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(jsonObjReq);



                //____________________________________

            }
        });

        name.setText(AllMenus.get(position).getCustomer_name());
        customer_id.setText(AllMenus.get(position).getCustomer_id().toString());
        id.setText(AllMenus.get(position).getMenu_id().toString());




        Button btn_menus = (Button) convertView.findViewById(R.id.btn_menus);

        btn_menus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent i = new Intent(context,WebActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("type", 4);
                i.putExtra("customer_id",AllMenus.get(position).getCustomer_id());
                v.getContext().startActivity(i);

            }
        });



        return convertView;
    }

    private void msg(int id) {


    }
}
