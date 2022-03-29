package com.shion1305.route.analysis;

import com.shion1305.route.data.FastDataManager;
import com.shion1305.route.object.ProcessedAccessData;
import com.shion1305.route.object.TextGroup;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class DataComparatorTest {

    @Test
    void compare() {

    }

    @Test
    void matchesPath() {
        ProcessedAccessData testD=new ProcessedAccessData("/ws/v1/cluster/apps/new-application",new HashMap<>());
        System.out.println(testD);
        ProcessedAccessData d1=new ProcessedAccessData("/ws/v1/cluster/apps/new-application",new HashMap<>());
        System.out.println(d1);
        System.out.println(DataComparator.compare1(testD,d1));
    }

    @Test
    void testSearch() throws InterruptedException {
        ArrayList<String> res=new ArrayList<>();
        ProcessedAccessData testD = new ProcessedAccessData("/Xh7f", new HashMap<>());
        String[] files = new File("fastData").list();
        ExecutorService service = Executors.newFixedThreadPool(3);
        for (String file : files) {
            try {
                System.out.println(file);
                service.submit(() -> {
                    var d = FastDataManager.readDataFast(file);
                    for (var data : d) {
                        if (data == null) {
                            System.out.println("ERROR");
                            continue;
                        }
                        switch (DataComparator.compare1(data, testD)) {
                            case MATCH_FULL:
                            case MATCH_PATH:
                                StringBuilder builder = new StringBuilder();
                                data.pathD.forEach((groups) -> {
                                    builder.append("/");
                                    for (TextGroup tg : groups) {
                                        builder.append(tg.getText());
                                    }
                                });
                                builder.delete(0,1);
                                builder.append("?");
                                data.queries.forEach((key, value) -> {
                                    key.forEach(textGroup -> {
                                        builder.append(textGroup.getText());
                                    });
                                    builder.append("=");
                                    value.forEach(textGroup -> {
                                        builder.append(textGroup.getText());
                                    });
                                    builder.append("&");
                                });
                                builder.delete(builder.length()-1,builder.length());
                                if (!res.contains(builder.toString())) {
                                    res.add(builder.toString());
                                    System.out.println(builder.toString());
                                }
                                break;
                            default:
                        }
                    }
                });
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }
        service.awaitTermination(100000, TimeUnit.MINUTES);
        service.shutdown();
    }
}