package com.himmerland.hero.service.notifications;

import com.himmerland.hero.service.helperclasses.enums.*;

public class Notification {
    private String address;
    private String cause;
    private String rule;

    private Criticality criticality;

    private String timeStamp;
    private boolean isActive;
    private boolean isSent;

    public Notification(String address, String cause, String rule, int criticalityLevel, String timeStamp, boolean isActive, boolean isSent) {
        this.address = address;
        this.cause = cause;
        this.rule = rule;
        this.criticality = Criticality.values()[criticalityLevel];
        this.timeStamp = timeStamp;
        this.isActive = true;
        this.isSent = false;
    }

    public String getAddress() {
        return address;
    }
    public String getCause() {
        return cause;
    }
    public String getRule() {
        return rule;
    }
    public Criticality getCriticality() {
        return criticality;
    }
    public String getTimeStamp() {
        return timeStamp;
    }
    public boolean isActive() {
        return isActive;
    }
    public boolean isSent() {
        return isSent;
    }
}
