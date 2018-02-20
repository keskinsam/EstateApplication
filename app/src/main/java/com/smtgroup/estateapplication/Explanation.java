package com.smtgroup.estateapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Explanation extends AppCompatActivity {

    @BindView(R.id.txtExplanation)
    TextView txtExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation);

        ButterKnife.bind(this);

        String strDetail = getIntent().getExtras().getString("detail");

        Log.d("detail", strDetail);

        txtExp.setMovementMethod(new ScrollingMovementMethod());
        txtExp.setText(strDetail);

    }
}
