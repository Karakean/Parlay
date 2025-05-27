package com.ericsson.nrgsdk.examples.applications.groupcommunicator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class Main {
    private Feature itsFeature;

    public static void main(String[] args) throws Exception {
        new Main();
    }

    public Main() throws IOException {
        Configuration.INSTANCE.load(this);
        GUI gui = new GUI();
        gui.addButton(new AbstractAction("Start") {
            public void actionPerformed(ActionEvent e) {
                start();
            }
        });
        itsFeature = new Feature(gui);
        gui.addButton(new AbstractAction("Join Group") {
            public void actionPerformed(ActionEvent e) {
                String groupId = JOptionPane.showInputDialog("Enter Group ID:");
                String memberId = JOptionPane.showInputDialog("Enter Member ID:");
                itsFeature.joinGroup(groupId, memberId);
            }
        });
        gui.addButton(new AbstractAction("Leave Group") {
            public void actionPerformed(ActionEvent e) {
                String groupId = JOptionPane.showInputDialog("Enter Group ID:");
                String memberId = JOptionPane.showInputDialog("Enter Member ID:");
                itsFeature.leaveGroup(groupId, memberId);
            }
        });
        gui.addButton(new AbstractAction("Send Message") {
            public void actionPerformed(ActionEvent e) {
                String groupId = JOptionPane.showInputDialog("Enter Group ID:");
                String senderId = JOptionPane.showInputDialog("Enter Sender ID:");
                String message = JOptionPane.showInputDialog("Enter Message:");
                itsFeature.sendMessageToGroup(groupId, senderId, message);
            }
        });
        gui.addButton(new AbstractAction("Stop") {
            public void actionPerformed(ActionEvent e) {
                stop();
            }
        });
        gui.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                terminate();
            }
        });
        gui.showCentered();
    }

    private void start() {
        itsFeature.start();
    }

    private void stop() {
        itsFeature.stop();
    }

    private void terminate() {
        System.exit(0);
    }
}