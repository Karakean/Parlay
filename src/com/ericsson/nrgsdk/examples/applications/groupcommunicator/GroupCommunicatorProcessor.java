package com.ericsson.nrgsdk.examples.applications.groupcommunicator;

import com.ericsson.hosasdk.api.hui.IpHosaUIManager;
import com.ericsson.hosasdk.api.hmm.hus.IpHosaUserStatus;

public class GroupCommunicatorProcessor {
    private IpHosaUIManager hosaUIManager;
    private IpHosaUserStatus hosaUSManager;
    private Feature feature;

    public GroupCommunicatorProcessor(IpHosaUIManager hosaUIManager, IpHosaUserStatus hosaUSManager, Feature feature) {
        this.hosaUIManager = hosaUIManager;
        this.hosaUSManager = hosaUSManager;
        this.feature = feature;
    }

    public void startGroupNotifications() {
        // TODO: Register for group message notifications using Parlay/OSA API
        System.out.println("Group notifications started (Parlay/OSA stub)");
    }

    public void stopGroupNotifications() {
        // TODO: Unregister group message notifications
        System.out.println("Group notifications stopped (Parlay/OSA stub)");
    }

    public void simulateMessageReceived(String sender, String groupId, String content) {
        feature.messageReceived(sender, groupId, content);
    }
}
