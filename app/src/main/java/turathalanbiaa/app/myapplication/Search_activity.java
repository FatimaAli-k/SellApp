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


    String names[] = {"ddd","rtrete","ddd","كرار حساني","ddd","ddd","ddd","ddd","ddd","ddd","ddd","ddd"};
    Integer ids[] = {71270,71271,71272,71273,71274,71275,71276,71283,71284,71285,71286,71287};

    ListView lv_customer ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



        lv_customer = (ListView) findViewById(R.id.customers_listView);

        Custome_base_adabter custome_base_adabter = new Custome_base_adabter(getApplicationContext() , names , ids);
       lv_customer.setAdapter(custome_base_adabter);


        lv_customer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


               Intent i = new Intent(getApplicationContext() , Main_Activity.class);
               i.putExtra("menu_id",ids[position]);
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

    private void msg() {
        Toast.makeText(this,txt_search.getText() ,Toast.LENGTH_SHORT).show();

    }
    private void msg(String s) {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();

    }

    private void open_sell() {
        this.finish();
    }


//    private void getSellMenuCustomersArray() {
//
//
//        Log.d("karrar", "start ");
//        final StringRequest stringRequest = new StringRequest(Request.Method.POST, getOldMenuURL,
//                new Response.Listener<String>() {
//
//
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d("karrar", "responsed ");
//                        try {
//                            //converting response to json object
//
//                            JSONArray obj = new JSONArray(response);
//
//
//                            for (int i = 0; i < obj.length(); i++) {
//
//                                JSONObject jsonItem = (JSONObject) obj
//                                        .get(i);
//
//                                String t = jsonItem.getString("sell_menu_item");
//
//                                JSONArray obj2 = new JSONArray(t);
//
//                                for (int j = 0; j < obj2.length(); j++) {
//                                    JSONObject jsonItem2 = (JSONObject) obj2
//                                            .get(j);
//
////
//                                    Log.d("karrar", jsonItem2.getString("item_name"));
//                                    int id = jsonItem2.getInt("id");
//                                    String name = jsonItem2.getString("item_name");
//                                    int price = jsonItem2.getInt("item_price");
//                                    int count = jsonItem2.getInt("item_count");
//                                    int itemid = jsonItem2.getInt("item_id");
//
//                                    String f4 = jsonItem2.getString("f4");
//
//
//                                    iteminfo = new Item();
//                                    itemArrayList.add(iteminfo);
//
//
//                                    SellItem = new SellMenuItem();
//                                    SellItem.setItem_count(count);
//                                    SellItem.setId(id);
//                                    SellItem.setItem_name(name);
//                                    SellItem.setItem_price(price);
//                                    SellItem.setItem_id(itemid);
//                                    //details
//
//                                    SellItem.setF4(f4);
//                                    menuItems.add(SellItem);
//                                }
//
//                            }
//
////
////
//                            adapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            Toast.makeText(getApplicationContext(),
//                                    " // " + e, Toast.LENGTH_LONG).show();
//                        }
//                        hidepDialog();
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                        hidepDialog();
//                    }
//                }) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("id", sellMenuId);
//
//                params.put("user_sell_it_id", session.getshared("id"));
//
//                return params;
//            }
//        };
//
//        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
//
//    }
//
//
//    private void getIcostomerObj(String url) {
//        Map<String, String> params = new HashMap<>();
//        params.put("barcode", itemCode);
//
//        showpDialog();
//
//        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
//                url, new JSONObject(params), new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                Log.d(TAG, response.toString());
//
//                try {
//                    JSONObject jsonItem = (JSONObject) response;
//                    int id = jsonItem.getInt("id");
//                    String name = jsonItem.getString("name");
//                    int price = jsonItem.getInt("price");
//                    String details = jsonItem.getString("detail");
//
//                    //     int cost = jsonItem.getInt("cost");
//                    String place = jsonItem.getString("place");
//                    int store_id = jsonItem.getInt("store_id");
//                    int storageCount = jsonItem.getInt("count");
//
//                    iteminfo = new Item();
//                    iteminfo.setPlace(place);
//                    iteminfo.setStore_id(store_id);
//                    //   iteminfo.setCost(cost);
//                    iteminfo.setCount(storageCount);
//                    itemArrayList.add(iteminfo);
//
//
//                    SellItem = new SellMenuItem();
//                    SellItem.setItem_count(1);
//                    SellItem.setItem_name(name);
//                    SellItem.setItem_price(price);
//                    SellItem.setItem_id(id);
//                    //detail
//                    if ((!details.equalsIgnoreCase("null")) && (details != null) && (!details.equals(""))) {
//                        SellItem.setF1(details);
//                        getItemDetails(details, SellItem);
//
//                    } else {
//                        menuItems.add(SellItem);
//
//                        adapter.notifyDataSetChanged();
//                    }
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(),
//                            "لم يتم الكشف عن باركود",
//                            Toast.LENGTH_LONG).show();
//                }
//
//                hidepDialog();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
////                VolleyLog.d(TAG, "لم يتم الكشف عن باركود" + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        "لم يتم الكشف عن باركود", Toast.LENGTH_SHORT).show();
//                hidepDialog();
//            }
//
//        });
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(req);
//
//    }

}