package com.example.myapplication;

public class Course {
    private String code;
    private String name;
    private String credit;
    private String coordinator;

    public Course(String code, String name, String credit, String coordinator) {
        this.code = code;
        this.name = name;
        this.credit = credit;
        this.coordinator = coordinator;

    }

    private String registeredBy;

    public Course() {
        // Default constructor required for Firebase
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(String coordinator) {
        this.coordinator = coordinator;
    }
}
