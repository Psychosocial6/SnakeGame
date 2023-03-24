package com.example.snakegame;

public class ScoreForDB {
    long id;
    int points;
    public ScoreForDB(long id, int points) {
        this.id = id;
        this.points = points;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public long getId() {
        return id;
    }
    public int getPoints() {
        return points;
    }
}
