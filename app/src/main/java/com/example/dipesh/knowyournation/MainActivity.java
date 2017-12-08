package com.example.dipesh.knowyournation;



import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.CoordinatorLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.BaseAdapter;
import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity {
    ListView lst;

    static StringBuffer buffer;
    static int index;
    static int count=0;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coord);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Loading Data....", Snackbar.LENGTH_LONG);
        snackbar.show();
        lst=(ListView) findViewById(R.id.listview);
                JsonDown js=new JsonDown();
               js.execute("https://restcountries.eu/rest/v2/all");
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(intent);
                index=i;

            }
        });

    }
    public  class JsonDown extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... urls) {
            HttpURLConnection con=null;
            BufferedReader reader=null;

            try {
                URL url=new URL(urls[0]);
                con=(HttpURLConnection) url.openConnection();
                con.connect();
                InputStream stream=con.getInputStream();
                reader=new BufferedReader(new InputStreamReader(stream));
                 buffer=new StringBuffer();
                String line="";
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }
                String json=buffer.toString();
                JSONArray array= new JSONArray(json);
               for (int i=0;i<array.length();i++){
                          count++;// count of number of country or object
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            CustomAdapter ad=new CustomAdapter();
            lst=(ListView) findViewById(R.id.listview);
              lst.setAdapter(ad);
            super.onPostExecute(s);
        }
    }


    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View viewResult=getLayoutInflater().inflate(R.layout.da_item,null);
            TextView country=(TextView) viewResult.findViewById(R.id.country);
            TextView capital=(TextView) viewResult.findViewById(R.id.capital);
            ImageView img=(ImageView) viewResult.findViewById(R.id.img);


            String json=MainActivity.buffer.toString();
            JSONArray array= null;
            try {
                array = new JSONArray(json);
                JSONObject obj=array.getJSONObject(i);
                String name= obj.getString("name");
                String cap=obj.getString("capital");
                country.setText(name);
                capital.setText(cap);
                String imageUri =obj.getString("flag");
                Glide.with(getApplicationContext()).load("https://theflagcompany.in/wp-content/uploads/2016/01/indian-cross-table-flag-with-acrylic-base-and-stainless-steel-pole.jpg") // image url
                        .placeholder(R.drawable.flag) // any placeholder to load at start
                        .error(R.drawable.flag).centerCrop().into(img);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return viewResult;
        }
    }
}
