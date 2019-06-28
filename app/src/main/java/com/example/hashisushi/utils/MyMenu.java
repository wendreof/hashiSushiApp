package com.example.hashisushi.utils;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hashisushi.R;
import com.example.hashisushi.views.ActPoints;
import com.example.hashisushi.views.ActRegProd;
import com.example.hashisushi.views.ActSignup;
import com.example.hashisushi.views.cardap.ActCombo;
import com.example.hashisushi.views.cardap.ActDrinks;
import com.example.hashisushi.views.cardap.ActPlatAce;
import com.example.hashisushi.views.cardap.ActPlatHot;
import com.example.hashisushi.views.cardap.ActSaleCardap;
import com.example.hashisushi.views.cardap.ActTemakis;

public class MyMenu extends AppCompatActivity {

    public MyMenu() { }

    //==> MENUS
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_promotion, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.menu_enter)
        {
            Intent it = new Intent(this, ActSaleCardap.class);
            startActivity(it);
            return true;
        }

        if (id == R.id.menu_plat_hot)
        {
            Intent it = new Intent(this, ActPlatHot.class);
            startActivity(it);
            return true;
        }

        if (id == R.id.menu_plat_ace)
        {
            Intent it = new Intent(this, ActPlatAce.class);
            startActivity(it);
            return true;
        }

        if (id == R.id.menu_combo)
        {
            Intent it = new Intent(this, ActCombo.class);
            startActivity(it);
            return true;
        }

        if (id == R.id.menu_drinks)
        {
            Intent it = new Intent(this, ActDrinks.class);
            startActivity(it);
            return true;
        }
        if (id == R.id.menu_temakis)
        {
            Intent it = new Intent(this, ActTemakis.class);
            startActivity(it);
            return true;
        }
        if (id == R.id.menu_edit_cadastro)
        {
            Intent it = new Intent(this, ActSignup.class);
            startActivity(it);
            return true;
        }
        if (id == R.id.menu_cad_prod )
        {
            Intent it = new Intent(this, ActRegProd.class);
            startActivity(it);
            return true;
        }
        if (id == R.id.menu_points)
        {
            Intent it = new Intent(this, ActPoints.class);
            startActivity(it);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
