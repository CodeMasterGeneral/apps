package vasu.debug.tonguetwister1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class WordListAdapterCopy extends RecyclerView.Adapter<WordListAdapterCopy.WordViewHolder> {


    private final LinkedList<String> mWordList;
    private LayoutInflater mInflater;

    public WordListAdapterCopy(Context context, LinkedList<String> mWordList) {
        this.mWordList = mWordList;
        mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public WordListAdapterCopy.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //for displaying element in Layout by oneItemLayout xml file
        View mItemView = mInflater.inflate(R.layout.one_menu_item_all_options_layout,parent,false);
        return new WordViewHolder(mItemView,this);
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapterCopy.WordViewHolder holder, int position) {

        //for filling every occurrence of elements with required/useful information
        String mCurrent = mWordList.get(position);
        holder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }

    public class WordViewHolder extends RecyclerView.ViewHolder {

        public final TextView wordItemView;
        final WordListAdapterCopy mAdapter;

        public WordViewHolder(@NonNull View itemView, WordListAdapterCopy adapter) {
            super(itemView);

            wordItemView = itemView.findViewById(R.id.menu_item_id);
            //mImageView = itemView.findViewById(R.id.myImageView);
            mAdapter = adapter;
        }
    }
}
