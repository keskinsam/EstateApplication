package com.smtgroup.estateapplication;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.smtgroup.estateapplication.properties.User;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomepageType extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.btnsatilik)
    Button btnSatilik;
    @BindView(R.id.btnkiralık)
    Button btnKiralık;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    TextView nav_txtName, nav_txtEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);


        btnKiralık.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdPage.adType = "Kiralık";
                Intent i = new Intent(HomepageType.this, AdPage.class);
                startActivity(i);
            }
        });

        btnSatilik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdPage.adType = "Satılık";
                Intent i = new Intent(HomepageType.this, AdPage.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        setUserInfos();
    }

    public void setUserInfos(){
        nav_txtName = navigationView.getHeaderView(0).findViewById(R.id.nav_txtName);
        nav_txtEmail = navigationView.getHeaderView(0).findViewById(R.id.nav_txtEmail);
        nav_txtName.setText(MainActivity.user.getName() + " " + MainActivity.user.getSurname());
        nav_txtEmail.setText(MainActivity.user.getEmail());
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
        getMenuInflater().inflate(R.menu.homepage, menu);
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

        if (id == R.id.nav_house) {
            Intent i = new Intent(HomepageType.this,HomepageHouseCategory.class);
            startActivity(i);
        } else if (id == R.id.nav_workplace) {
            Intent i = new Intent(HomepageType.this,HomepageBusinessCategory.class);
            startActivity(i);
        } else if (id == R.id.nav_building) {
            AdPage.adCategory = "Bina";
            Intent i = new Intent(HomepageType.this,HomepageType.class);
            startActivity(i);
        } else if (id == R.id.nav_plot) {
            AdPage.adCategory = "Arsa";
            Intent i = new Intent(HomepageType.this,HomepageType.class);
            startActivity(i);
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, "Share butonuna tiklandi", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(this, "Send butonuna tiklandi", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
