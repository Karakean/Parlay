package com.ericsson.nrgsdk.examples.applications.groupcommunicator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {
    public static final Configuration INSTANCE = new Configuration();
    private Properties properties;

    private Configuration() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream(".\\examples\\groupcommunicator\\resources\\groupcommunicator.ini"));
        } catch (IOException e) {
            properties.setProperty("CosNamingResponder.0.host", "localhost");
            properties.setProperty("CosNamingResponder.0.port", "23104");
        }
    }

    public Properties getProperties() {
        return properties;
    }

    public String getNrgHost() {
        return properties.getProperty("CosNamingResponder.0.host", "localhost");
    }

    public int getNrgPort() {
        String portStr = properties.getProperty("CosNamingResponder.0.port", "23104");
        try {
            return Integer.parseInt(portStr);
        } catch (NumberFormatException e) {
            return 23104;
        }
    }
}
