package com.example.eltonpsycho.astro.activities;

import android.content.Intent;
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
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.eltonpsycho.astro.R;
import com.example.eltonpsycho.astro.utils.ripples.RippleBackground;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar,tbt;
    FloatingActionButton searchForUsersFab;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RippleBackground rippleView;
    ImageButton musicNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setContentView(R.layout.activity_main);

        //initializing the views by finding the ids

        init();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // setSupportActionBar(tbt);

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

        musicNote = (ImageButton) findViewById(R.id.musicNote);
       //musicNote.setVisibility(View.INVISIBLE);

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

    public void settingsLaunch(MenuItem item) {

        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }

    public void startRipple(View view) {

        Toast.makeText(this, "Searching for nearby users", Toast.LENGTH_SHORT).show();
        rippleView = findViewById(R.id.rippleView);
        rippleView.startRippleAnimation();
    }

    public void playlistLaunch(MenuItem item) {

        Intent intent = new Intent(this,PlaylistActivity.class);
        startActivity(intent);
    }
}
