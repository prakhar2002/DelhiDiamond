package com.technotion.delhiDiamond.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.technotion.delhiDiamond.Agent_view_user_jantri;
import com.technotion.delhiDiamond.Delete_jantri_of_user;
import com.technotion.delhiDiamond.ImageNicer;
import com.technotion.delhiDiamond.R;
import com.technotion.delhiDiamond.ajent_users_list;
import com.technotion.delhiDiamond.patti_commission;
import com.technotion.delhiDiamond.sever_classes.Config;
import com.technotion.delhiDiamond.sever_classes.CustomRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class adpter_ajents_games  extends RecyclerView.Adapter<adpter_ajents_games.viewHolder> {

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
    public adpter_ajents_games(   ArrayList<String> mIconText,ArrayList<String> id,ArrayList<String> coins,int game_type,Context context) {

        this.mIconText = mIconText;
        this.id= id;
        this.game_type=game_type;
        this.coins= coins;
        this.context = context;
    }

    @NonNull
    @Override
    public adpter_ajents_games.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View viewfull = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_ajent_users,viewGroup,false);
        adpter_ajents_games.viewHolder v = new adpter_ajents_games.viewHolder(viewfull);
        return v;
    }

    @Override
    public void onBindViewHolder(@NonNull final adpter_ajents_games.viewHolder holder, final int i) {
      /*  Glide.with(context).asBitmap().load(mIcons.get(i)).into(viewHolder.imageView);
        viewHolder.textView.setText(mIconText.get(i));

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        holder.user_name.setText(mIconText.get(i));
        holder.player_coins.setText(coins.get(i));
        holder.send_points.setText("Jantri");
        holder.user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Delete_jantri_of_user.class);
                String agent_id=id.get(i);
                intent.putExtra("id",agent_id);
                intent.putExtra("game",game_type);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mIconText.size();
    }

}
