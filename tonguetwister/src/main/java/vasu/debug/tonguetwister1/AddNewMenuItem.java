package vasu.debug.tonguetwister1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;

public class AddNewMenuItem extends AppCompatActivity {

    //declaring layouts
    RelativeLayout perKgLayout, perUnitLayout, perPortionLayout;

    MaterialButtonToggleGroup tglGrp_serve_type,tglGrp_gm_kg;

    //declaring view for kg layout
    EditText et_pr_1,et_pr_2, et_pr_3, et_pr_4, et_pr_5, et_pr_6, et_pr_7, et_pr_8, et_pr_9, et_pr_10;
    EditText et_wt_1,et_wt_2, et_wt_3, et_wt_4, et_wt_5, et_wt_6, et_wt_7, et_wt_8, et_wt_9, et_wt_10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_menu_item);

        //initializing views
        perKgLayout = findViewById(R.id.per_kg_layout);
        perUnitLayout = findViewById(R.id.per_unit_layout);
        perPortionLayout = findViewById(R.id.per_portion_layout);

        //for kg Layout
        et_pr_1 = findViewById(R.id.enter_price_inPerKgLayout1);
        et_pr_2 = findViewById(R.id.enter_price_inPerKgLayout2);
        et_pr_3 = findViewById(R.id.enter_price_inPerKgLayout3);
        et_pr_4 = findViewById(R.id.enter_price_inPerKgLayout4);
        et_pr_5 = findViewById(R.id.enter_price_inPerKgLayout5);
        et_pr_6 = findViewById(R.id.enter_price_inPerKgLayout6);
        et_pr_7 = findViewById(R.id.enter_price_inPerKgLayout7);
        et_pr_8 = findViewById(R.id.enter_price_inPerKgLayout8);
        et_pr_9 = findViewById(R.id.enter_price_inPerKgLayout9);
        et_pr_10 = findViewById(R.id.enter_price_inPerKgLayout10);

        et_wt_1 = findViewById(R.id.enter_weight_inPerKgLayout1);
        et_wt_2 = findViewById(R.id.enter_weight_inPerKgLayout2);
        et_wt_3 = findViewById(R.id.enter_weight_inPerKgLayout3);
        et_wt_4 = findViewById(R.id.enter_weight_inPerKgLayout4);
        et_wt_5 = findViewById(R.id.enter_weight_inPerKgLayout5);
        et_wt_6 = findViewById(R.id.enter_weight_inPerKgLayout6);
        et_wt_7 = findViewById(R.id.enter_weight_inPerKgLayout7);
        et_wt_8 = findViewById(R.id.enter_weight_inPerKgLayout8);
        et_wt_9 = findViewById(R.id.enter_weight_inPerKgLayout9);
        et_wt_10 = findViewById(R.id.enter_weight_inPerKgLayout10);


        visibilityGone(et_pr_3);
        visibilityGone(et_pr_4);
        visibilityGone(et_pr_5);
        visibilityGone(et_pr_6);
        visibilityGone(et_pr_7);
        visibilityGone(et_pr_8);
        visibilityGone(et_pr_9);
        visibilityGone(et_pr_10);

        visibilityGone(et_wt_3);
        visibilityGone(et_wt_4);
        visibilityGone(et_wt_5);
        visibilityGone(et_wt_6);
        visibilityGone(et_wt_7);
        visibilityGone(et_wt_8);
        visibilityGone(et_wt_9);
        visibilityGone(et_wt_10);


        tglGrp_serve_type = findViewById(R.id.tglGrp_serve_type_selection);
        tglGrp_gm_kg = findViewById(R.id.tglGrp_gram_kilogram);

        //setting layout to invisible/gone
        perKgLayout.setVisibility(View.GONE);
        perUnitLayout.setVisibility(View.GONE);
        perPortionLayout.setVisibility(View.GONE);

        //------APP-BAR ops
        Toolbar addNewMenuItem_toolbar = findViewById(R.id.addNewMenuItem_toolbar);
        setSupportActionBar(addNewMenuItem_toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("add new menu item");
        //actionBar.setIcon(R.drawable.ic_cart);

        //color white the triple dots/menu dots on app bar for optionsMenu
        Drawable drawable = addNewMenuItem_toolbar.getOverflowIcon();
        if(drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            DrawableCompat.setTint(drawable.mutate(), getResources().getColor(R.color.white,getTheme()));
            addNewMenuItem_toolbar.setOverflowIcon(drawable);
        }
        //appbar ops xxx


        tglGrp_serve_type.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    switch (checkedId){
                        case R.id.btn_qty_perUnit:
                            Toast.makeText(getApplicationContext(),"clicked per unit", Toast.LENGTH_SHORT).show();break;
                        case R.id.btn_qty_perGrams:
                            Toast.makeText(getApplicationContext(),"clicked per gram kilogram", Toast.LENGTH_SHORT).show();break;
                        case R.id.btn_qty_portionSize:
                            Toast.makeText(getApplicationContext(),"clicked per portion", Toast.LENGTH_SHORT).show();break;

                    }
                }
            }
        });

    }

    public void loadPerKilogramLayout(View view) {
        perKgLayout.setVisibility(View.VISIBLE);//this kg
        perUnitLayout.setVisibility(View.GONE);
        perPortionLayout.setVisibility(View.GONE);
    }

    public void loadPerUnitLayout(View view) {
        perKgLayout.setVisibility(View.GONE);
        perUnitLayout.setVisibility(View.VISIBLE);//this unit
        perPortionLayout.setVisibility(View.GONE);
    }

    public void loadPerPortionsLayout(View view) {
        perKgLayout.setVisibility(View.GONE);
        perUnitLayout.setVisibility(View.GONE);
        perPortionLayout.setVisibility(View.VISIBLE);//this portion
    }

    public void visibilityGone(View view){
        view.setVisibility(View.GONE);
    }

    public void addRowInPerKgLayout(View view) {

        et_pr_3.setVisibility(View.VISIBLE);
        et_pr_4.setVisibility(View.VISIBLE);
        et_pr_5.setVisibility(View.VISIBLE);
        et_pr_6.setVisibility(View.VISIBLE);
        et_pr_7.setVisibility(View.VISIBLE);
        et_pr_8.setVisibility(View.VISIBLE);
        et_pr_9.setVisibility(View.VISIBLE);
        et_pr_10.setVisibility(View.VISIBLE);

        et_wt_3.setVisibility(View.VISIBLE);
        et_wt_4.setVisibility(View.VISIBLE);
        et_wt_5.setVisibility(View.VISIBLE);
        et_wt_6.setVisibility(View.VISIBLE);
        et_wt_7.setVisibility(View.VISIBLE);
        et_wt_8.setVisibility(View.VISIBLE);
        et_wt_9.setVisibility(View.VISIBLE);
        et_wt_10.setVisibility(View.VISIBLE);

        View btn = findViewById(R.id.btn_addRow_inPerKgLayout);
        btn.setVisibility(View.GONE);

    }
}