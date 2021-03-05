package vasu.debug.tonguetwister1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

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
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class SellerDashboard extends AppCompatActivity {

    ViewPager2 viewPager2;
    int mTotalTabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);

        //---GOOGLE SIGN IN -----------------------------------------------
        //google sign in vars
            ImageView userImage;
            TextView usernametv, userInfo;

        //for putting info into navDrawer Header
        NavigationView navDrawerView = findViewById(R.id.seller_navDrawerView);//used in NavigationDrawer as well down below
        View headerView = navDrawerView.getHeaderView(0);

        usernametv = headerView.findViewById(R.id.user_name);
        userInfo = headerView.findViewById(R.id.user_info_email_phone);
        userImage = headerView.findViewById(R.id.user_imageView);

//        testImage = findViewById(R.id.testUserImageView);
//        testName = findViewById(R.id.testUserName);
//        testInfo = findViewById(R.id.testUserInfo);

        //******_______logout btn operation present in onOptionsItemSelected() function____DO not delete___________***********

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null){
            usernametv.setText(account.getDisplayName());
            userInfo.setText(account.getEmail());
            Glide.with(this).load(account.getPhotoUrl()).into(userImage);
        }

        //for test
//        testName.setText(account.getDisplayName());
//        testInfo.setText(account.getEmail());
//        Glide.with(this).load(account.getPhotoUrl()).into(testImage);

        //---GOOGLE SIGN IN -----------xxx----------------------


        //------APP-BAR ops
        Toolbar seller_toolbar = findViewById(R.id.seller_main_toolbar);
        //        seller_toolbar.setTitle("");
        setSupportActionBar(seller_toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
        //actionBar.setIcon(R.drawable.ic_cart);//for company title
        //        actionBar.show();
        //        actionBar.hide();

        //color white the triple dots/menu dots on app bar
        Drawable drawable = seller_toolbar.getOverflowIcon();
        if(drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), getResources().getColor(R.color.white,getTheme()));
            seller_toolbar.setOverflowIcon(drawable);
        }
        //appbar ops xxx
        
 //----------NAVIGATION drawer-------------------
        //navDrawerHeader,navDrawermenu , activity userdashboard xml--> has drawer layout,activity sellerdashboard xml--> has seller drawer layout
        //nav drawer--------------------------------------------

        DrawerLayout drawerLayout = findViewById(R.id.seller_drawer_layout);
        ActionBarDrawerToggle navDrawerToggle = new ActionBarDrawerToggle(SellerDashboard.this, drawerLayout, seller_toolbar,R.string.open,R.string.close);

        if(drawerLayout!=null){
            drawerLayout.addDrawerListener(navDrawerToggle);
        }
        navDrawerToggle.syncState();
        seller_toolbar.setNavigationIcon(R.drawable.ic_nav_menu);//change navbar hamburger-->white color

        //ACCESS to navDrawer via id is required [ NavigationView navDrawerView = findViewById(R.id.navDrawerView);]. it was accesses above. so using directly.
        if(navDrawerView !=null){

            //removing option in navigation drawer for sellerDashboard----this is seller dashboard activity
            Menu navMenu = navDrawerView.getMenu();
                navMenu.findItem(R.id.seller_dashboard_inNavMenu).setVisible(false);

            //navDrawerView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
            navDrawerView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()){

                            case R.id.user_dashboard_inNavMenu: startActivity(new Intent(getApplicationContext(),UserDashboard.class));break;
                            case R.id.edit_menu_inNavMenu: startActivity(new Intent(getApplicationContext(),EditMenu.class));break;
                            case R.id.seller_settings_inNavMenu: startActivity(new Intent(getApplicationContext(),SellerSettings.class));break;

                        }//switch xxx------
                        return true;
                    }//onNavItemSelected xxx----------
                });
            }//if loop xxx

        //---------nav drawer xxx----------------

////THREAD 1=============================================================================================================
//        Thread thread1,thread2 = null;
//        thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
////thread code start---------------


//TABS--------------------------------------------------------
        //viewsateadaper,offerFrag,menuFrag,bestsellerFrag,defaultFrag
        TabLayout tabLayout = findViewById(R.id.seller_tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("shop menu"));
        tabLayout.addTab(tabLayout.newTab().setText("item update"));
        tabLayout.addTab(tabLayout.newTab().setText("groceries"));
        tabLayout.addTab(tabLayout.newTab().setText("organic"));
        tabLayout.addTab(tabLayout.newTab().setText("organic2"));
        tabLayout.addTab(tabLayout.newTab().setText("packaging"));

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        mTotalTabs = tabLayout.getTabCount();

        //viewpager2--------------
        viewPager2 = findViewById(R.id.seller_view_pager2);

//        List<String> list = new ArrayList<>();
//        list.add("First Screen");
//        list.add("Second Screen");
//        list.add("Third Screen");
//        list.add("Fourth Screen");


        //for listening page change/tab clicks
        FragmentManager fm = getSupportFragmentManager();
        String classname = SellerDashboard.class.getSimpleName();
        ViewStateAdapter sa = new ViewStateAdapter(fm, getLifecycle(), mTotalTabs,classname);
        final ViewPager2 pa = viewPager2;
        pa.setAdapter(sa);

        //Change Tab when swiping****For Swiping Gesture
        //Finally we couple back when the user swipes the fragment to set the correct tab item as selected
        pa.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        //used for tab click on tablayout TOP BAR ****For Clicks
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());//important step
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//                //thread xxx----------
//            }
//        });
//
//        thread1.start();
//        thread1.setPriority(Thread.MAX_PRIORITY);
////THREAD 1 XXX=========================================================================================================
        //tabs xxx
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        //remove sellerDashboard option in sellerDashboard.java activity
        menu.findItem(R.id.seller_dashboard_inOptionsMenu).setVisible(false);
        menu.findItem(R.id.about_inOptionsMenu).setVisible(false);
        menu.findItem(R.id.order_history_inOptionsMenu).setVisible(false);
        menu.findItem(R.id.my_cart_inOptionsMenu).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.my_cart_inOptionsMenu:
            case R.id.order_history_inOptionsMenu:
            case R.id.about_inOptionsMenu:
            case R.id.settings_inOptionsMenu:
                break;
            case R.id.user_dashboard_inOptionsMenu: startActivity(new Intent(getApplicationContext(),UserDashboard.class));
                finish();
                break;

            case R.id.logout_btn_inOptionMenu: {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(SellerDashboard.this,MainActivity.class));
                //finish();
                //getCacheDir().deleteOnExit();
                return true;
            }

        }


        return super.onOptionsItemSelected(item);
    }

    public void toFirebaseActivity(View view) {
        Intent firebaseAct_intent = new Intent(this, FireBaseDataTransferActivity.class);
        startActivity(firebaseAct_intent);
    }

}