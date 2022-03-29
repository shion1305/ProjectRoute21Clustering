package com.shion1305.route.data;

import com.shion1305.route.object.IPv4;
import com.shion1305.route.object.TextGroup;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.shion1305.route.data.FastDataManager.generate;

class FastDataManagerTest {

    /**
     * This code regenerates all data in /fastData
     * DO NOT EXECUTE
     * @throws InterruptedException
     */
    void generateAllData() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (String file : new File("datasets").list()) {
            service.submit(()->{
                try {
                    generate(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        service.awaitTermination(1000000, TimeUnit.MINUTES);
        service.shutdown();
    }

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