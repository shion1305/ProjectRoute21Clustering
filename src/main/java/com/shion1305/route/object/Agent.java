package com.shion1305.route.object;

import java.io.Serializable;

public class Agent implements Serializable {
    private String hostname;
    private String name;
    private String id;
    private String ephemeral_id;
    private String type;
    private String version;

    public Agent() {

    }

    @Override
    public String toString() {
        return "Agent{" +
                "hostname='" + hostname + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", ephemeral_id='" + ephemeral_id + '\'' +
                ", type='" + type + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
