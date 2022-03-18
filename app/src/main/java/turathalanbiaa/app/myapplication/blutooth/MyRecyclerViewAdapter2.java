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

import turathalanbiaa.app.myapplication.Model.Item;
import turathalanbiaa.app.myapplication.Model.SellMenuItem;
import turathalanbiaa.app.myapplication.R;

public class MyRecyclerViewAdapter2 extends RecyclerView.Adapter<MyRecyclerViewAdapter2.ViewHolder> {

    private List<Item> itemList;
    private List<SellMenuItem> sellMenuItemList;
    private LayoutInflater mInflater;

    private ItemClickListener mClickListener;


    // data is passed into the constructor
//    MyRecyclerViewAdapter(Context context, List<String> data) {
//
//        this.mInflater = LayoutInflater.from(context);
//        this.mData = data;
//    }
    MyRecyclerViewAdapter2(Context context, List<SellMenuItem> sellMenuItems, List<Item> itemList) {

        this.mInflater = LayoutInflater.from(context);
        this.sellMenuItemList = sellMenuItems;
        this.itemList=itemList;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_customer, parent, false);

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
       SellMenuItem item= sellMenuItemList.get(position);

       try {
           if (itemList.size() != 0) {
               Item itemInfo = itemList.get(position);
               holder.itemPlace.setText(itemInfo.getPlace());
               holder.itemStoreid.setText(itemInfo.getStore_id());
               holder.itemStorageCount.setText(itemInfo.getCount());

           }
       }catch (Exception e){

       }

//        holder.itemId.setText(SellItem.getId());
        holder.itemName.setText(item.getItem_name());
        holder.itemPrice.setText( String.format("%,d", Long.parseLong(item.getItem_price().toString())));
        holder.itemQuantity.setText(item.getItem_count().toString());

            if((item.getF4()!=null)&&(!item.getF4().equalsIgnoreCase("null"))&&(!item.getF4().equals(""))) {
                holder.itemDetail.setText(item.getF4());
                holder.itemDetail.setVisibility(View.VISIBLE);
            }

        else {
            holder.itemDetail.setText("");
            holder.itemDetail.setVisibility(View.INVISIBLE);
//        holder.textView.setText(SellItem.getItem_name());
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return sellMenuItemList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView itemId,itemName,itemPrice,itemQuantity,itemDetail;
        Button increaseQuantity,decreaseQuantity;
        public RelativeLayout viewBackground, viewForeground;

        TextView itemPlace,itemStoreid,itemStorageCount;

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

            itemDetail=itemView.findViewById(R.id.item_details);

            itemStorageCount=itemView.findViewById(R.id.item_storage_count);
            itemStoreid=itemView.findViewById(R.id.item_store_id);
            itemPlace=itemView.findViewById(R.id.item_place);

            increaseQuantity.setOnClickListener(new View.OnClickListener() {
//
//            textView=itemView.findViewById(R.id.textView);
                @Override
                public void onClick(View view) {
//                    increaseQuantity.setBackgroundColor(Color.RED);

                    try{

                       int q= sellMenuItemList.get(getAdapterPosition()).getItem_count();
                       q++;
                       sellMenuItemList.get(getAdapterPosition()).setItem_count(q);
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

                    int q= sellMenuItemList.get(getAdapterPosition()).getItem_count();
                    if(q>1)
                        q--;
                    sellMenuItemList.get(getAdapterPosition()).setItem_count(q);
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
        sellMenuItemList.remove(position);
        itemList.remove(position);
        // notify the SellItem removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }

    public void restoreItem(SellMenuItem item, int position) {
        sellMenuItemList.add(position, item);

        // notify SellItem added by position
        notifyItemInserted(position);
    }
}