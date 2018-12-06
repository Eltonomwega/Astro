package com.example.eltonpsycho.astro.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.example.eltonpsycho.astro.R;
import com.example.eltonpsycho.astro.utils.ripples.RippleView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    FloatingActionButton searchForUsersFab;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RippleView rippleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing the views by finding the ids

        init();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //not using this title because its easier to use the textview for a title
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        searchForUsersFab = findViewById(R.id.searchForUsersFab);
        startIntroAnimation(searchForUsersFab);

        drawerLayout = findViewById(R.id.main_drawer);
        navigationView = findViewById(R.id.navigation_view);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.hamburger));

        navigationView.setNavigationItemSelectedListener(this);

        rippleView = findViewById(R.id.rippleView);
        rippleView.newRipple();

    }

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    //when menu item is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.history:
                break;
            case R.id.notification:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    private void startIntroAnimation(View view) {
        view.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.fab_size));
        view.animate()
                //the position at which the animation starts = 0
                .translationY(0)
                //Type of interpolator
                .setInterpolator(new OvershootInterpolator(1.f))
                //duration before animation starts
                .setStartDelay(400)
                //durartion of the animation
                .setDuration(400)
                .start();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
