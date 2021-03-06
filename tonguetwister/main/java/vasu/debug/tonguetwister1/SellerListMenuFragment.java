package vasu.debug.tonguetwister1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;

public class SellerListMenuFragment extends Fragment {


    //in recycler view
    //word list adapter,one item menu all frag,menu list adapter,


    //recycler view vars
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;
    private final LinkedList<String> mWordList = new LinkedList<>();


    public SellerListMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_seller_list_menu, container, false);
        View view = inflater.inflate(R.layout.fragment_seller_list_menu, container, false);

        // Put initial data into the word list.
        for (int i = 0; i < 20; i++) {
            mWordList.addLast("menu item " + i);
        }
        mRecyclerView = view.findViewById(R.id.seller_menu_list_recyclerView);
        mAdapter = new WordListAdapter(view.getContext(), mWordList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//        GridLayoutManager mGridLayoutMgr;
//        int mSpanCount = 3;
//        mGridLayoutMgr = new GridLayoutManager( getActivity(), mSpanCount, GridLayoutManager.VERTICAL, false);
//        mRecyclerView.setLayoutManager(mGridLayoutMgr);

        // Inflate the layout for this fragment
        return view;
    }
}