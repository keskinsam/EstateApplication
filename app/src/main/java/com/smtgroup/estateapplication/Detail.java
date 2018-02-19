package com.smtgroup.estateapplication;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.smtgroup.estateapplication.database.DB;
import com.smtgroup.estateapplication.enums.ProductEnum;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Detail extends AppCompatActivity {

    @BindView(R.id.addTitle)
    TextView title;
    @BindView(R.id.addPrice)
    TextView price;
    @BindView(R.id.addAddress)
    TextView address;
    @BindView(R.id.addDetail)
    WebView detail;

    static JSONObject udata = null;

    private SliderLayout mDemoSlider;
    private String productId;

    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mDemoSlider = findViewById(R.id.slider);

        Log.e("Location", "Detail page baslangic");


        try {
            JSONArray images = udata.getJSONArray("images");
            HashMap<String, String> url_maps = new HashMap<String, String>();
            String uTitle = udata.getString("productName");
            String uPrice = udata.getString("price");
            String uAddress = udata.getString("brief");
            String uDetail = udata.getString("description");
            Log.e("Images length : ", "" + images.length());
            for (int i = 0; i < images.length(); i++) {
                String resim = images.getJSONObject(i).getString("normal");
                Log.e("i =", "" + i);
                url_maps.put("" + i, resim);
            }
            Log.e("URLMAP : ", "" + url_maps.size());
            for (String name : url_maps.keySet()) {
                TextSliderView textSliderView = new TextSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .description(name)
                        .image(url_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                Log.e("FOTO :", "" + url_maps.get(name));

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putString("extra", name);
                mDemoSlider.setPresetTransformer("Tablet");
                mDemoSlider.addSlider(textSliderView);
            }
            title.setText(uTitle);
            price.setText(uPrice);
            address.setText(uAddress);
            detail.loadDataWithBaseURL("", uDetail, "text/html", "UTF-8", "");

            Log.e("location", "fav controle girmeden once");
//            favControl();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        udata = null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.e("Location", "onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.detail, menu);
        favControl();

        return true;
    }

    private boolean isfav = false;
    private MenuItem item;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.item = item;
        if (isfav) {
            if (deleteFav()) {
                Toast.makeText(this, "Favorilerden Cıkarıldı...", Toast.LENGTH_SHORT).show();
                item.setIcon(ContextCompat.getDrawable(this, android.R.drawable.star_off));
                isfav = false;
            }
        } else {
            if (addFav()) {
                Toast.makeText(this, "Favorilere Eklendi...", Toast.LENGTH_SHORT).show();
                item.setIcon(ContextCompat.getDrawable(this, android.R.drawable.star_on));
                isfav = true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    SQLiteDatabase db;

    public void favControl() {
        Log.e("favcontrol()", "");
        db = new DB(this).getReadableDatabase();
        Cursor cursor = db.query("favories", null, null, null, null, null, null);
        try {
            productId = udata.getString("" + ProductEnum.productId);
            while (cursor.moveToNext()) {

                if (productId.equals(cursor.getString(0))) {
                    Log.e("control()", "Product id databasede kayitli");
                    MenuItem item = menu.findItem(R.id.action_fav);
                    isfav = true;
                    item.setIcon(ContextCompat.getDrawable(this, android.R.drawable.star_on));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        db.close();
    }

    public boolean addFav() {
        Log.e("Location", "addFav()");
        db = new DB(Detail.this).getWritableDatabase();
        ContentValues data = new ContentValues();

        data.put("" + ProductEnum.product_id, productId);
        Long isWrote = db.insert("" + ProductEnum.favories, null, data);
        if (isWrote > 0) {
            Log.e("Location", "Ekleme basarili");
            db.close();
            return true;
        }
        return false;
    }

    public boolean deleteFav(){
        Log.e("Location", "deleteFav");
        db = new DB(Detail.this).getWritableDatabase();

        int isDelete = db.delete(""+ProductEnum.favories, ""+ProductEnum.product_id + "=" + productId , null);

        return isDelete > 0;
    }


}
