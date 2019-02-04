package com.ziyata.football.api;

import com.ziyata.football.model.TeamsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/api/v1/json/1/search_all_teams.php")
    Call<TeamsResponse> getTeamResponse(
            @Query("l") String l

    );

    @GET("/api/v1/json/1/lookupteam.php")
    Call<TeamsResponse> getDetailResponse(
            @Query("id") int id
    );
}
