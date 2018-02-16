package com.smtgroup.estateapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mDemoSlider = findViewById(R.id.slider);

        try {
            JSONArray images = udata.getJSONArray("images");
            HashMap<String,String> url_maps = new HashMap<String, String>();
            String uTitle = udata.getString("productName");
            String uPrice = udata.getString("price");
            String uAddress = udata.getString("brief");
            String uDetail = udata.getString("description");
            Log.e("Images length : ",""+images.length());
            for(int i=0;i<images.length();i++) {
                String resim= images.getJSONObject(i).getString("normal");
                Log.e("i =",""+i);
                url_maps.put(""+  i, resim);
            }
            Log.e("URLMAP : ",""+url_maps.size());
            for(String name : url_maps.keySet()){
                TextSliderView textSliderView = new TextSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .description(name)
                        .image(url_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                Log.e("FOTO :",""+url_maps.get(name));

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putString("extra",name);
                mDemoSlider.setPresetTransformer("Tablet");
                mDemoSlider.addSlider(textSliderView);
            }
            title.setText(uTitle);
            price.setText(uPrice);
            address.setText(uAddress);
            detail.loadDataWithBaseURL("",uDetail,"text/html","UTF-8","");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        udata = null;

    }
}
