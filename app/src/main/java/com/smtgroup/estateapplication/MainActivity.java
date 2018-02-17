package com.smtgroup.estateapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.smtgroup.estateapplication.enums.ConstantsEnum;
import com.smtgroup.estateapplication.enums.UserEnum;
import com.smtgroup.estateapplication.properties.User;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.HashMap;

/**
 * @author Tugay Demirel.
 *
 */

public class MainActivity extends AppCompatActivity {


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private String email;
    private String password;

    static User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("" + ConstantsEnum.xmlFileName, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String strEmail = sharedPreferences.getString("userEmail", "");

        if (!strEmail.equals("")) {
            email = strEmail;
            password = sharedPreferences.getString("userPass", "");

            entry();
        }else{
            Intent intent = new Intent(MainActivity.this, SignIn.class);
            startActivity(intent);
            MainActivity.this.finish();
        }
    }

    public void entry() {
        HashMap<String, String> hmRegister = new HashMap<>();
        hmRegister.put("ref", "3d264cacec20af4f9b237a655f49bc60");
        hmRegister.put("userEmail", email);
        hmRegister.put("userPass", password);
        hmRegister.put("face", "no");

        String url = "http://jsonbulut.com/json/userLogin.php";
        new JData(url, hmRegister, MainActivity.this).execute();
    }

    class JData extends AsyncTask<Void, Void, Void> {

        String url = "";
        HashMap<String, String> hashMap = null;
        String data = "";
        Context context;

        public JData(String url, HashMap<String, String> hashMap, Context context) {
            this.url = url;
            this.hashMap = hashMap;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                data = Jsoup.connect(url).data(hashMap).timeout(30000).ignoreContentType(true).execute().body();
            } catch (Exception ex) {
                Log.e("Get data error", "" + ex.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                JSONObject object  = new JSONObject(data);
                JSONObject uObject = object.getJSONArray(""+UserEnum.user).getJSONObject(0);

                boolean durum = uObject.getBoolean("durum");
                String mesaj = uObject.getString("mesaj");
                if (durum) {
                    JSONObject informsObject = uObject.getJSONObject("bilgiler");
                    Toast.makeText(MainActivity.this, mesaj + " " + informsObject.getString(""+ UserEnum.userId), Toast.LENGTH_SHORT).show();

                    editor.putString("userEmail", email);
                    editor.putString("userPass", password);
                    editor.commit();

                    user.setName(informsObject.getString(""+ UserEnum.userName));
                    user.setSurname(informsObject.getString(""+ UserEnum.userSurname));
                    user.setEmail(informsObject.getString(""+ UserEnum.userEmail));
                    user.setPhone(informsObject.getString(""+ UserEnum.userPhone));

                    Log.e("location","Main activitiy'den user nesnesi gonderildi: " + user.getName());

                    Intent intent = new Intent(MainActivity.this, HomepageCategory.class);
                    intent.putExtra("current_user", user);

                    startActivity(intent);
                    MainActivity.this.finish();
                } else {
                    Toast.makeText(MainActivity.this, mesaj, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Log.e("onpostexecure error", "" + ex.toString());
            }
        }
    }
}