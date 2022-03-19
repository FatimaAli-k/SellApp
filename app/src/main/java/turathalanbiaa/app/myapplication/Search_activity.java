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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import turathalanbiaa.app.myapplication.Model.Item;
import turathalanbiaa.app.myapplication.Model.SellMenuItem;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Search_activity extends AppCompatActivity {
    EditText txt_search ;
Button btn_waiting;



    ArrayList<String> names ;
    ArrayList<Integer> ids ;
    String URLWaiting_menu ;
    ListView lv_customer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        turathalanbiaa.app.myapplication.ServerInfo ServerInfo = new ServerInfo(this);
        URLWaiting_menu = ServerInfo.getUrl("waiting_menu");
        names= new ArrayList<String>();
        ids= new ArrayList<Integer>();


        lv_customer = (ListView) findViewById(R.id.customers_listView);

        Custome_base_adabter custome_base_adabter = new Custome_base_adabter(getApplicationContext() , names , ids);
       lv_customer.setAdapter(custome_base_adabter);

       _listenters_func();

        getWaiting_menu_Array();
        lv_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


               Intent i = new Intent(getApplicationContext() , Main_Activity.class);
               i.putExtra("menu_id",ids.get(position));
               startActivity(i);

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











    private void getWaiting_menu_Array() {



        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URLWaiting_menu,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {
                        Log.d("karrar", "responsed ");
                        try {
                            //converting response to json object

                            JSONArray obj = new JSONArray(response);

                            names.clear();
                            ids.clear();
                            for (int i = 0; i < obj.length(); i++) {

                                JSONObject jsonItem = (JSONObject) obj
                                        .get(i);


                                int the_id = jsonItem.getInt("id");
                                String customer_name = jsonItem.getString("customer_name");
                                int customer_id = jsonItem.getInt("customer_id");

                                names.add(customer_name);
                                ids.add(the_id);

//                                String t = jsonItem.get(0);
//
//
//
//                                JSONArray obj2 = new JSONArray(t);
//
//                                for (int j = 0; j < obj2.length(); j++) {
//                                    JSONObject jsonItem2 = (JSONObject) obj2
//                                            .get(j);
//
////
//                                    Log.d("karrar", jsonItem2.getString("customer_name"));
//
//
//
//                                }

                            }
                            Custome_base_adabter custome_base_adabter = new Custome_base_adabter(getApplicationContext() , names , ids);
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