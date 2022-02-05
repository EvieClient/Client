package com.evieclient.utils.api;

import co.gongzh.procbridge.Client;
import io.sentry.Sentry;

import java.util.ArrayList;
import java.util.HashMap;

public class SocketClient {

    public static final Client client = new Client("users.websocket.client.evie.pw", 2569);
    public static final HashMap<String, User> users = new HashMap<String, User>();


    public static void main(String[] args) {
    }

    public static Boolean registerUser(String username) {
        try {
            String[] arguments = client.request("registerUser", username).toString().split(":");
            if (arguments[0].equals("true")) {
                return true;
            } else if (arguments[0].equals("false")) {
                return false;
            } else {
                Sentry.captureMessage("There was an error for " + username + " in registerUser socket request");
                return false;
            }
        } catch (Exception e) {
            Sentry.captureException(e);
        }
        return false;
    }


    public static boolean isUser(String username) {
        try {
            String[] arguments = client.request("isUser", username).toString().split(":");
            if (arguments[0].equals("true")) {
                return true;
            } else if (arguments[0].equals("false")) {
                return false;
            } else {
                Sentry.captureMessage("There was an error for " + username + " in isUser socket request");
                return false;
            }
        } catch (Exception e) {
            Sentry.captureException(e);
        }
        return false;
    }
}
