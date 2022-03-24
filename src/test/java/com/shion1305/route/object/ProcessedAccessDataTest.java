package com.shion1305.route.object;

import org.junit.Test;

class ProcessedAccessDataTest {
    public static void main(String[] args) {
        new ProcessedAccessDataTest().testConverter();
    }

    @Test
    public void testConverter() {
        System.out.println(ProcessedAccessData.convertToTextGroup("192.141.35.15AOINfoaig;nsegnoain124.23.74.25thiesono15.2.61.3"));
    }
}