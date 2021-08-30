package nc.opt.mobile;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

public class DataTest {
    @Test
    public void testCsvUTF8() throws IOException {
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(DataTest.class.getResourceAsStream("/partenaires.csv"), StandardCharsets.UTF_8))) {

            String line;
            int n = 0;
            while ((line = reader.readLine()) != null) {
                n++;
                assertTrue(line.matches("(\\p{IsLatin}|\\p{Print})+"),
                    "[partenaire.csv:" + n + "] Non printable character found '" + line
                            + "', check file enconding is UTF-8 ");
            }
        }
    }

    /**
     * Tous les champs dont le nom commence par "url_" doivent avoir une valeur qui commencer par https
     */
    @Test
    public void testURL() throws IOException {
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(DataTest.class.getResourceAsStream("/partenaires.csv"), StandardCharsets.UTF_8))) {

            String[] header = null;
            String lineText;
            int n = 0;
            while ((lineText = reader.readLine()) != null) {
                n++;
                if (header == null) {
                    header = lineText.split(",");
                } else {
                    String[] fields = header;
                    String[] line = lineText.split(",");
                    int ln = n;
                    IntStream.range(0, header.length)
                        .filter(i -> fields[i].startsWith("url_") && !line[i].isEmpty())
                        .forEach(i -> {
                            assertTrue(line[i].startsWith("https"),
                                "[partenaire.csv:" + ln + "] URL of field " + fields[i] + " must start with https: "
                                        + line[i]);
                        });
                }
            }
        }
    }
}
