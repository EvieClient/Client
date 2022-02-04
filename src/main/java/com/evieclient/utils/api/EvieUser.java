package com.evieclient.utils.api;

public class EvieUser {

    public String mcName;
    public boolean isUser;

    public EvieUser(String mcName, boolean isUser) {
        this.mcName = mcName;
        this.isUser = isUser;
    }

    public String getProperties() {
        return mcName + ":" + (isUser ? "true" : "false");
    }

    public String getMcName() {
        return mcName;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }
}
