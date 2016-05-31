/**
 * Created by aj on 20-5-16.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Mapping {
    String[][] map = null;

    public String[][] makeMapping (String path){
        double t = System.currentTimeMillis();
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String aLine;
            int numberofProts = 0;
            int i = 0;
            String protAcc;
            String probeID;



            while ( ( aLine = br.readLine() ) != null ) {
                if ( aLine.startsWith( ">" ) ){
                    numberofProts++;
                }
            }

            map = new String[numberofProts][2];

            br = new BufferedReader(new FileReader(path));
            while ( ( aLine = br.readLine() ) != null ) {
                if ( aLine.startsWith( ">" ) ) {
                    protAcc = aLine.substring( aLine.indexOf(">") +1, aLine.indexOf(" ") );
                    probeID = aLine.substring( aLine.indexOf("gene:") +5, aLine.indexOf(" transcript:") );

                    map[i][0] = probeID;
                    map[i][1] = protAcc;
                    i++;
                }
            }
            br.close();
            System.out.println("Building fumigatus gene<->prot map took "+ (System.currentTimeMillis() - t) +" millis");

        }catch(IOException e){
            System.out.println("Exception thrown  :" + e);
        }

        return map;
    }

    public String giveProt (String probeID){
        String result = null;

        for (int i = 0; i < map.length; i++) {

            if (map[i][0].equals(probeID))
                result = map[i][1];
        }

        return result;
    }

}
