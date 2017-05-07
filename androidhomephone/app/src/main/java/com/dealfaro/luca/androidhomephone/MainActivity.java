package com.dealfaro.luca.androidhomephone;

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

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
//import static com.dealfaro.luca.androidhomephone.R.id.detailView;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "androidhomephone";

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue = Volley.newRequestQueue(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // We need the "final" keyword here to guarantee that the
        // value does not change, as it is used in the callbacks.

        //getList();
        //sendMsg("Anna", "Clara", "Tschuss!");
        //getMessages("mario");
    }

    public void setPostText(String s){
        TextView postResponse = (TextView) findViewById(R.id.getText);

        JSONObject jsonResult = null;
        String result = null;
        try {
            jsonResult = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            result = jsonResult.getString("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postResponse.setText(result);


    }

    private void sendMsg(final String msg) {
        //final TextView postResponse = (TextView) findViewById(R.id.getText);


        StringRequest sr = new StringRequest(Request.Method.POST,
                "https://luca-ucsc-teaching-backend.appspot.com/hw3/request_via_post",
                new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(LOG_TAG, "Got:" + response);

                setPostText(response);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("token", msg);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);

    }


    public void getRequest(View V){
        String token = "abracadabra";
        getMessages(token);

    }

    private void getMessages(final String recipient) {

        // Instantiate the RequestQueue.
        String url = "https://luca-ucsc-teaching-backend.appspot.com/hw3/request_via_get";

        final String my_url = url + "?token=" + URLEncoder.encode(recipient);

        final TextView getResponse = (TextView) findViewById(R.id.getText);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, my_url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(LOG_TAG, "Received: " + response.toString());
                        String result = null;
                        try {
                            result = response.getString("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        getResponse.setText(result);
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

    public void postRequest(View V){
        String token = "abracadabra";
        sendMsg(token);

    }


}
