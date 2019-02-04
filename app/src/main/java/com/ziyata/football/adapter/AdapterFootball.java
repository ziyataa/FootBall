package com.ziyata.football.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ziyata.football.R;
import com.ziyata.football.TeamsActivity;
import com.ziyata.football.model.Constant;
import com.ziyata.football.model.TeamsData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdapterFootball extends RecyclerView.Adapter<AdapterFootball.ViewHolder> {

    private Bundle bundle;
    private Context context;
    private final List<TeamsData> teamsDataList;

    public AdapterFootball(Context context, List<TeamsData> teamsDataList) {
        this.context = context;
        this.teamsDataList = teamsDataList;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final TeamsData dataResponse = teamsDataList.get(i);
        viewHolder.txtNamaTeam.setText(dataResponse.getStrTeam());
        Glide.with(context).load(dataResponse.getStrTeamBadge()).into(viewHolder.imgAvatar);
        RequestOptions options = new RequestOptions().error(R.drawable.ic_broken_image);
        Glide.with(context).load(dataResponse.getStrTeamBadge()).apply(options).into(viewHolder.imgAvatar);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                bundle.putString(Constant.id, dataResponse.getIdTeam());
                context.startActivity(new Intent(context, TeamsActivity.class).putExtras(bundle));
            }
        });



    }

    @Override
    public int getItemCount(){
      return teamsDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imgAvatar)
        ImageView imgAvatar;
        @BindView(R.id.txtNamaTeam)
        TextView txtNamaTeam;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
