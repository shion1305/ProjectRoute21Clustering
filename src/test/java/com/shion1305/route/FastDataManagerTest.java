package com.shion1305.route;

import com.shion1305.route.object.ProcessedAccessData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FastDataManagerTest {

    @Test
    void readDataFast() {
        ArrayList<ProcessedAccessData> data = new ArrayList<>();
        data.addAll(FastDataManager.readDataFast("data2021-1001"));
        data.addAll(FastDataManager.readDataFast("data2021-1002"));
        data.addAll(FastDataManager.readDataFast("data2021-1003"));
        data.addAll(FastDataManager.readDataFast("data2021-1004"));
        data.addAll(FastDataManager.readDataFast("data2021-1005"));
        data.addAll(FastDataManager.readDataFast("data2021-1006"));
        data.addAll(FastDataManager.readDataFast("data2021-1007"));
        data.addAll(FastDataManager.readDataFast("data2021-1008"));
        data.addAll(FastDataManager.readDataFast("data2021-1009"));
        data.addAll(FastDataManager.readDataFast("data2021-1010"));
        System.out.println(data.size());
    }
}