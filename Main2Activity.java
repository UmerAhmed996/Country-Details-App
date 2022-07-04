package com.example.listview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.listview.MainActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;


public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        String selection = (String) getIntent().getSerializableExtra("selection");
        TextView textView = (TextView)findViewById(R.id.textView);
        String readCountryDescription=readDescription(selection);
        textView.setText(readCountryDescription);
        TextView textView1 = (TextView)findViewById(R.id.textView1);
        textView1.setText(selection);
        ImageView imageView=(ImageView) findViewById(R.id.action_image);
        Drawable  drawable  = Drawable.createFromPath("/sdcard/Download/Resources/Country Flags/"+selection+".jpg");
        imageView.setImageDrawable(drawable);
    }

    private String readDescription(String Key) {
        File file = new File("/sdcard/Download/Resources/Country Description/"+Key+".txt");
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append("\n");
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String text_1 = text.toString();
        return text_1;
    }
}