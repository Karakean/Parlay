package com.ericsson.nrgsdk.examples.applications.groupcommunicator;

public class User {
    private String userId;

    public User(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void receiveMessage(Message message) {
        System.out.println("[" + userId + "] Received: " + message);
    }
}
