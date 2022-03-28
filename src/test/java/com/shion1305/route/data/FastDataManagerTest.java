package com.shion1305.route.data;

import com.shion1305.route.object.IPv4;
import com.shion1305.route.object.TextGroup;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

class FastDataManagerTest {

    @Test
    void readDataFast() {
        String[] files = new File("fastData").list();
        for (String file : files) {
            var d = FastDataManager.readDataFast(file);
            begin:
            for (var data : d) {
                for (ArrayList<TextGroup> txg : data.pathD) {
                    for (var tx : txg) {
                        if (tx.getClass().equals(IPv4.class) && txg.size() > 1) {
                            System.out.println(data);
                            continue begin;
                        }
                    }
                }
            }
        }
    }
}