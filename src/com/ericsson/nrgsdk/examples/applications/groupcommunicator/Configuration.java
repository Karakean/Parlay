package com.ericsson.nrgsdk.examples.applications.groupcommunicator;

import com.ericsson.nrgsdk.examples.tools.configuration.NestedProperties;
import com.ericsson.nrgsdk.examples.tools.configuration.NotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Configuration extends NestedProperties {
    public static final Configuration INSTANCE = new Configuration();

    private static String itsBillingInformation;

    private Group[] itsGroups;

    private Configuration() {

    }

    public void load(Object aSource) throws IOException {
        super.load(aSource);
        itsGroups = loadGroups("group");
    }

    public Group[] getGroups() {
        return itsGroups;
    }

    private Group[] loadGroups(String aParm) {
        List groups = new ArrayList();
        for (int i = 0;; i++) {
            String parm = aParm + "." + i;
            try {
                groups.add(loadGroup(parm));
            } catch (NotFoundException e) {
                if (prefixExists(parm)) {
                    throw e;
                }
                break;
            }
        }
        return (Group[]) groups.toArray(new Group[0]);
    }

    private Group loadGroup(String aParm) {
        String groupId = loadString(aParm + ".groupId");
        Set<User> initialMembers = loadMembers(aParm + ".members");
        Group group = new Group(groupId, initialMembers);

        return group;
    }

    private Set<User> loadMembers(String aParm) {
        Set<User> users = new HashSet<>();
        for (int i = 0;; i++) {
            try {
                users.add(new User(loadString(aParm + "." + i)));
            } catch (NotFoundException e) {
                break;
            }
        }
        return users;
    }

    private String loadString(String aParm) {
        String s = getProperty(aParm);
        if (s == null) {
            throw new NotFoundException(aParm);
        }
        return s;
    }

    public String getBillingInformation() {
        return itsBillingInformation;
    }
}
