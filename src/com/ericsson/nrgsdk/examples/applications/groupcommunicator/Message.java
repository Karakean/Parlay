package com.ericsson.nrgsdk.examples.applications.groupcommunicator;

public class Message {
    private String fromUserId;
    private String content;

    public Message(String fromUserId, String content) {
        this.fromUserId = fromUserId;
        this.content = content;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public String getContent() {
        return content;
    }

    public String toString() {
        return fromUserId + ": " + content;
    }
}
