package com.ericsson.nrgsdk.examples.applications.groupcommunicator;

import java.util.HashSet;
import java.util.Set;

public class Group {
    private String groupId;
    private Set members;

    public Group(String groupId, Set members) {
        this.groupId = groupId;
        this.members = members;
    }

    public String getGroupId() {
        return groupId;
    }

    public synchronized void join(User user) {
        members.add(user);
    }

    public synchronized void leave(User user) {
        members.remove(user);
    }

    public synchronized Set getMembers() {
        return new HashSet(members);
    }

    public synchronized void broadcast(Message message) {
        java.util.Iterator it = members.iterator();
        while (it.hasNext()) {
            User member = (User) it.next();
            member.receiveMessage(message);
        }
    }
}