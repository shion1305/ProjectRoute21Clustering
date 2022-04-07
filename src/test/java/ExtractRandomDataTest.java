import com.shion1305.route.io.DataReader;
import com.shion1305.route.object.AccessData;
import com.shion1305.route.process.Grouper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ExtractRandomDataTest {
    public static void main(String[] args) throws IOException {
//        printAccessDataWithRandomPath();
        printRandomData();
    }

    public static void printRandomData() throws IOException {
        Random r = new Random();
        String[] files = new File("datasets").list();
        for (int i = 0; i < 20; i++) {
            List<AccessData> d = DataReader.readData(files[Math.abs(r.nextInt()) % files.length]);
            while (true) {
                AccessData data = d.get(Math.abs(r.nextInt()) % d.size());
                if (!data.sourceData.url.startsWith("http://")&&data.sourceData.url.length()>2) {
                    System.out.println(data.sourceData.url);
                    System.out.println(data.sourceData.request);
                    break;
                }
            }
        }
    }

    public static void printAccessDataWithRandomPath() throws IOException {
        Random r = new Random();
        String[] files = new File("datasets").list();
        for (int i = 0; i < 30; i++) {
            List<AccessData> d = DataReader.readData(files[Math.abs(r.nextInt()) % files.length]);
            var dP = Grouper.groupByPath(d);
            dP.forEach((s, data) -> {
                if (s != null && s.matches("^/[A-Za-z]{3,}[0-9]+/?$") && data.size() == 1) {
                    AccessData data1 = data.get(Math.abs(r.nextInt()) % data.size());
                    System.out.println(data1.sourceData.url);
                }
            });
        }
    }
}
