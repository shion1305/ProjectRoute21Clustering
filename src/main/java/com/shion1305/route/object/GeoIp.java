package com.shion1305.route.object;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GeoIp {
    @JsonProperty("ip")
    public String ip;
    @JsonProperty("destination_port")
    public int destination_port;

    public GeoIp() {

    }
}
