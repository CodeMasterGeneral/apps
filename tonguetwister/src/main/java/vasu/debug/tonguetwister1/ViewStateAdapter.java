package vasu.debug.tonguetwister1;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

//for fragments arranging in TABS
public class ViewStateAdapter extends FragmentStateAdapter {
    int mTotalTabFrag;
    String myClassName;

    public ViewStateAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int mTotalTabs,String classname) {
        super(fragmentManager, lifecycle);
        this.mTotalTabFrag = mTotalTabs;
        this.myClassName=classname.toString();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if(myClassName.equals("UserDashboard")){
            switch(position){
                case 0: return new MenuFragment();
                case 1: return new BestsellersFragment();
                case 2: return new OffersFragment();
                default: return new DefaultTabFragment();
            }
        }else if(myClassName.equals("SellerDashboard")){
            switch(position){
                case 0: return new SellerOrdersFrag();
                case 1: return new QuantityUpdateFragment();
                case 2:case 3: return new SellerListMenuFragment();
                default: return new DefaultTabFragment();
            }
        }else{
            return new DefaultTabFragment();
        }


    }

    @Override
    public int getItemCount() {
        return mTotalTabFrag;
    }
}
