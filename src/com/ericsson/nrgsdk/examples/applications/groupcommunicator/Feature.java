package com.ericsson.nrgsdk.examples.applications.groupcommunicator;

import com.ericsson.hosasdk.api.HOSAMonitor;
import com.ericsson.hosasdk.api.fw.P_UNKNOWN_SERVICE_TYPE;
import com.ericsson.hosasdk.api.hui.IpHosaUIManager;
import com.ericsson.hosasdk.utility.framework.FWproxy;
import com.ericsson.nrgsdk.examples.tools.SDKToolkit;

public class Feature {
    private FWproxy itsFramework;
    private IpHosaUIManager itsHosaUIManager;
    private GroupCommunicatorProcessor itsGroupCommunicatorProcessor;

    public Feature(GUI aGUI) {
        aGUI.setTitle("GroupCommunicator");
        aGUI.addTab("Description", getDescription());
    }

    public void start() {
        HOSAMonitor.addListener(SDKToolkit.LOGGER);
        System.out.println("Getting framework");
        itsFramework = new FWproxy(Configuration.INSTANCE);
        System.out.println("Getting service managers");
        try {
            itsHosaUIManager = (IpHosaUIManager) itsFramework.obtainSCF("SP_HOSA_USER_INTERACTION");
        } catch (P_UNKNOWN_SERVICE_TYPE anException) {
            System.err.println("Service not found: " + anException);
        }
        System.out.println("Creating group communicator processor");
        itsGroupCommunicatorProcessor = new GroupCommunicatorProcessor(itsHosaUIManager);
    }

    public void stop() {
        if (itsFramework != null) {
            itsFramework.endAccess();
            itsFramework.dispose();
        }
        HOSAMonitor.removeListener(SDKToolkit.LOGGER);
    }

    public void joinGroup(String groupId, String memberId) {
        itsGroupCommunicatorProcessor.joinGroup(groupId, memberId);
    }

    public void leaveGroup(String groupId, String memberId) {
        itsGroupCommunicatorProcessor.leaveGroup(groupId, memberId);
    }

    public void sendMessageToGroup(String groupId, String senderId, String message) {
        itsGroupCommunicatorProcessor.sendMessageToGroup(groupId, senderId, message);
    }

    public void messageReceived(String sender, String groupId, String content) {
        System.out.println("Message received in group '" + groupId + "' from '" + sender + "': " + content);
    }

    private String getDescription() {
        return "This application allows users to join groups, leave groups, send messages, and receive messages via SMS.";
    }

    public void terminate() {
        System.exit(0);
    }
}