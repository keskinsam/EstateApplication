package com.smtgroup.estateapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;
import android.widget.Toast;

import com.smtgroup.estateapplication.enums.CategoryEnum;
import com.smtgroup.estateapplication.enums.UserEnum;
import com.smtgroup.estateapplication.properties.Category;
import com.smtgroup.estateapplication.properties.User;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomepageCategory extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.txtBaslik)
    TextView txtBaslik;
    @BindView(R.id.btnKonut)
    Button btnKonut;
    @BindView(R.id.btnIsyeri)
    Button btnIsyeri;
    @BindView(R.id.btnBina)
    Button btnBina;
    @BindView(R.id.btnArsa)
    Button btnArsa;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    TextView nav_txtName, nav_txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Log.e("Location ", "HomepageCategory");

        Log.e("Tür", AdPage.adType);
        ButterKnife.bind(this);

        btnKonut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageCategory.this, HomepageHouseCategory.class);
                startActivity(intent);
            }
        });

        btnIsyeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomepageCategory.this, HomepageBusinessCategory.class);
                startActivity(i);
            }
        });

        btnArsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdPage.adCategory = "Arsa";
                Intent i = new Intent(HomepageCategory.this, HomepageType.class);
                Bundle bundle = new Bundle();
                startActivity(i);
            }
        });

        btnBina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AdPage.adCategory = "Bina";
                Intent i = new Intent(HomepageCategory.this, HomepageType.class);
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
            Intent i = new Intent(HomepageCategory.this, HomepageHouseCategory.class);
            startActivity(i);
        } else if (id == R.id.nav_workplace) {
            Intent i = new Intent(HomepageCategory.this, HomepageBusinessCategory.class);
            startActivity(i);
        } else if (id == R.id.nav_building) {
            AdPage.adCategory = "Bina";
            Intent i = new Intent(HomepageCategory.this, HomepageType.class);
            startActivity(i);
        } else if (id == R.id.nav_plot) {
            AdPage.adCategory = "Arsa";
            Intent i = new Intent(HomepageCategory.this, HomepageType.class);
            startActivity(i);
        } else if (id == R.id.nav_fav) {
            AdPage.adCategory = "";
            Intent i = new Intent(HomepageCategory.this,AdPage.class);
            startActivity(i);
        } else if (id == R.id.nav_exit) {
            SharedPreferences sPreferences;
            SharedPreferences.Editor editor;

            sPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            editor = sPreferences.edit();

            if (editor.remove("userEmail").commit() && editor.remove("userPass").commit()){
                Intent intent = new Intent(HomepageCategory.this, SignIn.class);
                startActivity(intent);
                HomepageCategory.this.finish();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    public void getCategory() {
//        HashMap<String, String> hmRegister = new HashMap<>();
//        hmRegister.put("ref", "3d264cacec20af4f9b237a655f49bc60");
//        String url = "http://jsonbulut.com/json/companyCategory.php";
//        new JData(url, hmRegister, this).execute();
////        connect(url, hmRegister);
//    }
//
//    public void connect(String url, HashMap<String, String> hashMap) {
//        try {
//            String data = Jsoup.connect(url).data(hashMap).timeout(30000).ignoreContentType(true).execute().body();
//
//            JSONObject object = new JSONObject(data);
//            JSONObject uObject = object.getJSONArray("" + CategoryEnum.Kategoriler).getJSONObject(0);
//
//            boolean durum = uObject.getBoolean("durum");
//            String mesaj = uObject.getString("mesaj");
//            if (durum) {
//                Log.e("HomepageCategory.durum", "" + durum);
//                JSONArray categoryObjects = uObject.getJSONArray("" + CategoryEnum.Categories);
////                Log.e("HomepageCategory.categoryObjects size ",""+categoryObjects.length());
//                categoryList = new ArrayList<>();
//                for (int i = 0; i < categoryObjects.length(); i++) {
//                    Log.e("HomepageCategory i", "" + i);
//
//                    JSONObject jsonObject = categoryObjects.getJSONObject(i);
//                    Category category = new Category();
//                    category.setId(jsonObject.getString("" + CategoryEnum.CatogryId));
//                    category.setName(jsonObject.getString("" + CategoryEnum.CatogryName));
//                    category.setTopId(jsonObject.getString("" + CategoryEnum.TopCatogryId));
//                    categoryList.add(category);
//                }
//
//
//            } else {
//                Toast.makeText(HomepageCategory.this, mesaj, Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception ex) {
//            Log.e("onpostexecure error", "" + ex.toString());
//        }
//    }
//
//    class JData extends AsyncTask<Void, Void, Void> {
//
//        String url = "";
//        HashMap<String, String> hashMap = null;
//        String data = "";
//        Context context;
//
//        public JData(String url, HashMap<String, String> hashMap, Context context) {
//            this.url = url;
//            this.hashMap = hashMap;
//            this.context = context;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try {
//                data = Jsoup.connect(url).data(hashMap).timeout(30000).ignoreContentType(true).execute().body();
//            } catch (Exception ex) {
//                Log.e("Get data error", "" + ex.toString());
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            try {
//                JSONObject object = new JSONObject(data);
//                JSONObject uObject = object.getJSONArray("" + CategoryEnum.Kategoriler).getJSONObject(0);
//
//                boolean durum = uObject.getBoolean("durum");
//                String mesaj = uObject.getString("mesaj");
//                if (durum) {
//                    Log.e("HomepageCategory.durum", "" + durum);
//                    JSONArray categoryObjects = uObject.getJSONArray("" + CategoryEnum.Categories);
////                    Log.e("HomepageCategory.categoryObjects size ",""+categoryObjects.length());
//                    categoryList = new ArrayList<>();
//                    for (int i = 0; i < categoryObjects.length(); i++) {
//                        Log.e("HomepageCategory i", "" + i);
//
//                        JSONObject jsonObject = categoryObjects.getJSONObject(i);
//                        Category category = new Category();
//                        category.setId(jsonObject.getString("" + CategoryEnum.CatogryId));
//                        category.setName(jsonObject.getString("" + CategoryEnum.CatogryName));
//                        category.setTopId(jsonObject.getString("" + CategoryEnum.TopCatogryId));
//                        categoryList.add(category);
//                    }
//
//                    Log.e("Gonderilen list size ", "" + categoryList.size());
//                    Intent intent = new Intent(HomepageCategory.this, HomepageHouseCategory.class);
//
//                    intent.putParcelableArrayListExtra("com.smtgroup.estateapplication.properties.Category", categoryList);
//                    startActivity(intent);
//
//
//                } else {
//                    Toast.makeText(HomepageCategory.this, mesaj, Toast.LENGTH_SHORT).show();
//                }
//            } catch (Exception ex) {
//                Log.e("onpostexecure error", "" + ex.toString());
//            }
//        }
//
//    }

}
