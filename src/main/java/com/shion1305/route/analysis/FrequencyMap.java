package com.shion1305.route.analysis;

import com.shion1305.route.object.AccessData;

import java.io.Serializable;
import java.util.HashMap;

public class FrequencyMap extends HashMap<String, Params> {
    public void addValue(String key, AccessData tData) {
        Params p;
        if (this.containsKey(key)) {
            p = this.get(key);
            p.count++;
            if (tData.sourceData.timestamp.before(p.first)) {
                p.first = tData.sourceData.timestamp;
            } else if (tData.sourceData.timestamp.after(p.last)) {
                p.last = tData.sourceData.timestamp;
            }
            if (!p.ips.contains(tData.sourceData.source_ip)) {
                p.ips.add(tData.sourceData.source_ip);
            }
        } else {
            p = new Params();
            p.ips.add(tData.sourceData.geoIp.ip);
            p.first = tData.sourceData.timestamp;
            p.last = tData.sourceData.timestamp;
        }
        this.put(key, p);
    }
}
