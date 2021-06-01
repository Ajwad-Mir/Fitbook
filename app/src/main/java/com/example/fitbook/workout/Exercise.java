package com.example.fitbook.workout;

public class Exercise {

    public String exerciseuid;
    public String exercisename;
    public int exercisereps;

    public Exercise() {
    }

    public Exercise(String exercise_uid, String exercise_name, int exercise_reps) {
        this.exerciseuid = exercise_uid;
        this.exercisename = exercise_name;
        this.exercisereps = exercise_reps;
    }

    public String getexerciseuid() {
        return exerciseuid;
    }

    public String getexercisename() {
        return exercisename;
    }

    public int getexercisereps() {
        return exercisereps;
    }


    public void setexerciseuid(String uid) {
        exerciseuid = uid;
    }

    public void setexercisename(String name) {
        exercisename = name;
    }

    public void setexercisereps(int reps) {
        exercisereps = reps;
    }
}
