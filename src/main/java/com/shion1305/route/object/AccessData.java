package com.shion1305.route.object;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessData {
    @JsonProperty("_source")
    public SourceData sourceData;
    @JsonProperty("_score")
    public float score;
    @JsonProperty("_id")
    public String id;

    public AccessData() {
    }

    @Override
    public String toString() {
        return "AccessData{" +
                "sourceData=" + sourceData +
                ", score=" + score +
                ", id='" + id + '\'' +
                '}';
    }
}
