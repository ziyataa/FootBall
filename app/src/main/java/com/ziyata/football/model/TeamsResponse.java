package com.ziyata.football.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TeamsResponse {
    @SerializedName("teams")
    private List<TeamsData> teams;

    public List<TeamsData> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamsData> teams) {
        this.teams = teams;
    }
}
