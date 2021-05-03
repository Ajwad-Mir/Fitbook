package com.example.fitbook.exercise;


public class Exercise {
    public String id;
    public String name;
    public String date;
    public String creatorname;
    public String creatoruid;

    public Exercise() {
    }

    public Exercise(String id, String name, String date, String creatorname, String creatoruid) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.creatorname = creatorname;
        this.creatoruid = creatoruid;
    }

    public String getid() {
        return id;
    }

    public String getname() {
        return name;
    }

    public String getdate() {
        return date;
    }

    public String getcreatorname() {
        return creatorname;
    }

    public String getcreatoruid() {
        return creatoruid;
    }

    public void setid(String id) {
        this.id = id;
    }

    public void setname(String name) {
        this.name = name;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public void setcreatorname(String creatorname) {
        this.creatorname = creatorname;
    }

    public void setcreatoruid(String creatoruid) {
        this.creatoruid = creatoruid;
    }
}
