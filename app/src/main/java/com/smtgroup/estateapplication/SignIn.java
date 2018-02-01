package com.smtgroup.estateapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignIn extends AppCompatActivity {

    @BindView(R.id.txtEmail)
    EditText txtEmail;
    @BindView(R.id.txtPassword)
    EditText txtPassword;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);

        sharedPreferences = getSharedPreferences("" + ConstantsEnum.xmlFileName, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        Log.d("Neredeyim", "SingIn.java");
        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("sign in ", "on click");
                email = txtEmail.getText().toString();
                password = txtPassword.getText().toString();
                signIn();
            }
        });
    }

    public void signIn() {
        Log.d("neredeyim", "register");
        Log.d("email", email);
        Log.d("password", password);

        HashMap<String, String> hmRegister = new HashMap<>();
        hmRegister.put("ref", "3d264cacec20af4f9b237a655f49bc60");
        hmRegister.put("userEmail", email);
        hmRegister.put("userPass", password);
        hmRegister.put("face", "no");

        Log.d("hasmap uzunlugu", "" + hmRegister.size());

        String url = "http://jsonbulut.com/json/userLogin.php";
        new JData(url, hmRegister, SignIn.this).execute();
    }

    class JData extends AsyncTask<Void, Void, Void> {

        String url = "";
        HashMap<String, String> hashMap = null;
        ProgressDialog progressDialog = null;
        String data = "";
        Context context;

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
                Log.d("jData doInBackgroundns", hashMap.get("userEmail"));
                Log.d("jData doInBackground", hashMap.get("userPass"));
                data = Jsoup.connect(url).data(hashMap).timeout(30000).ignoreContentType(true).execute().body();
                Log.d("jData doInBackgroundns", data);
            } catch (Exception ex) {
                Log.e("Get data error", "" + ex.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.d("Jdata Sinifi", "onPostExecute");
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            try {
                JSONObject object  = new JSONObject(data);
                JSONObject uObject = object.getJSONArray("user").getJSONObject(0);

                boolean durum = uObject.getBoolean("durum");
                String mesaj = uObject.getString("mesaj");
                if (durum) {
                    Log.d("neredeyim", "onpostexecute - > durum true");
                    JSONObject informsObject = uObject.getJSONObject("bilgiler");
                    Toast.makeText(SignIn.this, mesaj + " " + informsObject.getString("userId"), Toast.LENGTH_SHORT).show();

                    editor.putString("userEmail", email);
                    editor.putString("userPass", password);
                    editor.commit();

                    Intent intent = new Intent(SignIn.this, deneme.class);
                    startActivity(intent);
                    SignIn.this.finish();
                } else {
                    Log.d("neredeyim", "onpostexecute - > durum false");
                    Toast.makeText(SignIn.this, mesaj, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Log.e("onpostexecure error", "" + ex.toString());
            }
        }

    }
}
