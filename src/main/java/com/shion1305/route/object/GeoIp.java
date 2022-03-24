package com.shion1305.route.object;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class GeoIp implements Serializable {
    @JsonProperty("ip")
    public String ip;
    @JsonProperty("destination_port")
    public int destination_port;

    public GeoIp() {

    }
}
