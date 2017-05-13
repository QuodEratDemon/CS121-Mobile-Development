package com.dealfaro.luca.listviewexample;

import android.content.Context;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.content.Intent;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "lv-ex";

    private class ListElement {
        ListElement() {};

        ListElement(String tl, String bl, String url) {
            textLabel = tl;
            buttonLabel = bl;
            urlLabel = url;
        }

        public String textLabel;
        public String buttonLabel;
        public String urlLabel;
    }

    private ArrayList<ListElement> aList;

    private class MyAdapter extends ArrayAdapter<ListElement> {

        int resource;
        Context context;

        public MyAdapter(Context _context, int _resource, List<ListElement> items) {
            super(_context, _resource, items);
            resource = _resource;
            context = _context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout newView;

            ListElement w = getItem(position);

            // Inflate a new view if necessary.
            if (convertView == null) {
                newView = new LinearLayout(getContext());
                LayoutInflater vi = (LayoutInflater)
                        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vi.inflate(resource,  newView, true);
            } else {
                newView = (LinearLayout) convertView;
            }

            // Fills in the view.
            TextView tv = (TextView) newView.findViewById(R.id.itemText);
            TextView b = (TextView) newView.findViewById(R.id.subText);
            tv.setText(w.textLabel);
            b.setText(w.buttonLabel);
            final String send_url = w.urlLabel;
            /*
            // Sets a listener for the button, and a tag for the button as well.
            b.setTag(new Integer(position));
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Reacts to a button press.
                    // Gets the integer tag of the button.
                    String s = v.getTag().toString();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, s, duration);
                    toast.show();
                    // Let's remove the list item.
                    int i = Integer.parseInt(s);
                    //aList.remove(i);
                    aa.notifyDataSetChanged();
                }
            });
            */
            // Set a listener for the whole list item.
            newView.setTag(w.textLabel);
            newView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainActivity.this, Reader2Activity.class);
                    i.putExtra("URL", send_url);
                    String s = v.getTag().toString();
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, s, duration);
                    toast.show();
                    startActivity(i);


                }
            });

            return newView;
        }
    }

    private MyAdapter aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);
        aList = new ArrayList<ListElement>();
        aa = new MyAdapter(this, R.layout.list_element, aList);
        ListView myListView = (ListView) findViewById(R.id.listView);
        myListView.setAdapter(aa);
        aa.notifyDataSetChanged();
    }

    @Override
    protected void onResume (){
        super.onResume();
        getMessages();
    }

    public void clickRefresh (JSONArray ss) throws JSONException, IOException {
        Log.i(LOG_TAG, "Requested a refresh of the list");

        int n = ss.length();
        JSONObject temp =null;
        aList.clear();


        for (int i = 0; i < n; i++) {
            temp = ss.getJSONObject(i);
            //test = temp.getString("url");
            if ( temp.getString("url") != "null" && temp.getString("title") != "null" && temp.getString("subtitle") != "null"){
                aList.add(new ListElement(
                        temp.getString("title"),  temp.getString("subtitle"), temp.getString("url")

                ));
            }
        }

        aa.notifyDataSetChanged();
    }

    RequestQueue queue;
    private void getMessages() {

        // Instantiate the RequestQueue.
        String url = "https://luca-ucsc-teaching-backend.appspot.com/hw4/get_news_sites";

        final String my_url = url ;// + "?token=" + URLEncoder.encode(recipient);

        //final TextView getResponse = (TextView) findViewById(R.id.getText);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, my_url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOG_TAG, "Received: " + response.toString());
                        JSONArray result = null;
                        try {
                            result = response.getJSONArray("news_sites");
                            Log.d(LOG_TAG, "yay");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (result == null){
                            Log.d(LOG_TAG, "no");
                        }
                        try {
                            clickRefresh(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        //getResponse.setText(result);
                        // Ok, let's disassemble a bit the json object.
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d(LOG_TAG, error.toString());
                    }
                });

        // In some cases, we don't want to cache the request.
        // jsObjRequest.setShouldCache(false);

        queue.add(jsObjRequest);
    }

    public void clickStart (View v){
        getMessages();
    }

}
