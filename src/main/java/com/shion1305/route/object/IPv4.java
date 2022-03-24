package com.shion1305.route.object;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPv4 extends SpecificFormat implements Serializable {
    public static final long serialVersionUID = 7827725166551021656L;
    int[] ip = new int[4];

    public IPv4(String ip) {
        Matcher m = Pattern.compile("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)").matcher(ip);
        if (!m.matches()) throw new IllegalArgumentException("INPUT \"" + ip + "\" is not recognized as IPv4 format");
        for (int i = 0; i < 4; i++) {
            this.ip[i] = Integer.parseInt(m.group(i + 1));
        }
    }

    public IPv4(int[] ip) {
        if (ip.length != 4) throw new IllegalArgumentException();
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "IPv4{" +
                "ip=" + ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3] +
                '}';
    }
}
