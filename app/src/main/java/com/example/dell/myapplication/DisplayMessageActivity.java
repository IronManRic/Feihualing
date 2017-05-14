package com.example.dell.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DisplayMessageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        InputStream inputStream = getResources().openRawResource(R.raw.tssbs);
        try {
            Intent intent = getIntent();
            String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
            InputStreamReader reader = new InputStreamReader(inputStream,"UTF-8");
            BufferedReader br = new BufferedReader(reader);
            String line ,ans=""+"\n", title1="",title2="";
            int i,position1=0;
            int[] num=new int[300];

            while ((line = br.readLine())!= null) {
                int index;
                if((index=line.indexOf('：')) != -1 ){
                    title1=line.substring(3,index);
                    title2=line.substring(index+1);
                }

                if (line.contains(message)&&(line.charAt(0) < '0' || line.charAt(0) > '9')) {
                    i = line.indexOf(message) + ans.length();
                    ans += line + '\n'
                            +"\t"+"——"+title1+"《"+title2+"》"+'\n';
                    num[position1] = i;
                    position1++;
                }
            }
            br.close();
            SpannableStringBuilder style = new SpannableStringBuilder(ans);
             for(int j=0; j<position1; j++) {
                   style.setSpan(new ForegroundColorSpan(Color.RED), num[j], num[j]+message.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
             }
            TextView textView = new TextView(this);
            textView.setText(style);
            textView.setTextSize(16);
            textView.setMovementMethod(ScrollingMovementMethod.getInstance());

            RelativeLayout layout = (RelativeLayout) findViewById(R.id.content);
            layout.addView(textView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
