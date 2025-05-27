package com.ericsson.nrgsdk.examples.applications.groupcommunicator;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
    private static final Map groups = new HashMap();
    private static final Map users = new HashMap();
    private static Feature feature;

    public static void main(String[] args) {
        feature = new Feature();
        feature.start();
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
        System.out.println("Group Communicator Example (Parlay/OSA API based)");
        while (true) {
            System.out.println("\nCommands: join <user> <group>, send <user> <group> <message>, leave <user> <group>, exit");
            String line = null;
            try {
                line = reader.readLine();
            } catch (java.io.IOException e) {
                break;
            }
            if (line == null) break;
            StringTokenizer st = new StringTokenizer(line, " ");
            if (!st.hasMoreTokens()) continue;
            String command = st.nextToken();
            if (command.equalsIgnoreCase("join") && st.countTokens() >= 2) {
                String userId = st.nextToken();
                String groupId = st.nextToken();
                User user = (User) users.get(userId);
                if (user == null) {
                    user = new User(userId);
                    users.put(userId, user);
                }
                Group group = (Group) groups.get(groupId);
                if (group == null) {
                    group = new Group(groupId);
                    groups.put(groupId, group);
                }
                group.join(user);
                System.out.println(userId + " joined group " + groupId);
            } else if (command.equalsIgnoreCase("send") && st.countTokens() >= 2) {
                String userId = st.nextToken();
                String groupId = st.nextToken();
                String messageContent = "";
                if (st.hasMoreTokens()) {
                    messageContent = line.substring(line.indexOf(groupId) + groupId.length()).trim();
                }
                User user = (User) users.get(userId);
                Group group = (Group) groups.get(groupId);
                if (user != null && group != null) {
                    Message message = new Message(userId, messageContent);
                    group.broadcast(message);
                    feature.messageReceived(userId, groupId, messageContent);
                } else {
                    System.out.println("User or group not found.");
                }
            } else if (command.equalsIgnoreCase("leave") && st.countTokens() >= 2) {
                String userId = st.nextToken();
                String groupId = st.nextToken();
                User user = (User) users.get(userId);
                Group group = (Group) groups.get(groupId);
                if (user != null && group != null) {
                    group.leave(user);
                    System.out.println(userId + " left group " + groupId);
                } else {
                    System.out.println("User or group not found.");
                }
            } else if (command.equalsIgnoreCase("exit")) {
                feature.stop();
                break;
            } else {
                System.out.println("Unknown command.");
            }
        }
        try { reader.close(); } catch (Exception e) {}
    }
}
