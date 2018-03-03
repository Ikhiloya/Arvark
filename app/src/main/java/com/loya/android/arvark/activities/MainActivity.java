package com.loya.android.arvark.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loya.android.arvark.R;
import com.loya.android.arvark.adapters.CategoryAdapter;
import com.loya.android.arvark.adapters.ViewPagerAdapter;
import com.loya.android.arvark.models.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, CategoryAdapter.ListItemClickListener {

    //object reference
    private ViewPager viewPager;
    private LinearLayout sliderDotsPanel;
    private int dotsCount;
    private ImageView[] dots;

    private RecyclerView mRecycler;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Category> categories;

    private ImageView profileImage;
    private TextView name, companyName;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    // [START declare_databaseReference]
    private DatabaseReference mDatabaseUsers;
    // [END declare_auth_databaseReference]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // [START initialize_databaseReference]
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
       // [END initialize_auth_databaseReference]

        //init views
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        mRecycler = (RecyclerView) findViewById(R.id.recycler);

        sliderDotsPanel = (LinearLayout) findViewById(R.id.sliderDots);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);

        viewPager.setAdapter(viewPagerAdapter);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        //inflate the header and gets access to the nav profile picture and text
        View navView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        final ImageView profileImage = navView.findViewById(R.id.nav_profile_image_view);
        name = navView.findViewById(R.id.nav_name);
        companyName = navView.findViewById(R.id.nav_company_name);

        //retrieve user data from the database
        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //get data
                String imageUrl = (String) dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("image").getValue();
                String nameText = (String) dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("name").getValue();
                String companyNameText = (String) dataSnapshot.child(mAuth.getCurrentUser().getUid()).child("company_name").getValue();

                //set views
                name.setText(nameText);
                companyName.setText(companyNameText);
                profileImage.setImageResource(R.drawable.ic_account_circle_black_24dp);
                Picasso.with(MainActivity.this).load(imageUrl).into(profileImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        navigationView.setNavigationItemSelectedListener(this);

       //set up the image slider
        dotsCount = viewPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotsPanel.addView(dots[i], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotsCount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.nonactive_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        //init arrayList
        categories = new ArrayList<>();
        categories.add(new Category(R.drawable.cat_a, "Excavators"));
        categories.add(new Category(R.drawable.cat_b, "Buldozers"));
        categories.add(new Category(R.drawable.cat_c, "Scrapers"));
        categories.add(new Category(R.drawable.cat_d, "Motor Graders"));

        //set up the GridLayout
        int numberOfColumns = 2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numberOfColumns);

        //set up recycler view and and adapter
        mRecycler.setHasFixedSize(true);
        mRecycler.setLayoutManager(gridLayoutManager);
        categoryAdapter = new CategoryAdapter(categories, this, this);
        mRecycler.setAdapter(categoryAdapter);

    }

    @Override
    public void onListItemClick() {
        Intent intent = new Intent(MainActivity.this, ProductActivity.class);
        startActivity(intent);
    }


    //inner class that sets the time
    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void signOut() {
        mAuth.signOut();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        } else if (id == R.id.nav_wishlist) {
            startActivity(new Intent(MainActivity.this, Cart.class));

        } else if (id == R.id.nav_cart) {
            startActivity(new Intent(MainActivity.this, Cart.class));

        } else if (id == R.id.nav_admin) {
            startActivity(new Intent(MainActivity.this, DetailActivity.class));

        } else if (id == R.id.nav_log_out) {
            signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
