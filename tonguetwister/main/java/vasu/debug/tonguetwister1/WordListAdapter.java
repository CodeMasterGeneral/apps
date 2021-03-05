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
/*
 RecyclerView.Adapter connects your data to the RecyclerView.
 It prepares the data in a RecyclerView.ViewHolder.
 You will create an adapter that inserts into and updates your generated words in your views.
*/
public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordViewHolder> {

    private final LinkedList<String> mWordList;
    private LayoutInflater mInflater;

    public WordListAdapter(Context context,  LinkedList<String> mWordList) {
        this.mWordList = mWordList;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //for displaying element in Layout by oneItemLayout xml file
        View mItemView = mInflater.inflate(R.layout.one_menu_item_all_options_layout,parent,false);
        return new WordViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.WordViewHolder holder, int position) {
        try{
        //for filling every occurrence of elements with required/useful information
        String mCurrent = mWordList.get(position);
        holder.wordItemView.setText(mCurrent);
    }catch (Exception e){
            Log.e("debugTAG","no more items to display");
        }
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }


    //    A ViewHolder.
    //    Inside your adapter, you will create a ViewHolder that contains the View information
    //    for displaying one item from the item's layout.
    // implement view.onclickListener for adding onClick method
    class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView wordItemView;
        final WordListAdapter mAdapter;

        public WordViewHolder(@NonNull View itemView, WordListAdapter adapter) {
            super(itemView);//public WordViewHolder(@NonNull View itemView,WordListAdapter adapter) {

            wordItemView = itemView.findViewById(R.id.menu_item_id);
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
