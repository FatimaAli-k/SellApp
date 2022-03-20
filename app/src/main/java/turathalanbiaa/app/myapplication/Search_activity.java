package turathalanbiaa.app.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.net.InetSocketAddress;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import turathalanbiaa.app.myapplication.Model.Item;
import turathalanbiaa.app.myapplication.Model.SellMenuItem;
import turathalanbiaa.app.myapplication.Model.TheMenu;
import turathalanbiaa.app.myapplication.blutooth.Main_Activity;
import turathalanbiaa.app.myapplication.blutooth.MyRecyclerViewAdapter;
import turathalanbiaa.app.myapplication.volley.AppController;
import turathalanbiaa.app.myapplication.volley.VolleySingleton;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Search_activity extends AppCompatActivity {
    EditText txt_search ;
Button btn_waiting;

    String userNewMenuURL;


    ArrayList<TheMenu> AllMenus ;

    String URLWaiting_menu ;
    String URLSearchName_menu ;
    ListView lv_customer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        turathalanbiaa.app.myapplication.ServerInfo ServerInfo = new ServerInfo(this);
        URLWaiting_menu = ServerInfo.getUrl("waiting_menu");
        URLSearchName_menu = ServerInfo.getUrl("search_name_menu");
        userNewMenuURL = ServerInfo.getUrl("user_new_sell_menu");
//        names= new ArrayList<String>();
//        ids= new ArrayList<Integer>();
        AllMenus= new ArrayList<TheMenu>();


        lv_customer = (ListView) findViewById(R.id.customers_listView);

        Custome_base_adabter custome_base_adabter = new Custome_base_adabter(getApplicationContext() ,AllMenus);
       lv_customer.setAdapter(custome_base_adabter);

       _listenters_func();

        getWaiting_menu_Array();
        lv_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(AllMenus.get(position).getStatus() == 0)
                {
                    Intent i = new Intent(getApplicationContext() , Main_Activity.class);
                    i.putExtra("menu_id",AllMenus.get(position).getMenu_id());
                    startActivity(i);
                }else
                {
                    Toast.makeText(getApplicationContext(),"لايمكن الاضافة-القائمة متممه" ,Toast.LENGTH_LONG).show();
                }


            }
        });


        Button btn_sell = (Button) findViewById(R.id.btn_sell);
        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open_sell();

            }
        });
         txt_search = (EditText) findViewById(R.id.txt_search);
        txt_search.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,int count) {
                if(!s.equals("") ) {

                    AllMenus.clear();
                    search_name_Array(txt_search.getText().toString());


                 }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {

            }

            public void afterTextChanged(Editable s) {


            }
        });

    }


    private void _listenters_func() {
         btn_waiting= (Button) findViewById(R.id.btn_waiting);
btn_waiting.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AllMenus.clear();
        EditText txt_search = (EditText) findViewById(R.id.txt_search);
        txt_search.setText("");
        getWaiting_menu_Array();
    }
});



    }

    private void msg() {
        Toast.makeText(this,txt_search.getText() ,Toast.LENGTH_SHORT).show();

    }
    private void msg(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();

    }

    private void open_sell() {
        this.finish();
    }





    private void search_name_Array(final String name) {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URLSearchName_menu,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        Log.d("karrar", "responsed ");
                        try {
                            //converting response to json object

                            JSONArray obj = new JSONArray(response);

                            for (int i = 0; i < obj.length(); i++) {

                                JSONObject jsonItem = (JSONObject) obj
                                        .get(i);


                                int the_id = jsonItem.getInt("id");
                                String customer_name = jsonItem.getString("customer_name");
                                int customer_id = jsonItem.getInt("customer_id");
                                int status = jsonItem.getInt("status");
                                int discount = jsonItem.getInt("discount");

                                TheMenu theMenu = new TheMenu(the_id,customer_id,status,discount,customer_name);
                                AllMenus.add(theMenu);


                            }
                            Custome_base_adabter custome_base_adabter = new Custome_base_adabter(getApplicationContext() , AllMenus );
                            lv_customer.setAdapter(custome_base_adabter);
                            //

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),
                                    " // " + e, Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }







    private void getWaiting_menu_Array() {



        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URLWaiting_menu,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        Log.d("karrar", "responsed ");
                        try {
                            //converting response to json object

                            JSONArray obj = new JSONArray(response);

//                            names.clear();
//                            ids.clear();
                            for (int i = 0; i < obj.length(); i++) {

                                JSONObject jsonItem = (JSONObject) obj
                                        .get(i);


                                int the_id = jsonItem.getInt("id");
                                String customer_name = jsonItem.getString("customer_name");
                                int customer_id = jsonItem.getInt("customer_id");
                                int status = jsonItem.getInt("status");
                                int discount = jsonItem.getInt("discount");

                                TheMenu theMenu = new TheMenu(the_id,customer_id,status,discount,customer_name);
                                AllMenus.add(theMenu);
                            }
                            Custome_base_adabter custome_base_adabter = new Custome_base_adabter(getApplicationContext() , AllMenus );
                            lv_customer.setAdapter(custome_base_adabter);

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),
                                    " // " + e, Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("id", sellMenuId);
//
//                params.put("user_sell_it_id", session.getshared("id"));
//
//                return params;
//            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }



}