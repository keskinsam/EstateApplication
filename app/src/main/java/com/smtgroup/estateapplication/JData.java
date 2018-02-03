package com.smtgroup.estateapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.HashMap;

/**
 * Author Tugay Demirel.
 */

public class JData extends AsyncTask<Void, Void, Void> {

    private String url = "";
    private HashMap<String, String> hashMap = null;
    private ProgressDialog progressDialog = null;
    private String data = "";
    private Context context;
    private JSONObject object = null;

    public JData(String url, HashMap<String, String> hashMap, Context context) {
        this.url = url;
        this.hashMap = hashMap;
        this.context = context;

        progressDialog = ProgressDialog.show(context, "Yükleniyor", "Islem Yapılırken Bekleyiniz", true);
    }

    @Override
    protected void onPreExecute() {
        Log.d("Jdata Sinifi", "onpreexecute");
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.d("Jdata Sinifi", "doInBackground");
        try {
            data = Jsoup.connect(url).data(hashMap).timeout(30000).ignoreContentType(true).execute().body();
        } catch (Exception ex) {
            Log.e("Get data error", "" + ex);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.d("Jdata Sinifi", "onPostExecute");
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        try {
            object = new JSONObject(data);
            if (object != null)
                Log.d("Jdata Sinifi", "object nesnesi "+object.getJSONArray("user").length());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public JSONObject getJsonObject() {
        Log.d("Jdata sinifi", "getjsonobject");
        return object;
    }
}
