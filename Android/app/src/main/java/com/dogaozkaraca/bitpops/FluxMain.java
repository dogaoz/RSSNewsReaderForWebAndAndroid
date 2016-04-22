package com.dogaozkaraca.bitpops;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.yalantis.phoenix.PullToRefreshView;

public class FluxMain extends AppCompatActivity
        //implements NavigationView.OnNavigationItemSelectedListener
        {

    public static FloatingActionButton fab;
    public static Activity rotaryReader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle("BitPops | Now");
        setContentView(R.layout.activity_rotary_reader);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rotaryReader = this;
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

      //  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      //  ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
       //         this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       // drawer.setDrawerListener(toggle);
        //toggle.syncState();

       // NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
       PullToRefreshView mPullToRefreshView = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        GridView mGridView = (GridView) findViewById(R.id.gridView1);
        FluxCore.load(this,mPullToRefreshView,mGridView);
        boolean onFirstTime = true;
        if (onFirstTime == true)
        {
            //FragmentManager fragmentManager = getSupportFragmentManager();

            //    fragmentManager.beginTransaction()
            //            .replace(R.id.container, FirstFragment.newInstance())
            //            .commit();
            Intent FT = new Intent(FluxMain.this,FluxFirstUse.class);
            startActivity(FT);
        }

    }


    @Override
    public void onResume()
    {
        super.onResume();

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
        getMenuInflater().inflate(R.menu.rotary_reader, menu);
        return true;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(FluxMain.this, FluxSettings.class);
            FluxMain.this.startActivity(intent);

           // Intent myIntent = new Intent(FluxMain.this, RssSettings.class);
           // FluxMain.this.startActivity(myIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    //@Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.all_feed) {

        } else if (id == R.id.news_feed) {

        } else if (id == R.id.twitter_feed) {

        } else if (id == R.id.instagram_feed) {

        } else if (id == R.id.settings) {
            Intent intent = new Intent(FluxMain.this, FluxSettings.class);

            FluxMain.this.startActivity(intent);

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void feedloaded()
    {
        Snackbar.make(fab, "Updated now.", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
    public static void showMessage(String s)
    {
        Snackbar.make(fab, s, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
