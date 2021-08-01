package com.example.fitbook.routes;

public class Routes {
    private String routeid;
    private String routename;
    private String routedistancetravelled;
    private String routeelaspedtime;
    private String routestepstaken;

    public Routes() {
    }

    public Routes(String routeid, String routename, String routedistancetravelled, String routeelaspedtime, String routestepstaken) {
        this.routeid = routeid;
        this.routename = routename;
        this.routedistancetravelled = routedistancetravelled;
        this.routeelaspedtime = routeelaspedtime;
        this.routestepstaken = routestepstaken;
    }

    public String getRouteid() {
        return routeid;
    }

    public void setRouteid(String routeid) {
        this.routeid = routeid;
    }

    public String getRoutename() {
        return routename;
    }

    public void setRoutename(String routename) {
        this.routename = routename;
    }

    public String getRoutedistancetravelled() {
        return routedistancetravelled;
    }

    public void setRoutedistancetravelled(String routedistancetravelled) {
        this.routedistancetravelled = routedistancetravelled;
    }

    public String getRoutelaspedtime() {
        return routeelaspedtime;
    }

    public void setRoutelaspedtime(String routelaspedtime) {
        this.routeelaspedtime = routelaspedtime;
    }

    public String getRoutestepstaken() {
        return routestepstaken;
    }

    public void setRoutestepstaken(String routestepstaken) {
        this.routestepstaken = routestepstaken;
    }
}
