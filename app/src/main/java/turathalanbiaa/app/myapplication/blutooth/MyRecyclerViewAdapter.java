package turathalanbiaa.app.myapplication.blutooth;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import turathalanbiaa.app.myapplication.Model.SellMenuItem;
import turathalanbiaa.app.myapplication.R;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<String> mData;
    private List<SellMenuItem> itemList;
    private LayoutInflater mInflater;

    private ItemClickListener mClickListener;


    // data is passed into the constructor
//    MyRecyclerViewAdapter(Context context, List<String> data) {
//
//        this.mInflater = LayoutInflater.from(context);
//        this.mData = data;
//    }
    MyRecyclerViewAdapter(Context context, List<SellMenuItem> itemData) {

        this.mInflater = LayoutInflater.from(context);
        this.itemList = itemData;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_item, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row

//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position) {
//        String id = mData.get(position);
//
//        holder.itemId.setText(id);
//    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       SellMenuItem item=itemList.get(position);

//        holder.itemId.setText(item.getId());
        holder.itemName.setText(item.getItem_name());
        holder.itemPrice.setText(item.getItem_price().toString());
        holder.itemQuantity.setText(item.getItem_count().toString());
//        holder.textView.setText(item.getItem_name());

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return itemList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemId,itemName,itemPrice,itemQuantity;
        Button increaseQuantity,decreaseQuantity;
        public RelativeLayout viewBackground, viewForeground;

        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            itemId = itemView.findViewById(R.id.item_id);
            itemQuantity = itemView.findViewById(R.id.item_quantity);
            itemName = itemView.findViewById(R.id.item_name);
            itemPrice = itemView.findViewById(R.id.item_price);
            viewBackground=itemView.findViewById(R.id.view_background);
            viewForeground=itemView.findViewById(R.id.view_foreground);

            increaseQuantity=itemView.findViewById(R.id.increase_quantity);
            decreaseQuantity=itemView.findViewById(R.id.decrease_quantity);
//
//
            increaseQuantity.setOnClickListener(new View.OnClickListener() {
//
//            textView=itemView.findViewById(R.id.textView);
                @Override
                public void onClick(View view) {
//                    increaseQuantity.setBackgroundColor(Color.RED);

                    try{

                       int q= itemList.get(getAdapterPosition()).getItem_count();
                       q++;
                       itemList.get(getAdapterPosition()).setItem_count(q);
                        itemQuantity.setText(String.valueOf(q));

                    }
                    catch (Exception e){
                      itemName.setText(e.toString());
                    }


                }
            });

            decreaseQuantity.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
//

                    int q= itemList.get(getAdapterPosition()).getItem_count();
                    if(q>1)
                        q--;
                    itemList.get(getAdapterPosition()).setItem_count(q);
                    itemQuantity.setText(String.valueOf(q));




                }
            });

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
//    String getItem(int id) {
//        return mData.get(id);
//    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;

    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);

    }

    public void removeItem(int position) {
        itemList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(SellMenuItem item, int position) {
        itemList.add(position, item);
        // notify item added by position
        notifyItemInserted(position);
    }
}