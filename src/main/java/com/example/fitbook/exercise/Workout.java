package com.example.fitbook.exercise;

public class Workout {
    public String workoutid;
    public String workoutname;
    public int workoutreps;

    public Workout() {
    }

    public Workout(String workoutid, String workoutname, int workoutreps) {
        this.workoutid = workoutid;
        this.workoutname = workoutname;
        this.workoutreps = workoutreps;
    }

    public String getworkoutid() {
        return workoutid;
    }

    public String getworkoutname() {
        return workoutname;
    }

    public int getworkoutreps() {
        return workoutreps;
    }

    public void setworkoutid(String workoutid) {
        this.workoutid = workoutid;
    }

    public void setworkoutname(String workoutname) {
        this.workoutname = workoutname;
    }

    public void setworkoutreps(int workoutreps) {
        this.workoutreps = workoutreps;
    }
}
