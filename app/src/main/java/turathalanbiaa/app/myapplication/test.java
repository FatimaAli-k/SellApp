//package turathalanbiaa.app.myapplication;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.Toast;
//
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.VolleyLog;
//import com.android.volley.toolbox.JsonArrayRequest;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//
//import turathalanbiaa.app.myapplication.Model.SellMenuItem;
//import turathalanbiaa.app.myapplication.blutooth.MyRecyclerViewAdapter;
//import turathalanbiaa.app.myapplication.volley.AppController;
//
//public class test extends Fragment implements MyRecyclerViewAdapter.ItemClickListener {
//
//   LinearLayout Layout;
//    MyRecyclerViewAdapter adapter;
//    //    MyRecyclerViewAdapter adapter;
//    ArrayList<SellMenuItem> menuItems = new ArrayList<>();
//    private static String TAG = test.class.getSimpleName();
//    SellMenuItem item=new SellMenuItem();
//
//
//    // Progress dialog
////     ProgressDialog pDialog;
//
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_test, container,false);
//
//
//        String url="https://jsonblob.com/api/c343b7e4-34c8-11ea-ad35-d729c7db8fd8";
//        makeJsonArrayRequest(url);
//
//        RecyclerView recyclerView = view.findViewById(R.id.items_recycler_view);
//       Layout = view.findViewById(R.id.liner_layout);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        adapter= new MyRecyclerViewAdapter( getContext(), menuItems);
//        adapter.setClickListener(this);
//        recyclerView.setAdapter(adapter);
//
//
////        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
////        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
////
//
//
//
//
//
//        return view;
//    }
//
//
//
//    private void makeJsonArrayRequest(String url) {
////        showpDialog();
//
//        JsonArrayRequest req = new JsonArrayRequest(url,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.d(TAG, response.toString());
//
//                        try {
//                            // Parsing json array response
//                            // loop through each json object
//
//                            for (int i = 0; i < response.length(); i++) {
//
//                                JSONObject person = (JSONObject) response
//                                        .get(i);
//
//                                String name = person.getString("name");
//                                int price = person.getInt("price");
//
//
//                                item=new SellMenuItem();
//                                item.setItem_count(1);
//                                item.setItem_name(name);
//                                item.setItem_price(price);
//                                menuItems.add(item);
//
//
//                            }
//
//                            adapter.notifyDataSetChanged();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getContext(),
//                                    "Error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG).show();
//                        }
//
////                        hidepDialog();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getContext(),
//                        error.getMessage(), Toast.LENGTH_SHORT).show();
////                hidepDialog();
//            }
//        });
//
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(req);
//    }
////    private void showpDialog() {
////        if (!pDialog.isShowing())
////            pDialog.show();
////    }
////
////    private void hidepDialog() {
////        if (pDialog.isShowing())
////            pDialog.dismiss();
////    }
//
//    @Override
//    public void onItemClick(View view, int position) {
//        Toast.makeText(getContext(), "You clicked row number " + position, Toast.LENGTH_SHORT).show();
//    }
//
////    @Override
////    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
////        if (viewHolder instanceof MyRecyclerViewAdapter.ViewHolder) {
////            // get the removed item name to display it in snack bar
////            String name = menuItems.get(viewHolder.getAdapterPosition()).getItem_name();
////
////            // backup of removed item for undo purpose
////            final SellMenuItem deletedItem = menuItems.get(viewHolder.getAdapterPosition());
////            final int deletedIndex = viewHolder.getAdapterPosition();
////
////            // remove the item from recycler view
////            adapter.removeItem(viewHolder.getAdapterPosition());
////
////            // showing snack bar with Undo option
////            Snackbar snackbar = Snackbar
////                    .make(Layout, name + " removed from cart!", Snackbar.LENGTH_LONG);
////            snackbar.setAction("UNDO", new View.OnClickListener() {
////                @Override
////                public void onClick(View view) {
////
////                    // undo is selected, restore the deleted item
////                    adapter.restoreItem(deletedItem, deletedIndex);
////                }
////            });
////            snackbar.setActionTextColor(Color.YELLOW);
////            snackbar.show();
////        }
////    }
//}
