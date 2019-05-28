package com.example.hashisushi.views.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hashisushi.R;
import com.example.hashisushi.views.ActOrder;
import com.example.hashisushi.views.ActPromotion;
import com.example.hashisushi.views.ActSaleCardap;
import com.example.hashisushi.views.cardap.ActCombo;
import com.example.hashisushi.views.cardap.ActDrinks;
import com.example.hashisushi.views.cardap.ActPlatAce;
import com.example.hashisushi.views.cardap.ActPlatHot;
import com.example.hashisushi.views.cardap.ActTemakis;

public class Navigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            Intent it = new Intent(this, ActPromotion.class);
            startActivity(it);
        } else if (id == R.id.nav_gallery) {
            Intent it = new Intent(this, ActSaleCardap.class);
            startActivity(it);
        } else if (id == R.id.nav_slideshow) {
            Intent it = new Intent(this, ActPlatHot.class);
            startActivity(it);
        } else if (id == R.id.nav_tools) {
            Intent it = new Intent(this, ActPlatAce.class);
            startActivity(it);
        } else if (id == R.id.temakis) {
            Intent it = new Intent(this, ActTemakis.class);
            startActivity(it);
        } else if (id == R.id.combo) {
            Intent it = new Intent(this, ActCombo.class);
            startActivity(it);
        } else if (id == R.id.bebidas) {
            Intent it = new Intent(this, ActDrinks.class);
            startActivity(it);
        } else if (id == R.id.carrinho) {
            Intent it = new Intent(this, ActOrder.class);
            startActivity(it);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
