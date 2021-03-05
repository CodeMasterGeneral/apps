package vasu.debug.tonguetwister1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;

public class EditMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        //---GOOGLE SIGN IN -----------------------------------------------

        //google sign in vars
        ImageView userImage;
        TextView usernametv, userInfo;


        //for putting info into navDrawer Header
        NavigationView navDrawerView = findViewById(R.id.editMenu_navDrawerView);//used in NavigationDrawer as well down below
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
        Toolbar editMenu_toolbar = findViewById(R.id.editMenu_toolbar);
        setSupportActionBar(editMenu_toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("edit menu");
        //actionBar.setIcon(R.drawable.ic_cart);

        //color white the triple dots/menu dots on app bar for optionsMenu
        Drawable drawable = editMenu_toolbar.getOverflowIcon();
        if(drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), getResources().getColor(R.color.white,getTheme()));
            editMenu_toolbar.setOverflowIcon(drawable);
        }
        //appbar ops xxx

        //----------NAVIGATION drawer-------------------
        //navDrawerHeader,navDrawermenu , activity userdashboard xml--> has drawer layout,activity sellerdashboard xml--> has seller drawer layout

        //nav drawer--------------------------------------------
        DrawerLayout drawerLayout = findViewById(R.id.editMenu_drawer_layout);
        ActionBarDrawerToggle navDrawerToggle = new ActionBarDrawerToggle(EditMenu.this, drawerLayout, editMenu_toolbar,R.string.open,R.string.close);

        if(drawerLayout!=null){
            drawerLayout.addDrawerListener(navDrawerToggle);
        }
        navDrawerToggle.syncState();
        editMenu_toolbar.setNavigationIcon(R.drawable.ic_nav_menu);//change navbar hamburger-->white color

        //ACCESS to navDrawer via id is required [ NavigationView navDrawerView = findViewById(R.id.navDrawerView);]. it was accesses above. so using directly.
        if(navDrawerView !=null){

            //removing user dashboard option in navigation drawer----this is user dashboard activity
//            Menu navMenu = navDrawerView.getMenu();
//            navMenu.findItem(R.id.user_dashboard_inNavMenu).setVisible(false);
//            navMenu.findItem(R.id.edit_menu_inNavMenu).setVisible(false);

            //navDrawerView.setNavigationItemSelectedListener(this::onOptionsItemSelected);
            navDrawerView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()){
                        case R.id.seller_dashboard_inNavMenu:  startActivity(new Intent(getApplicationContext(),SellerDashboard.class));
                            break;
                        case R.id.menu_inNavMenu:
                            //fragment = new MenuFragment();
                            break;
                    }//switch case xxx
                    return true;
                }//onNavItemSelected xxx
            });
        }//if loop xxx

        //---------nav drawer xxx----------------

        //FAB
        FloatingActionButton efab = findViewById(R.id.fab_add_menu_item);
        efab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
               startActivity(new Intent(EditMenu.this,AddNewMenuItem.class));
            }
        });


    }//oncreate xxx


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
                startActivity(new Intent(this,MainActivity.class));
                //finish();
                //getCacheDir().deleteOnExit();
                return true;
            }

        }


        return super.onOptionsItemSelected(item);
    }


}