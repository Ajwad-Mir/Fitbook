package com.example.fitbook.leaderboard;

public class Leaderboard {
    private String leaderboardUserName;
    private float leaderboardTotalDistanceTravelled;

    public Leaderboard() {
    }

    public Leaderboard(String leaderboardUserName, float leaderboardTotalDistanceTravelled) {
        this.leaderboardUserName = leaderboardUserName;
        this.leaderboardTotalDistanceTravelled = leaderboardTotalDistanceTravelled;
    }

    public String getleaderboardUserName() {
        return leaderboardUserName;
    }

    public float getleaderboardTotalDistanceTravelled() {
        return leaderboardTotalDistanceTravelled;
    }

    public void setleaderboardUserName(String leaderboardUserName) {
        this.leaderboardUserName = leaderboardUserName;
    }

    public void setleaderboardTotalDistanceTravelled(float leaderboardTotalDistanceTravelled) {
        this.leaderboardTotalDistanceTravelled = leaderboardTotalDistanceTravelled;
    }
}
