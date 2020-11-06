package com.example.ossd_project;

public class companylist {

    String name,ctc,role,internorfte,deadlinedate,deadlinetime,about,additional;
    String cgpa;

    public companylist(String name, String ctc, String role, String internorfte, String deadlinedate, String deadlinetime, String about, String additional) {
        this.name = name;
        this.ctc = ctc;
        this.role = role;
        this.internorfte = internorfte;
        this.deadlinedate = deadlinedate;
        this.deadlinetime = deadlinetime;
        this.about = about;
        this.additional = additional;
    }


    public companylist() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCtc() {
        return ctc;
    }

    public void setCtc(String ctc) {
        this.ctc = ctc;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getInternorfte() {
        return internorfte;
    }

    public void setInternorfte(String internorfte) {
        this.internorfte = internorfte;
    }

    public String getDeadlinedate() {
        return deadlinedate;
    }

    public void setDeadlinedate(String deadlinedate) {
        this.deadlinedate = deadlinedate;
    }

    public String getDeadlinetime() {
        return deadlinetime;
    }

    public void setDeadlinetime(String deadlinetime) {
        this.deadlinetime = deadlinetime;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }
}
