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

import com.smtgroup.estateapplication.enums.ConstantsEnum;
import com.smtgroup.estateapplication.enums.UserEnum;
import com.smtgroup.estateapplication.properties.User;

import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Tugay Demirel.
 *
 */

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

        btnSignIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                email = txtEmail.getText().toString();
                password = txtPassword.getText().toString();
                signIn();
            }
        });
    }

    public void signIn() {
        HashMap<String, String> hmRegister = new HashMap<>();
        hmRegister.put("ref", "3d264cacec20af4f9b237a655f49bc60");
        hmRegister.put("userEmail", email);
        hmRegister.put("userPass", password);
        hmRegister.put("face", "no");

        String url = "http://jsonbulut.com/json/userLogin.php";
        new JData(url, hmRegister, SignIn.this).execute();
    }

    class JData extends AsyncTask<Void, Void, Void> {

        String url = "";
        HashMap<String, String> hashMap = null;
        ProgressDialog progressDialog = null;
        String data = "";
        Context context;

        private JData(String url, HashMap<String, String> hashMap, Context context) {
            this.url = url;
            this.hashMap = hashMap;
            this.context = context;
            progressDialog = ProgressDialog.show(context, getString(R.string.str_loading), getString(R.string.str_loading), true);
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
            progressDialog.dismiss();
            try {
                JSONObject object  = new JSONObject(data);
                JSONObject uObject = object.getJSONArray("user").getJSONObject(0);

                boolean durum = uObject.getBoolean("durum");
                String mesaj = uObject.getString("mesaj");
                if (durum) {
                    JSONObject informsObject = uObject.getJSONObject("bilgiler");
                    Toast.makeText(SignIn.this, mesaj + " " + informsObject.getString("userId"), Toast.LENGTH_SHORT).show();

                    editor.putString("userEmail", email);
                    editor.putString("userPass", password);
                    editor.commit();

                    User user = new User();
                    user.setName(informsObject.getString(""+ UserEnum.userName));
                    user.setSurname(informsObject.getString(""+ UserEnum.userSurname));
                    user.setEmail(informsObject.getString(""+ UserEnum.userEmail));
                    user.setPhone(informsObject.getString(""+ UserEnum.userPhone));


                    Intent intent = new Intent(SignIn.this, HomepageType.class);
                    intent.putExtra("current_user", user);
                    startActivity(intent);
                    SignIn.this.finish();

                } else {
                    Toast.makeText(SignIn.this, mesaj, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Log.e("onpostexecure error", "" + ex.toString());
            }
        }

    }
}
