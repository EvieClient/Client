package com.evieclient.utils.api;

public class User {

    public String mcName;
    public boolean isUsingEvie;

    public User(String mcName) {
        this.mcName = mcName;
        this.isUsingEvie = SocketClient.isUser(mcName);
    }

    public boolean isEvieUser() {
        return isUsingEvie;
    }

    public String getName() {
        return mcName;
    }

    public String UserInfo() {
        return "User: " + mcName + " is using Evie: " + isUsingEvie;
    }
}
