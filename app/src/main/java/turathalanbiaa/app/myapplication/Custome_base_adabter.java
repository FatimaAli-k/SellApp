package turathalanbiaa.app.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import turathalanbiaa.app.myapplication.SharedPrefrencesSession.SessionManager;

public class Custome_base_adabter extends BaseAdapter {

    Context context;
    String names[] ;
    Integer  ids[];
    LayoutInflater inflater ;

    public Custome_base_adabter(Context ctx ,String [] names,Integer [] ids)
    {
        this.context = ctx;
        this.ids= ids ;
        this.names= names;
        inflater = LayoutInflater.from(ctx);


    }
    @Override
    public int getCount() {
        return names.length;
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

        TextView  name = (TextView) convertView.findViewById(R.id.item_name);
        TextView  id = (TextView) convertView.findViewById(R.id.item_id);

        Button btn_new= (Button)  convertView.findViewById(R.id.btn_new);

        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("btn in listview already works!!" + ids[position]);
            }
        });
        Button btn_menus= (Button)  convertView.findViewById(R.id.btn_menus);

        btn_menus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context,WebActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("type", 2);
                i.putExtra("menu_id", ids[position]);
                v.getContext().startActivity(i);

            }
        });


        name.setText(names[position]);
        id.setText(ids[position].toString());

        return convertView;
    }
}
