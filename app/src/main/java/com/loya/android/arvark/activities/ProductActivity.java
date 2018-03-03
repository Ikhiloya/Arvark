package com.loya.android.arvark.activities;

import android.content.Intent;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.loya.android.arvark.R;
import com.loya.android.arvark.adapters.ProductAdapter;
import com.loya.android.arvark.models.Category;
import com.loya.android.arvark.utils.Utils;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity implements ProductAdapter.ListItemClickListener {

    private RecyclerView mRecycler;
    private ProductAdapter mAdapter;
    private ArrayList<Category> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        mRecycler = (RecyclerView) findViewById(R.id.productRecycler);

        products = new ArrayList<>();
        products.add(new Category(R.drawable.cat_a, "Excavator"));
        products.add(new Category(R.drawable.cat_b, "Motor Grader"));
        products.add(new Category(R.drawable.cat_c, "Trenchers"));
        products.add(new Category(R.drawable.cat_c, "Dumpers"));
        products.add(new Category(R.drawable.cat_d, "Skid Steer"));
        products.add(new Category(R.drawable.cat_e, "Forklift"));

        int numberOfColumns = 2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numberOfColumns);
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(gridLayoutManager);
        mAdapter = new ProductAdapter(products, this, this);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate menu
        getMenuInflater().inflate(R.menu.product, menu);

        // Get the notifications MenuItem and LayerDrawable (layer-list)
        MenuItem item = menu.findItem(R.id.action_shopping_cart);
        LayerDrawable icon = (LayerDrawable) item.getIcon();

        // Update LayerDrawable's BadgeDrawable
        Utils.setBadgeCount(this, icon, 20);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_shopping_cart) {
            startActivity(new Intent(ProductActivity.this, Cart.class));
        } else if (id == R.id.favorite) {
            startActivity(new Intent(ProductActivity.this, Cart.class));
        } else if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick() {
        startActivity(new Intent(ProductActivity.this, DetailActivity.class));
    }
}
