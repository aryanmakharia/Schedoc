package com.example.drapp;

public class DocHelperClasss {
    String name, phno, reason, slot, date, drname, drphno;

    public DocHelperClasss() {

    }

    public DocHelperClasss(String name, String phno, String reason, String slot, String date, String drname, String drphno) {
        this.name = name;
        this.phno = phno;
        this.reason = reason;
        this.slot = slot;
        this.date = date;
        this.drname = drname;
        this.drphno = drphno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDrname() {
        return drname;
    }

    public void setDrname(String drname) {
        this.drname = drname;
    }

    public String getDrphno() {
        return drphno;
    }

    public void setDrphno(String drphno) {
        this.drphno = drphno;
    }
}
