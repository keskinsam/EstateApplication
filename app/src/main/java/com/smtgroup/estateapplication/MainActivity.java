package com.smtgroup.estateapplication;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.txtUserName) EditText txtUserName;
    @BindView(R.id.txtPassword) EditText txtPassword;
    @BindView(R.id.btnSignIn) Button btnSignIn;
    @BindView(R.id.btnSignUp) Button btnSignUp;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO shared preferences ile kontrolleri yap

        sharedPreferences = getSharedPreferences(""+ConstantsEnum.xmlFileName, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String strControl = sharedPreferences.getString("UserId","");



    }
}
