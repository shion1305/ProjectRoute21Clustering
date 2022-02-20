package com.shion1305.route.object;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class SourceData {
    @JsonProperty("request")
    public String request;
    @JsonProperty("agent")
    public Agent agent;
    //geoipは到達先のIPアドレス
    @JsonProperty("geoip")
    public GeoIp geoIp;
    @JsonProperty("request_method")
    public String request_method;
    @JsonProperty("destination_port")
    public int destination_port;
    @JsonProperty("source_ip")
    public String source_ip;
    @JsonProperty("@timestamp")
    public Date timestamp;

    public SourceData() {

    }
}
