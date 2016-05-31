import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Objects;

/**
 * this is an informative info message
 */
public class BlastParser {

    private HashMap<String, String> map;

    public BlastParser(String file) {
        double t = System.currentTimeMillis();
        map = new HashMap<>();
        try {
            File f = new File(file);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            String protz5 = "";
            String prota = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    if (line.startsWith("# Query")) {
                        protz5 = line.split(" ")[2];
                        prota = "";
                    }
                }else{
                    if (line.length()>0 && Objects.equals("", prota)) {
                        prota = line.split("\\t")[1];
                        map.put(protz5, prota);
                    }
                }
            }
            System.out.println("Building fumigatus blast map took "+ (System.currentTimeMillis() - t) +" millis");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String Z5ToA (String prota) {
        return map.get(prota);
    }
}
