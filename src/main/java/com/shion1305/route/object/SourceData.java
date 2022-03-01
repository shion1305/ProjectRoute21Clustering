package com.shion1305.route.object;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.HashMap;

public class SourceData {
    public String request;
    public String url;
    public String url_path;
    public Agent agent;
    //geoipは到達先のIPアドレス
    public GeoIp geoIp;
    public String request_method;
    public int destination_port;
    public String source_ip;
    public Date timestamp;
    public HashMap<String, String> queries;
    public HashMap<String, String> headers;

    public SourceData(@JsonProperty("request") String request,
                      @JsonProperty("url") String url, @JsonProperty("agent") Agent agent, @JsonProperty("geoip") GeoIp geoip,
                      @JsonProperty("request_method") String request_method, @JsonProperty("destination_port") int destination_port,
                      @JsonProperty("source_ip") String source_ip, @JsonProperty("@timestamp") Date timestamp) throws IllegalStateException {
        this.request = request;
        this.url = url;
        queries = new HashMap<>();
        if (url == null) throw new IllegalStateException();
        if (url.indexOf('?') != -1) {
            url_path = url.substring(0, url.indexOf('?'));
            String query = url.substring(url.indexOf("?") + 1);
            for (String q : query.split("&")) {
                if (q.contains("=")) {
                    queries.put(q.substring(0, q.indexOf("=")), q.substring(q.indexOf("=")) + 1);
                }
            }
        } else {
            url_path = url;
        }
        String[] requests = request.split("\n");
        headers = new HashMap<>();
        String keyHolder = "";
        StringBuilder vHolder = new StringBuilder();
        for (int i = 0, i1; i < requests.length; i++) {
            if (i == 0) {
                continue;
            }
            if ((i1 = requests[i].indexOf(": ")) == -1) {
                vHolder.append("\n").append(requests[i]);
            } else {
                if (!keyHolder.equals("")) {
                    headers.put(keyHolder, vHolder.toString());
                }
                keyHolder = requests[i].substring(0, i1);
                vHolder = new StringBuilder(requests[i].substring(i1 + 2));
            }
        }
        this.agent = agent;
        this.geoIp = geoip;
        this.request_method = request_method;
        this.destination_port = destination_port;
        this.source_ip = source_ip;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "SourceData{" +
                "request='" + request + '\'' +
                ", url='" + url + '\'' +
                ", url_path='" + url_path + '\'' +
                ", agent=" + agent +
                ", geoIp=" + geoIp +
                ", request_method='" + request_method + '\'' +
                ", destination_port=" + destination_port +
                ", source_ip='" + source_ip + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
