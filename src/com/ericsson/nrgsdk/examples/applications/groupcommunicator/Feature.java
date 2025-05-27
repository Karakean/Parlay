package com.ericsson.nrgsdk.examples.applications.groupcommunicator;

import com.ericsson.hosasdk.api.HOSAMonitor;
import com.ericsson.hosasdk.api.fw.P_UNKNOWN_SERVICE_TYPE;
import com.ericsson.hosasdk.api.hmm.hus.IpHosaUserStatus;
import com.ericsson.hosasdk.api.hui.IpHosaUIManager;
import com.ericsson.hosasdk.utility.framework.FWproxy;
import com.ericsson.nrgsdk.examples.tools.SDKToolkit;

public class Feature {
    private FWproxy itsFramework;
    private IpHosaUIManager itsHosaUIManager;
    private IpHosaUserStatus itsHosaUSManager;
    private GroupCommunicatorProcessor itsGroupCommunicatorProcessor;

    public Feature() {
    }

    public void start() {
        HOSAMonitor.addListener(SDKToolkit.LOGGER);
        System.out.println("Getting framework");
        itsFramework = new FWproxy(Configuration.INSTANCE.getProperties());
        System.out.println("Getting service managers");
        try {
            itsHosaUIManager = (IpHosaUIManager) itsFramework.obtainSCF("P_USER_INTERACTION");
            itsHosaUSManager = (IpHosaUserStatus) itsFramework.obtainSCF("P_USER_STATUS");
        } catch (P_UNKNOWN_SERVICE_TYPE anException) {
            System.err.println("Service not found. Please refer to the Ericsson Network Resource Gateway User Guide for a list of which applications that are able to run on which test tools\n" + anException);
        }
        System.out.println("Creating group communicator processor");
        itsGroupCommunicatorProcessor = new GroupCommunicatorProcessor(itsHosaUIManager, itsHosaUSManager, this);
        itsGroupCommunicatorProcessor.startGroupNotifications();
    }

    public void stop() {
        if (itsGroupCommunicatorProcessor != null) {
            itsGroupCommunicatorProcessor.stopGroupNotifications();
        }
        if (itsHosaUIManager != null) {
            itsFramework.releaseSCF(itsHosaUIManager);
        }
        if (itsHosaUSManager != null) {
            itsFramework.releaseSCF(itsHosaUSManager);
        }
        if (itsFramework != null) {
            itsFramework.endAccess();
            itsFramework.dispose();
        }
        HOSAMonitor.removeListener(SDKToolkit.LOGGER);
    }

    public void messageReceived(String sender, String groupId, String content) {
        System.out.println("Message received for group '" + groupId + "' from '" + sender + "': " + content);
    }
}
