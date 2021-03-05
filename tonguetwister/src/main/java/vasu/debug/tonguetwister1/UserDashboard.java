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

public class UserDashboard extends AppCompatActivity{


    ViewPager2 viewPager2;
    int mTotalTabs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        //---GOOGLE SIGN IN -----------------------------------------------

        //google sign in vars
        ImageView userImage;
        TextView usernametv, userInfo;


        //for putting info into navDrawer Header
        NavigationView navDrawerView = findViewById(R.id.user_navDrawerView);//used in NavigationDrawer as well down below
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
        Toolbar user_toolbar = findViewById(R.id.user_main_toolbar);
        //        user_toolbar.setTitle("");
        setSupportActionBar(user_toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.app_name);
        //actionBar.setIcon(R.drawable.ic_cart);//for company title
        //        actionBar.show();
        //        actionBar.hide();

        //color white the triple dots/menu dots on app bar
        Drawable drawable = user_toolbar.getOverflowIcon();
        if(drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), getResources().getColor(R.color.white,getTheme()));
            user_toolbar.setOverflowIcon(drawable);
        }
        //appbar ops xxx

        //----------NAVIGATION drawer-------------------
        //navDrawerHeader,navDrawermenu , activity userdashboard xml--> has drawer layout,activity sellerdashboard xml--> has seller drawer layout


        //nav drawer--------------------------------------------
        DrawerLayout drawerLayout = findViewById(R.id.user_drawerLayout);

        ActionBarDrawerToggle navDrawerToggle = new ActionBarDrawerToggle(UserDashboard.this, drawerLayout, user_toolbar,R.string.open,R.string.close);

        if(drawerLayout!=null){
            drawerLayout.addDrawerListener(navDrawerToggle);
        }
        navDrawerToggle.syncState();
        user_toolbar.setNavigationIcon(R.drawable.ic_nav_menu);//change navbar hamburger-->white color


        //ACCESS to navDrawer via id is required [ NavigationView navDrawerView = findViewById(R.id.navDrawerView);]. it was accesses above. so using directly.
        if(navDrawerView !=null){

            //removing user dashboard option in navigation drawer----this is user dashboard activity
            Menu navMenu = navDrawerView.getMenu();
                navMenu.findItem(R.id.user_dashboard_inNavMenu).setVisible(false);
                navMenu.findItem(R.id.edit_menu_inNavMenu).setVisible(false);


            //navDrawerView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
            navDrawerView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    // update the main content by replacing fragments
//                    Fragment fragment = null;
//                    FragmentManager fragmentManager = getSupportFragmentManager(); // For AppCompat use getSupportFragmentManager
//
                    switch (item.getItemId()){

                        case R.id.seller_dashboard_inNavMenu:  startActivity(new Intent(getApplicationContext(),SellerDashboard.class));
                            break;

                        case R.id.menu_inNavMenu:
                            //fragment = new MenuFragment();
                            break;

                    }//switch case xxx
//
//                  if(fragment.equals(null)){//do nothing
//                    }else {
//                        fragmentManager.beginTransaction()
//                                .replace(R.id.user_tabs_in_view_pager2, fragment)
//                                .commit();
//                    }
                    return true;

                }//onNavItemSelected xxx


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
        //viewsateadaper,offerFrag,menuFrag,bestsellerFrag,defaultFrag,contentMain xml in --> userDashboard java
        TabLayout tabLayout = findViewById(R.id.user_tabs_menu_appbar);

        tabLayout.addTab(tabLayout.newTab().setText("menu"));
        tabLayout.addTab(tabLayout.newTab().setText("bestsellers"));
        tabLayout.addTab(tabLayout.newTab().setText("offers"));
        tabLayout.addTab(tabLayout.newTab().setText("option1"));
        tabLayout.addTab(tabLayout.newTab().setText("option2"));
        tabLayout.addTab(tabLayout.newTab().setText("option3"));
        tabLayout.addTab(tabLayout.newTab().setText("option4"));
        tabLayout.addTab(tabLayout.newTab().setText("option5"));
        tabLayout.setTabMode(tabLayout.MODE_SCROLLABLE);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        mTotalTabs = tabLayout.getTabCount();


        // ------viewpager2--------------
        viewPager2 = findViewById(R.id.user_tabs_in_view_pager2);

//        List<String> list = new ArrayList<>();
//        list.add("First Screen");
//        list.add("Second Screen");
//        list.add("Third Screen");
//        list.add("Fourth Screen");


        //for listening page change/tab clicks
        FragmentManager fm = getSupportFragmentManager();
        String classname = UserDashboard.class.getSimpleName();
        ViewStateAdapter sa = new ViewStateAdapter(fm, getLifecycle(), mTotalTabs, classname);
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

    }//onCreate XXX----------------

//    @Override
//    public void onBackPressed() {
//        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        menu.findItem(R.id.user_dashboard_inOptionsMenu).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.my_cart_inOptionsMenu:startActivity(new Intent(getApplicationContext(),CartActivity.class));
                break;
            case R.id.order_history_inOptionsMenu:
            case R.id.about_inOptionsMenu:
            case R.id.settings_inOptionsMenu:
                break;
            case R.id.seller_dashboard_inOptionsMenu: startActivity(new Intent(getApplicationContext(),SellerDashboard.class)); finish();
                break;

            case R.id.logout_btn_inOptionMenu: {
                                                FirebaseAuth.getInstance().signOut();
                                                startActivity(new Intent(UserDashboard.this,MainActivity.class));
                                                finish(); }
                                                break;


        }


        return super.onOptionsItemSelected(item);
    }

//    private void loadFragment(MenuFragment menuFragment) {
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.user_tabs_in_view_pager2, menuFragment);
//        transaction.commit();
//    }

    public void toFirebaseActivity(View view) {
        Intent firebaseAct_intent = new Intent(this, FireBaseDataTransferActivity.class);
        startActivity(firebaseAct_intent);
    }

    private void deleteAppData() {
        //deleteAppData(); it close the app and clear cache data
        try {
            // clearing app data
            String packageName = getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+packageName);

        } catch (Exception e) {
            e.printStackTrace();
        } }

}