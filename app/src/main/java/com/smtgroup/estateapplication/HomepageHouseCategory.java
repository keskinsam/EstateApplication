package com.smtgroup.estateapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomepageHouseCategory extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.listHouse)
    ListView listHouse;
    LayoutInflater linf;
    BaseAdapter adp;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    TextView nav_txtName, nav_txtEmail;


    List ls = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_house_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dataGetir();
        Log.d("Sizeee :", "" + ls.size());
        linf = LayoutInflater.from(this);


        ButterKnife.bind(this);

        adp = new BaseAdapter() {
            @Override
            public int getCount() {
                return ls.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View view, ViewGroup viewGroup) {
                if (view == null) {
                    view = linf.inflate(R.layout.house_row, null);
                }

                try {
                    TextView txtKategori = view.findViewById(R.id.txtHouseCategory);
                    txtKategori.setText(ls.get(position).toString());
                } catch (Exception e) {
                    Log.e("Liste doldurma hatası", e.toString());
                }
                return view;
            }
        };
        listHouse.setAdapter(adp);

        listHouse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                AdPage.adCategory = ls.get(i).toString();
                Intent intent = new Intent(HomepageHouseCategory.this, HomepageType.class);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        setUserInfos();
    }

    public void setUserInfos() {
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
        getMenuInflater().inflate(R.menu.homepage_house_category, menu);
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
            Intent i = new Intent(HomepageHouseCategory.this, HomepageHouseCategory.class);
            startActivity(i);
        } else if (id == R.id.nav_workplace) {
            Intent i = new Intent(HomepageHouseCategory.this, HomepageBusinessCategory.class);
            startActivity(i);
        } else if (id == R.id.nav_building) {
            AdPage.adCategory = "Bina";
            Intent i = new Intent(HomepageHouseCategory.this, HomepageType.class);
            startActivity(i);
        } else if (id == R.id.nav_plot) {
            AdPage.adCategory = "Arsa";
            Intent i = new Intent(HomepageHouseCategory.this, HomepageType.class);
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

    public List dataGetir() {

        ls.add("Daire");
        ls.add("Rezidans");
        ls.add("Müstakil");
        ls.add("Villa");
        ls.add("Çiftlik Evi");
        ls.add("Köşk");
        ls.add("Yalı");
        ls.add("Yazlık");

        return ls;
    }
}
