package com.technotion.delhiDiamond;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.Adapters.Ajent_user_points_working;
import com.technotion.delhiDiamond.Adapters.adpter_ajents_games;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DeleteUserJantri extends AppCompatActivity {
    ArrayList<String> myImageList = new ArrayList<>();
    ArrayList<String> users_id = new ArrayList<>();
    ArrayList<String> users_coins = new ArrayList<>();
    ArrayAdapter shortcuts;
    int view_list=0;
    Spinner shortcuts_spinner;
    String jantri_array[] ={"--Select Game--","Delhi Diamond","Faridabad","Ghaziabad","Gali","Desawar"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user_jantri);
        shortcuts_spinner = findViewById(R.id.planets_spinner);
        shortcuts = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,jantri_array);

        shortcuts_spinner.setAdapter(shortcuts);
        shortcuts_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        view_list = 0;
                        break;
                    case 1:
                        view_list = 4;
                        fun();
                        break;
                    case 2:
                        view_list = 5;
                        fun();
                        break;
                    case 3:
                        view_list = 6;
                        fun();
                        break;
                    case 4:
                        view_list = 7;
                       fun(); 
                        break;
                    case 5:
                        view_list = 8;
                        fun();
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void fun() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("LOADING...");
        progressDialog.show();
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final String user_type = sharedPreferences.getString(Config.USER_STATUS,"Not Available");
        String url ="";
        Map<String, String> params = new HashMap<String, String>();
        String agent_id="";

        if(user_type.equals("admin")){
            url = "https://delhidiamond.online/index.php/get-agents-by-game ";
            agent_id = sharedPreferences.getString(Config.PLAYER_ID,"-1");

        }
        String game = String.valueOf(view_list);
        params.put("game_type",game);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("Response: ", response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("response");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject employee = jsonArray.getJSONObject(i);
                        /* if(user_type.equals("admin")) {
                         *//*    if (i != 0) {
                               String id = employee.getString("id");
                               *//**//*                        String username = employee.getString("username");*//**//*
                               String user_coins = employee.getString("user_coins");
                               myImageList.add(employee.getString("username"));
                               users_id.add(id);
                               users_coins.add(user_coins);
                           }
                       }else{*//*
                           String id = employee.getString("id");
                           *//*                        String username = employee.getString("username");*//*
                           String user_coins = employee.getString("user_coins");
                           myImageList.add(employee.getString("username"));
                           users_id.add(id);
                           users_coins.add(user_coins);
                       }else {*/
                        String id = employee.getString("agent_id");
                        /*                        String username = employee.getString("username");*/
                        String user_coins ="-";
                        myImageList.add(employee.getString("name"));
                        users_id.add(id);
                        users_coins.add(user_coins);

                    }
                    progressDialog.dismiss();
                    usersendpoints();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsObjRequest);
    }

    private void usersendpoints() {
        RecyclerView.LayoutManager manager = new GridLayoutManager(getApplicationContext(),1);
        RecyclerView vieww= findViewById(R.id.user_points_working);
        vieww.setHasFixedSize(true);
        vieww.setLayoutManager(manager);
        Context context =DeleteUserJantri.this;
        Collections.reverse(myImageList);
        Collections.reverse(users_id);
        Collections.reverse(users_coins);

        adpter_ajents_games attributes= new adpter_ajents_games(myImageList,users_id,users_coins,view_list,context);

        vieww.setAdapter(attributes);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {
            Intent intent = new Intent(DeleteUserJantri.this, MainDashBoard.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
