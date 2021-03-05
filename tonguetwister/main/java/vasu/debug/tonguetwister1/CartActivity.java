package vasu.debug.tonguetwister1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.LinkedList;

public class CartActivity extends AppCompatActivity {

    //    ------google info-----------------------
    ImageView userImage;
    TextView usernametv, userInfo;
//    ------google info---xxx--------------------






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //---GOOGLE SIGN IN -----------------------------------------------
        //for putting info into navDrawer Header
        NavigationView navDrawerView = findViewById(R.id.navDrawerView);//used in NavigationDrawer as well down below
        View headerView = navDrawerView.getHeaderView(0);

        usernametv = headerView.findViewById(R.id.user_name);
        userInfo = headerView.findViewById(R.id.user_info_email_phone);
        userImage = headerView.findViewById(R.id.user_imageView);
        //******_______logout btn operation present in onOptionsItemSelected() function____DO not delete___________***********

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            usernametv.setText(account.getDisplayName());
            userInfo.setText(account.getEmail());
            Glide.with(this).load(account.getPhotoUrl()).into(userImage);
        }

        //---GOOGLE SIGN IN -----------xxx----------------------

        //------APP-BAR ops
        Toolbar cart_toolbar = findViewById(R.id.cart_main_toolbar);
        //        cart_toolbar.setTitle("");
        setSupportActionBar(cart_toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.my_cart);

        //color white the triple dots/menu dots on app bar
        Drawable drawable = cart_toolbar.getOverflowIcon();
        if(drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), getResources().getColor(R.color.white,getTheme()));
            cart_toolbar.setOverflowIcon(drawable);
        }
        //appbar ops xxx

        //----------NAVIGATION drawer-------------------
        //navDrawerHeader,navDrawermenu , activity userdashboard xml--> has drawer layout,activity sellerdashboard xml--> has seller drawer layout
        //nav drawer--------------------------------------------
        DrawerLayout cart_drawerLayout = findViewById(R.id.cart_drawer_layout);
        ActionBarDrawerToggle navDrawerToggle = new ActionBarDrawerToggle(CartActivity.this, cart_drawerLayout, cart_toolbar,R.string.open,R.string.close);
        if(cart_drawerLayout!=null){
            cart_drawerLayout.addDrawerListener(navDrawerToggle);
        }
        navDrawerToggle.syncState();
        cart_toolbar.setNavigationIcon(R.drawable.ic_nav_menu);//change navbar hamburger-->white color


        //ACCESS to navDrawer via id is required [ NavigationView navDrawerView = findViewById(R.id.navDrawerView);]. it was accesses above. so using directly.
        if(navDrawerView !=null){
            //removing user dashboard option in navigation drawer----this is user dashboard activity
            Menu navMenu = navDrawerView.getMenu();
            navMenu.findItem(R.id.user_dashboard_inNavMenu).setVisible(false);
            navMenu.findItem(R.id.seller_dashboard_inNavMenu).setVisible(false);

            //navDrawerView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
            navDrawerView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.order_history_inNavMenu:
                            break;
                        case R.id.menu_inNavMenu:
                            //fragment = new MenuFragment();
                            break;
                    }//switch xxx------
                    return true;
                }//onNavItemSelected xxx----------
            });

        }//if loop xxx

        //---------nav drawer xxx----------------

        //----------recyclerview------------------------

        //recycler view vars
        RecyclerView mRecyclerView;
        CartRecyclerViewAdapter mAdapter;
        final LinkedList<String> mWordList = new LinkedList<>();

        // Put initial data into the word list.
        for (int i = 0; i < 20; i++) {
            mWordList.addLast("menu item " + i);
        }
        mRecyclerView = findViewById(R.id.cart_recyclerView);
        mAdapter = new CartRecyclerViewAdapter(this,mWordList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        GridLayoutManager mGridLayoutMgr;
//        int mSpanCount = 3;
//        mGridLayoutMgr = new GridLayoutManager( getActivity(), mSpanCount, GridLayoutManager.VERTICAL, false);
//        mRecyclerView.setLayoutManager(mGridLayoutMgr);

        //----------recyclerview-----xxx-------------------

        //------snackbar------------------------------
        DrawerLayout drawerLayout;
        drawerLayout = findViewById(R.id.cart_drawer_layout);
        showSnackbar(drawerLayout);


    }     //onCreate XXX----------------

    private void showSnackbar(DrawerLayout drawerLayout) {
        Snackbar snackbar = Snackbar
                .make(drawerLayout, "yay! all set. click proceed to pay \nand place your order", Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.proceed, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(CartActivity.this,UserDashboard.class));
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.snackbar_green,getTheme()));
        snackbar.show();
    }

//    @Override
//    public void onBackPressed() {
//        if(cart_drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            cart_drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        menu.findItem(R.id.user_dashboard_inOptionsMenu).setVisible(false);
        menu.findItem(R.id.seller_dashboard_inOptionsMenu).setVisible(false);
        menu.findItem(R.id.seller_dashboard_inOptionsMenu).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.order_history_inOptionsMenu:
            case R.id.about_inOptionsMenu:
            case R.id.settings_inOptionsMenu:
                break;

            case R.id.logout_btn_inOptionMenu: {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(CartActivity.this,MainActivity.class));
                finish(); }
            break;


        }


        return super.onOptionsItemSelected(item);
    }


}