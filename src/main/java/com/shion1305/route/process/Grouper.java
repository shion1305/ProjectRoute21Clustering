package com.shion1305.route.process;

import com.shion1305.route.object.AccessData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Grouper {
    public static HashMap<String, ArrayList<AccessData>> groupByIp(AccessData[] data) {
        return groupByIp(Arrays.asList(data));
    }

    public static HashMap<String, ArrayList<AccessData>> groupByIp(List<AccessData> data) {
        HashMap<String, ArrayList<AccessData>> dataBySIP = new HashMap<>();
        for (AccessData d : data) {
            if (dataBySIP.containsKey(d.sourceData.geoIp.ip)) {
                dataBySIP.get(d.sourceData.geoIp.ip).add(d);
            } else {
                var t = new ArrayList<AccessData>();
                t.add(d);
                dataBySIP.put(d.sourceData.geoIp.ip, t);
            }
        }
        return dataBySIP;
    }
    public static HashMap<String, ArrayList<AccessData>> groupByPath(AccessData[] data) {
        return groupByPath(Arrays.asList(data));
    }

    public static HashMap<String, ArrayList<AccessData>> groupByPath(List<AccessData> data) {
        HashMap<String, ArrayList<AccessData>> dataByPath = new HashMap<>();
        for (AccessData d : data) {
            if (dataByPath.containsKey(d.sourceData.url_path)) {
                dataByPath.get(d.sourceData.url_path).add(d);
            } else {
                var t = new ArrayList<AccessData>();
                t.add(d);
                dataByPath.put(d.sourceData.url_path, t);
            }
        }
        return dataByPath;
    }

}
