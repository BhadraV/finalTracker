package com.example.tracker;

public class BusDetails {
    private String bustime, bustype, s1, s2, s3, s4, s5, s6, current_location;

    public BusDetails() {
    }

    public BusDetails(String bustime, String bustype, String s1, String s2, String s3, String s4, String s5, String s6, String current_location) {
        this.bustime = bustime;
        this.bustype = bustype;
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.s4 = s4;
        this.s5 = s5;
        this.s6 = s6;
        this.current_location = current_location;
    }

    public String getBustime() {
        return bustime;
    }

    public String getBustype() {
        return bustype;
    }

    public String getS1() {
        return s1;
    }

    public String getS2() {
        return s2;
    }

    public String getS3() {
        return s3;
    }

    public String getS4() {
        return s4;
    }

    public String getS5() {
        return s5;
    }

    public String getS6() {
        return s6;
    }

    public String getCurrent_location() {
        return current_location;
    }

    public String toString() {
        return "Busdetails{" +
                "bustype='" + bustype + '\'' +
                ", bustime='" + bustime + '\'' +
                ", stop 1='" + s1 + '\'' +
                ", stop 2='" + s2 + '\'' +
                ", stop 3='" + s3 + '\'' +
                ", stop 4='" + s4 + '\'' +
                ", stop 5='" + s5 + '\'' +
                ", stop 6='" + s6 + '\'' +
                ", current location='" + current_location + '\'' +
                '}';
    }
}