package com.smtgroup.estateapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.HashMap;

public class AdPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static String adCategory = "";
    static String adType = "";

    ListView liste;
    BaseAdapter adp;
    LayoutInflater linf;
    JSONArray larr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //http://jsonbulut.com/json/product.php?ref=3d264cacec20af4f9b237a655f49bc60&start=0
        String url = "http://jsonbulut.com/json/product.php";
        HashMap<String,String> hm = new HashMap<>();
        hm.put("ref","3d264cacec20af4f9b237a655f49bc60");
        hm.put("start","0");
        new jData(url,hm,this).execute();

        liste = findViewById(R.id.adList);
        linf = LayoutInflater.from(this);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    class jData extends AsyncTask<Void, Void, Void> {
        String url = "";
        HashMap<String,String> hm = null;
        ProgressDialog pro = null;
        String data = "";
        Context cnx;
        public jData (String url, HashMap<String, String> hm, Context cnx) {
            this.url = url;
            this.hm = hm;
            this.cnx = cnx;
            pro = ProgressDialog.show(cnx, "Yükleniyor", "İşlem Yapılırken Bekleyiniz", true);
        }


        // 1
        @Override
        protected void onPreExecute() {
            super.onPreExecute();// ilk çalışan method
        }

        // 2 - dataların yanıtlarını bekler
        @Override
        protected Void doInBackground(Void... voids) {
            // jsoup
            // url, HashMap
            try {
                data = Jsoup.connect(url).data(hm).timeout(30000).ignoreContentType(true).execute().body();
            }catch (Exception ex) {
                System.err.println("Data Getirme Hatasi : " + ex);
            }
            return null;
        }

        // 3 - datalar geldi artık işlemleri yap
        @Override
        protected void onPostExecute(Void aVoid) {
            // grafiksel işlemler yapılır.
            // yeni sayfa aç yükleyiciyi durudr.
            super.onPostExecute(aVoid);
            pro.dismiss();
            //Log.d("Gelen Data ", data);
            try {
                JSONObject obj = new JSONObject(data);
                boolean durum = obj.getJSONArray("Products").getJSONObject(0).getBoolean("durum");
                String mesaj = obj.getJSONArray("Products").getJSONObject(0).getString("mesaj");
                if (durum) {
                    // işlem başarılı
                    larr = obj.getJSONArray("Products").getJSONObject(0).getJSONArray("bilgiler");
                    Log.e("Array :",""+larr.length());
                    if(larr.length() > 0) {
                        // ürün var
                        arrayFilter();
                        dataDoldur();
                    }else {
                        Toast.makeText(cnx, "Henüz bir ürün yok !", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(cnx, mesaj, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
        getMenuInflater().inflate(R.menu.ad_page, menu);
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


    public void dataDoldur() {


        // larr
        adp = new BaseAdapter() {
            @Override
            public int getCount() {
                return larr.length();
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                if (view == null) {
                    view = linf.inflate(R.layout.adpage_row, null);
                }



                try {


                    ImageView img = view.findViewById(R.id.adImage);
                    TextView title = view.findViewById(R.id.adTitle);
                    TextView price = view.findViewById(R.id.adPrice);

                    JSONObject uo = larr.getJSONObject(i);
                    title.setText(uo.getString("productName"));
                    price.setText(uo.getString("price")+" TL");

                    // resim gösterimi
                    boolean imgControl = uo.getBoolean("image");
                    if(imgControl) {
                        String rurl = uo.getJSONArray("images").getJSONObject(0).getString("normal");
                        Picasso.with(AdPage.this) .load(rurl) .resize(75, 75) .centerCrop() .into(img);
                    }

                }catch (Exception ex) {
                    Log.e("Hata", ex.toString());
                }
                return view;
            }
        };
        liste.setAdapter(adp);
        liste.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    Detail.udata = larr.getJSONObject(i);
                    Log.d("UDATA :",""+Detail.udata);
                    Intent ii = new Intent(AdPage.this, Detail.class);
                    startActivity(ii);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void arrayFilter()  {
        for (int i=larr.length()-1;i>=0;i--) {
            try {
                if(!larr.getJSONObject(i).getJSONObject("saleInformation").getString("saleType").equals(adType)) {
                    larr.remove(i);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for (int i=larr.length()-1;i>=0;i--) {
            try {
                if(!larr.getJSONObject(i).getJSONArray("categories").getJSONObject(0).getString("categoryName").equals(adCategory)) {
                    larr.remove(i);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
