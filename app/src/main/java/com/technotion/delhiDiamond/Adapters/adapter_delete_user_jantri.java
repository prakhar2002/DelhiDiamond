package com.technotion.delhiDiamond.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.technotion.delhiDiamond.DeleteUserJantri;
import com.technotion.delhiDiamond.Delete_jantri_of_user;
import com.technotion.delhiDiamond.R;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adapter_delete_user_jantri  extends RecyclerView.Adapter<adapter_delete_user_jantri.viewHolder> {

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView user_name,player_coins;
        Button send_points;



        public viewHolder(@NonNull View itemView) {
            super(itemView);
            user_name =itemView.findViewById(R.id.user_name);
            player_coins=itemView.findViewById(R.id.player_current_coins);
            send_points=itemView.findViewById(R.id.send_points);
        }

    }

    ArrayList<String> mIconText = new ArrayList<>();
    ArrayList<String> id = new ArrayList<>();
    ArrayList<String> coins= new ArrayList<>();
    Context context;
    int game_type;
    public adapter_delete_user_jantri(   ArrayList<String> mIconText,ArrayList<String> id,ArrayList<String> coins,int game_type,Context context) {

        this.mIconText = mIconText;
        this.id= id;
        this.game_type=game_type;
        this.coins= coins;
        this.context = context;
    }

    @NonNull
    @Override
    public adapter_delete_user_jantri.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewfull = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_ajent_users,viewGroup,false);
        adapter_delete_user_jantri.viewHolder v = new adapter_delete_user_jantri.viewHolder(viewfull);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull final adapter_delete_user_jantri.viewHolder holder, final int i) {
      /*  Glide.with(context).asBitmap().load(mIcons.get(i)).into(viewHolder.imageView);
        viewHolder.textView.setText(mIconText.get(i));

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        holder.user_name.setText(mIconText.get(i));
        holder.player_coins.setText(coins.get(i));
        holder.send_points.setText("Delete");
        holder.send_points.setOnClickListener(new View.OnClickListener() {
            String get_id=id.get(i);
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("LOADING...");
                progressDialog.show();
                SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                final String user_type = sharedPreferences.getString(Config.USER_STATUS,"Not Available");
                String url ="";
                Map<String, String> params = new HashMap<String, String>();

                if(user_type.equals("admin")){
                    url = "https://delhidiamond.online/index.php/delete-user-jantri ";


                }
                String game = String.valueOf(game_type);
                params.put("user_id", get_id);
                params.put("game_type",game);

                CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response: ", response.toString());
                        progressDialog.dismiss();
                        Intent intent = new Intent(context, DeleteUserJantri.class);
                        context.startActivity(intent);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError response) {
                        Log.d("Response: ", response.toString());
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                jsObjRequest.setRetryPolicy(new DefaultRetryPolicy( 5000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                requestQueue.add(jsObjRequest);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mIconText.size();
    }

}

