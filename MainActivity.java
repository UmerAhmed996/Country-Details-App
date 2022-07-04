package com.example.listview;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    ArrayList < String > grocery = new ArrayList < > ();
    List < String > grocery1 = new ArrayList < > ();
    int index;
    String selection;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        }
        grocery1 = readText();
        grocery = new ArrayList <String> (grocery1);

        final ListView myListView = findViewById(R.id.myListView1);
        final ArrayAdapter <String> arrayAdapter = new ArrayAdapter < String > (this, android.R.layout.simple_list_item_1, grocery);
        myListView.setAdapter(arrayAdapter);
        myListView.requestLayout();
        arrayAdapter.notifyDataSetChanged();

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView < ? > adapterView, View view, int position, long id) {
                String selection = myListView.getItemAtPosition(position).toString();
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.putExtra("selection", selection);
                startActivity(intent);
            }
        });

        myListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView < ? > parent, View view, int position, long id) {
                index = position;
                selection = myListView.getItemAtPosition(position).toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do you want to delete " + selection + "?");
                builder.setTitle("Alert !");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        arrayAdapter.remove(grocery.remove(index));
                        str = "";
                        for (int i = 0; i < grocery.size(); i++) {
                            str+=grocery.get(i)+",";
                        }
                        str=str.substring(0, str.length() - 1);

                        Log.i("info",str);
                        File f = new File("/sdcard/Download/Resources/Country Names/Names.txt");
                        File f1 = new File("/sdcard/Download/Resources/Country Description/" + selection + ".txt");
                        File f2 = new File("/sdcard/Download/Resources/Country Flags/" + selection + ".jpg");
                        f.delete();
                        f1.delete();
                        f2.delete();
                        try {
                            FileWriter writer = new FileWriter("/sdcard/Download/Resources/Country Names/Names.txt", true);
                            writer.write(str);
                            writer.close();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                arrayAdapter.notifyDataSetChanged();
                myListView.requestLayout();
                return true;
            }
        });
    }

    private List < String > readText() {
        StringBuffer stringBuffer=null;
        String folderPath = "/sdcard/Download/Resources/Country Names/Names.txt";
        File myFile = new File(folderPath);
        try {
            FileInputStream fstream = new FileInputStream(myFile);
            stringBuffer = new StringBuffer();
            int i;
            while ((i = fstream.read()) != -1) {
                stringBuffer.append((char) i);
            }
            fstream.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        String content = stringBuffer.toString();
        String[] res = content.split("[,]", 0);
        List < String > arrayList = new ArrayList < String > ();
        arrayList = Arrays.asList(res);
        return arrayList;
    }
}