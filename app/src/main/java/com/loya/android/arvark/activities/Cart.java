package com.loya.android.arvark.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.loya.android.arvark.R;
import com.loya.android.arvark.adapters.CartAdapter;
import com.loya.android.arvark.models.CartModel;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {

    private RecyclerView mRecycler;
    private CartAdapter cartAdapter;
    private ArrayList<CartModel> cartModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        TextView cartItemNumber = (TextView)findViewById(R.id.cart_item_number);

        mRecycler = (RecyclerView) findViewById(R.id.cartRecycler);
        cartModels = new ArrayList<>();
        cartModels.add(new CartModel("Motor Grader", R.drawable.cat_a));
        cartModels.add(new CartModel("Skid Steer", R.drawable.cat_b));
        cartModels.add(new CartModel("Trencher", R.drawable.cat_c));
        cartModels.add(new CartModel("Wheel Loader", R.drawable.cat_d));
        cartModels.add(new CartModel("Forklift", R.drawable.cat_e));


        cartItemNumber.setText(String.valueOf(cartModels.size()));
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setHasFixedSize(true);
        cartAdapter = new CartAdapter(cartModels, this);
        mRecycler.setAdapter(cartAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id ==android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
