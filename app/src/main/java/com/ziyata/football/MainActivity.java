package com.ziyata.football;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.widget.Toast;

import com.ziyata.football.adapter.AdapterFootball;
import com.ziyata.football.api.ApiClient;
import com.ziyata.football.api.ApiInterface;
import com.ziyata.football.model.Constant;
import com.ziyata.football.model.TeamsData;
import com.ziyata.football.model.TeamsResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rvTeams)
    RecyclerView rvTeams;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swiperefresh;

    private List<TeamsData> dataResponseList;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        dataResponseList = new ArrayList<>();
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();

            }
        });

        getData();

        showProgress();
    }

    private void showProgress() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    private void getData() {
        apiInterface = ApiClient.getCliend().create(ApiInterface.class);
        retrofit2.Call<TeamsResponse> call = apiInterface.getTeamResponse(Constant.l);
        call.enqueue(new Callback<TeamsResponse>() {
            @Override
            public void onResponse(retrofit2.Call<TeamsResponse> call, Response<TeamsResponse> response) {
                progressDialog.dismiss();
                swiperefresh.setRefreshing(false);
                TeamsResponse teamsResponse = response.body() ;
                dataResponseList = teamsResponse.getTeams();

                rvTeams.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                rvTeams.setAdapter(new AdapterFootball(MainActivity.this, dataResponseList));

            }

            @Override
            public void onFailure(retrofit2.Call<TeamsResponse> call, Throwable t) {
                progressDialog.dismiss();
                swiperefresh.setRefreshing(false);
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
