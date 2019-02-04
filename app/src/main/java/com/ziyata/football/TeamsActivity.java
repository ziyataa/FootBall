package com.ziyata.football;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ziyata.football.api.ApiClient;
import com.ziyata.football.api.ApiInterface;
import com.ziyata.football.model.Constant;
import com.ziyata.football.model.TeamsData;
import com.ziyata.football.model.TeamsResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeamsActivity extends AppCompatActivity {

    @BindView(R.id.imgLogo)
    ImageView imgLogo;
    @BindView(R.id.txtNamaTeam)
    TextView txtNamaTeam;
    @BindView(R.id.txtSejak)
    TextView txtSejak;
    @BindView(R.id.txtLiga)
    TextView txtLiga;
    @BindView(R.id.txtDeskripsi)
    TextView txtDeskripsi;

    private Bundle bundle;
    private int id;
    private ApiInterface apiInterface;
    private List<TeamsData> teamsDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teams);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        if (bundle != null){
            id = Integer.valueOf(bundle.getString(Constant.id));
        }

        teamsDataList = new ArrayList<>();

        getData();
    }

    private void getData() {
        apiInterface = ApiClient.getCliend().create(ApiInterface.class);
        Call<TeamsResponse> call = apiInterface.getDetailResponse(id);
        call.enqueue(new Callback<TeamsResponse>() {
            @Override
            public void onResponse(Call<TeamsResponse> call, Response<TeamsResponse> response) {
                TeamsResponse teamsResponse = response.body();
                teamsDataList = teamsResponse.getTeams();

                txtNamaTeam.setText(teamsDataList.get(0).getStrTeam());
                txtSejak.setText(teamsDataList.get(0).getIntFormedYear());
                txtLiga.setText(teamsDataList.get(0).getStrLeague());
                txtDeskripsi.setText(teamsDataList.get(0).getStrDescriptionEN());
                Glide.with(TeamsActivity.this).load(teamsDataList.get(0).getStrTeamBadge()).into(imgLogo);
                txtDeskripsi.setText(teamsDataList.get(0).getStrDescriptionEN());
            }

            @Override
            public void onFailure(Call<TeamsResponse> call, Throwable t) {
                Toast.makeText(TeamsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
