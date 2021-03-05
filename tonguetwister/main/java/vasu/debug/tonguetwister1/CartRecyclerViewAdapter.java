package vasu.debug.tonguetwister1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.CartItemViewHolder> {

    private final LinkedList<String> mWordList;
    private LayoutInflater mInflater;

    public CartRecyclerViewAdapter(Context context, LinkedList<String> mWordList) {
        this.mWordList = mWordList;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CartRecyclerViewAdapter.CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //for displaying element in Layout by oneItemLayout xml file
        View mItemView = mInflater.inflate(R.layout.cart_one_item_layout,parent,false);
        return new CartRecyclerViewAdapter.CartItemViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerViewAdapter.CartItemViewHolder holder, int position) {

        //for filling every occurrence of elements with required/useful information
        String mCurrent = mWordList.get(position);
        holder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView wordItemView;
        final CartRecyclerViewAdapter mAdapter;


        public CartItemViewHolder(@NonNull View itemView, CartRecyclerViewAdapter adapter) {
            super(itemView);

            wordItemView = itemView.findViewById(R.id.item_name);
            //mImageView = itemView.findViewById(R.id.myImageView);
            mAdapter = adapter;
            //set onClickListener after adapter
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();
            // Use that to access the affected item in mWordList.
            String element = mWordList.get(mPosition);
            // Change the word in the mWordList.
            mWordList.set(mPosition, "Clicked! " + element);
            // Notify the adapter, that the data has changed so it can
            // update the RecyclerView to display the data.
            mAdapter.notifyDataSetChanged();
        }
    }
}
