package com.himmerland.hero.service.notifications;

import com.himmerland.hero.service.helperclasses.enums.*;
import com.himmerland.hero.service.helperclasses.id.IdentifiableBase;

public class Notification extends IdentifiableBase{
    private String address;
    private String cause;
    private String rule;

    private Criticality criticality;

    private String timeStamp;
    private boolean isActive;
    private boolean isSent;

    public Notification() {
    }

    public Notification(String address, String cause, String rule, Criticality criticality, String timeStamp, boolean isActive, boolean isSent) {
        this.address = address;
        this.cause = cause;
        this.rule = rule;
        this.criticality = criticality;
        this.timeStamp = timeStamp;
        this.isActive = true;
        this.isSent = false;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCause() {
        return cause;
    }
    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getRule() {
        return rule;
    }
    public void setRule(String rule) {
        this.rule = rule;
    }
    public Criticality getCriticality() {
        return criticality;
    }
    public void setCriticality(Criticality criticality) {
        this.criticality = criticality;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
    public boolean isSent() {
        return isSent;
    }
    public void setSent(boolean sent) {
        isSent = sent;
    }
}
