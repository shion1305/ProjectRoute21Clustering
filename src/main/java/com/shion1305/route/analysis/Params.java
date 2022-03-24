package com.shion1305.route.analysis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Params implements Serializable {
    public Integer count = 1;
    public ArrayList<String> ips = new ArrayList<>();
    public Date first;
    public Date last;
}
