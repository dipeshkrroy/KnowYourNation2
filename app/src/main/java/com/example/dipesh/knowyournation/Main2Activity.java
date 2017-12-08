package com.example.dipesh.knowyournation;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    TextView txtc;
    TextView txtca;
    TextView txtp;
    TextView txta;
    TextView txtregion;
    TextView txtsubregion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
         txtc=(TextView) findViewById(R.id.txtcountry);
        txtca=(TextView) findViewById(R.id.txtcapital);
         txtp=(TextView) findViewById(R.id.txtpopu);
         txta=(TextView) findViewById(R.id.txtarea);
        txtregion=(TextView) findViewById(R.id.txtregion1);
        txtsubregion=(TextView) findViewById(R.id.txtsubregion1);

        String json=MainActivity.buffer.toString();
        try {
            JSONArray array= new JSONArray(json);
            JSONObject obj=array.getJSONObject(MainActivity.index);
            String name= obj.getString("name");
            String capital=obj.getString("capital");
            int population=obj.getInt("population");
            double area=obj.getDouble("area");
            txtc.setText(name);
            txtca.setText(capital);
           txtp.setText(String.valueOf(population));
            txta.setText(String.valueOf(area));
            txtregion.setText(obj.getString("region"));
            txtsubregion.setText(obj.getString("subregion"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
