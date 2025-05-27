package com.ericsson.nrgsdk.examples.applications.groupcommunicator;

import com.ericsson.hosasdk.api.TpAddress;
import com.ericsson.hosasdk.api.TpHosaDeliveryTime;
import com.ericsson.hosasdk.api.TpHosaMessage;
import com.ericsson.hosasdk.api.TpHosaSendMessageError;
import com.ericsson.hosasdk.api.TpHosaSendMessageReport;
import com.ericsson.hosasdk.api.TpHosaTerminatingAddressList;
import com.ericsson.hosasdk.api.TpHosaUIMessageDeliveryType;
import com.ericsson.hosasdk.api.hui.IpAppHosaUIManager;
import com.ericsson.hosasdk.api.hui.IpAppHosaUIManagerAdapter;
import com.ericsson.hosasdk.api.hui.IpHosaUIManager;
import com.ericsson.hosasdk.api.ui.P_UI_RESPONSE_REQUIRED;
import com.ericsson.nrgsdk.examples.tools.SDKToolkit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GroupCommunicatorProcessor extends IpAppHosaUIManagerAdapter {

    private IpHosaUIManager hosaUIManager;
    private Map<String, Set<String>> groupMembers = new HashMap<>();

    public GroupCommunicatorProcessor(IpHosaUIManager hosaUIManager) {
        this.hosaUIManager = hosaUIManager;
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
                    sendSMS(senderId, member, message);
                }
            }
        } else {
            System.err.println("Group " + groupId + " not found.");
        }
    }

    private void sendSMS(String sender, String receiver, String messageContent) {
        TpHosaUIMessageDeliveryType deliveryType = TpHosaUIMessageDeliveryType.P_HUI_SMS;
        TpHosaDeliveryTime deliveryTime = new TpHosaDeliveryTime();
        deliveryTime.Dummy((short) 0);

        TpAddress originatingAddress = SDKToolkit.createTpAddress(sender);
        TpAddress destinationAddress = SDKToolkit.createTpAddress(receiver);
        TpHosaTerminatingAddressList recipients = new TpHosaTerminatingAddressList();
        recipients.ToAddressList = new TpAddress[]{destinationAddress};

        TpHosaMessage message = new TpHosaMessage();
        message.Text(messageContent);

        hosaUIManager.hosaSendMessageReq(
                this,
                originatingAddress,
                recipients,
                null,
                message,
                deliveryType,
                Configuration.INSTANCE.getBillingInformation(),
                P_UI_RESPONSE_REQUIRED.value,
                false,
                deliveryTime,
                ""
        );
    }

    @Override
    public void hosaSendMessageErr(int assignmentID, TpHosaSendMessageError[] errorList) {
        System.err.println("Error sending SMS to " + errorList[0].UserAddress.AddrString +
                " (ErrorCode: " + errorList[0].Error.value() + ")");
    }

    @Override
    public void hosaSendMessageRes(int assignmentID, TpHosaSendMessageReport[] responseList) {
        System.out.println("SMS successfully sent to " + responseList[0].UserAddress.AddrString);
    }
}