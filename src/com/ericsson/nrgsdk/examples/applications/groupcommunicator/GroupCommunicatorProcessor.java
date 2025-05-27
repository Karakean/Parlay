package com.ericsson.nrgsdk.examples.applications.groupcommunicator;

import com.ericsson.hosasdk.api.hui.IpHosaUIManager;
import com.ericsson.hosasdk.api.hmm.hus.IpHosaUserStatus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GroupCommunicatorProcessor {
    private IpHosaUIManager hosaUIManager;
    private IpHosaUserStatus hosaUSManager;
    private Feature feature;
    private Map<String, Set<String>> groupMembers = new HashMap<>();

    public GroupCommunicatorProcessor(IpHosaUIManager hosaUIManager, IpHosaUserStatus hosaUSManager, Feature feature) {
        this.hosaUIManager = hosaUIManager;
        this.hosaUSManager = hosaUSManager;
        this.feature = feature;
    }

    public void joinGroup(String groupId, String memberId) {
        groupMembers.computeIfAbsent(groupId, k -> new HashSet<>()).add(memberId);
        System.out.println(memberId + " joined group " + groupId);
    }

    public void leaveGroup(String groupId, String memberId) {
        Set<String> members = groupMembers.get(groupId);
        if (members != null && members.remove(memberId)) {
            System.out.println(memberId + " left group " + groupId);
            if (members.isEmpty()) {
                groupMembers.remove(groupId);
            }
        } else {
            System.err.println("Member " + memberId + " not found in group " + groupId);
        }
    }

    public void sendMessageToGroup(String groupId, String senderId, String message) {
        Set<String> members = groupMembers.get(groupId);
        if (members != null) {
            for (String member : members) {
                if (!member.equals(senderId)) {
                    feature.messageReceived(senderId, groupId, message);
                }
            }
        } else {
            System.err.println("Group " + groupId + " not found.");
        }
    }
}
